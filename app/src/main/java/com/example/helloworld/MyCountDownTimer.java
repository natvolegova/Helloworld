package com.example.helloworld;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

public abstract class MyCountDownTimer {
    private long mMillisInFuture;
    private long mCountdownInterval;
    private long mStopTimeInFuture;
    private long mValuePause; // сколько не досчитал таймер

    public MyCountDownTimer(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }

    public synchronized final void pause() {
        if(!mHandler.hasMessages(MSG)) return;
        mValuePause = mStopTimeInFuture - SystemClock.elapsedRealtime(); // сколько не досчитал таймер
        mHandler.removeMessages(MSG);
    }

    public synchronized final void resume() {
        if(mHandler.hasMessages(MSG)) return;
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mValuePause;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
    }

    public final void cancel() {
        mHandler.removeMessages(MSG);
    }

    public synchronized final MyCountDownTimer start() {
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }

    public synchronized final boolean isActive() {
        if(mHandler.hasMessages(MSG)) return true;
        return false;
    }

    public abstract void onTick(long millisUntilFinished);
    public abstract void onFinish();
    private static final int MSG = 1;

    // handles counting down
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            synchronized (MyCountDownTimer.this) {
                final long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime(); // сколько осталось

                if (millisLeft <= 0) {
                    onFinish();
                } else if (millisLeft < mCountdownInterval) {  // если оставшееся время даже меньше чем время для onTick
                    // no tick, just delay until done		      то заходим сюда через оставшееся время и попадём в onFinish
                    sendMessageDelayed(obtainMessage(MSG), millisLeft);
                } else {
                    long lastTickStart = SystemClock.elapsedRealtime(); // сюда попадаем, если оставшееся время >= onTick
                    onTick(millisLeft);

                    // take into account user's onTick taking time to execute
                    long delay = lastTickStart + mCountdownInterval - SystemClock.elapsedRealtime(); // корректировка до следующего тика

                    // special case: user's onTick took more than interval to
                    // complete, skip to next interval
                    while (delay < 0) delay += mCountdownInterval;

                    sendMessageDelayed(obtainMessage(MSG), delay);
                }
            }
        }
    };
}
