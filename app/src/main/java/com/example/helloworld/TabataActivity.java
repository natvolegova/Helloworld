package com.example.helloworld;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

public class TabataActivity extends AppCompatActivity {
    TextView tvMainTime;
    TextView tvCurRound;
    Button btnFight;
    CountDownTimer roundTimer;
    CountDownTimer restTimer;
    SimpleDateFormat sdf;  // формат времени
    int currentRound = 1;  // для подсчёта раундов
    SharedPreferences shPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabata);
        initView();
        shPref = PreferenceManager.getDefaultSharedPreferences(this);
        sdf = new SimpleDateFormat("mm:ss");
    }

    private void initView() {
        tvMainTime = findViewById(R.id.tvMainTime);
        tvCurRound = findViewById(R.id.tvCurRound);
        btnFight = findViewById(R.id.btnFight);
        btnFight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTabata(3, 20, 10);
            }
        });
    }

    private void startTabata(final int rounds, int timeRound, int timeRest) {

        tvCurRound.setText("" + currentRound);

        roundTimer = new CountDownTimer(timeRound * 1000, 200) {
            @Override
            public void onTick(long l) {
                tvMainTime.setText(sdf.format(l));
            }

            @Override
            public void onFinish() {
                if (currentRound > rounds) {
                    ResetTimer();
                } else {
                    currentRound++;
                    restTimer.start();
                }
            }

            ;
        };
        roundTimer.start();

        restTimer = new CountDownTimer(timeRest*1000, 200){

            @Override
            public void onTick(long l) {
                tvMainTime.setText(sdf.format(l));
            }

            @Override
            public void onFinish() {
                if (currentRound == rounds) {
                    ResetTimer();
                } else {
                    tvCurRound.setText("" + currentRound);
                    roundTimer.start();
                }
            }
        };


    }

    public void ResetTimer() {
        if (roundTimer != null) roundTimer.cancel();
        if (restTimer != null) restTimer.cancel();
        tvCurRound.setText("" + currentRound);
        tvMainTime.setText(""+60);
    }
}
