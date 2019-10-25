package com.example.helloworld;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Element> listOfElements;
    private ImageView iv_image;
    private TextView tv_name, tv_timer, tv_timer_all;
    private ProgressBar progressBar;
    private int i = 0;
    private int allTime = 0;
    private int sec =0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initElements();
        initView();
        initTask();
        initAllTimer();
    }

    private void initAllTimer() {
        final DateFormat formatter = new SimpleDateFormat("mm:ss");
        CountDownTimer countDownTimerAll = new CountDownTimer((allTime+listOfElements.size()) * 1000, 1000) {
            @Override
            public void onTick(long l) {
                tv_timer_all.setText(formatter.format(l));
            }
            @Override
            public void onFinish() {
                tv_timer_all.setText(formatter.format(0));
            }
        };
        countDownTimerAll.start();
    }

    private void initTask() {
        final Handler handler = new Handler();
        final DateFormat formatter = new SimpleDateFormat("mm:ss");
        handler.post(new Runnable() {
            @Override
            public void run() {
                final Element el = listOfElements.get(i);
                final int time = el.getTime();
                tv_name.setText(el.getName());
                String img = el.getImage();
                int resID = getRawResIdByName(img);
                iv_image.setImageResource(resID);
                progressBar.setMax(time);
                CountDownTimer countDownTimer = new CountDownTimer(time * 1000, 1000) {
                    @Override
                    public void onTick(long l) {
                        tv_timer.setText(formatter.format(l));
                        progressBar.setProgress(time-(int)l/1000);
                    }
                    @Override
                    public void onFinish() {
                        tv_timer.setText(formatter.format(0));
                        progressBar.setProgress(time);
                    }
                };
                countDownTimer.start();
                i++;
                if (i < listOfElements.size()) {
                    handler.postDelayed(this, time * 1000+1000);
                }
            }
        });
    }

    private void initView() {
        iv_image = findViewById(R.id.iv_image);
        tv_name = findViewById(R.id.tv_name);
        tv_timer = findViewById(R.id.tv_timer);
        progressBar = findViewById(R.id.progressBar);
        tv_timer_all = findViewById(R.id.tv_timer_all);
    }

    private void initElements() {
        listOfElements = new ArrayList<>();
        Element el = new Element("Name1", "img1", 10);

        listOfElements.add(el);
        el = new Element("Name2", "img2", 7);
        listOfElements.add(el);
        el = new Element("Name3", "img3", 5);
        listOfElements.add(el);

        //общее время
        for (int i = 0; i < listOfElements.size(); i++) {
            el = listOfElements.get(i);
            allTime = allTime + el.getTime();
        }
    }

    private int getRawResIdByName(String resName) {
        String pkgName = this.getPackageName();
        int resID = this.getResources().getIdentifier(resName, "drawable", pkgName);
        return resID;
    }
}
