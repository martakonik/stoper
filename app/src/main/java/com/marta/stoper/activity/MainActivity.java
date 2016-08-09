package com.marta.stoper.activity;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.marta.stoper.R;
import com.marta.stoper.databinding.ActivityMainBinding;
import com.marta.stoper.viewmodel.StoperViewModel;

public class MainActivity extends AppCompatActivity {

    static final String START_TIME = "startTime";
    static final String RUN = "run";
    static final String TAG = "marta-tag";
    private static final String START = "start";
    private static final String CUR_TIME = "currentTime";
    StoperViewModel stoperViewModel;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getSharedPreferences(START, 0);
        long time = settings.getLong(START_TIME, 0);
        Boolean isWorking = settings.getBoolean(RUN, false);
        long curTime = settings.getLong(CUR_TIME, 0);

        stoperViewModel = new StoperViewModel(time, isWorking, curTime);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(stoperViewModel);

        // reasume/pause
        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!stoperViewModel.running.get())
                    stoperViewModel.sendMessageResume();
                else
                    stoperViewModel.sendMessagePause();
            }
        });
        binding.buttonClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stoperViewModel.sendMessageClear();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences settings = getSharedPreferences(START, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putLong(START_TIME, stoperViewModel.getStartTime());
        editor.putBoolean(RUN, stoperViewModel.running.get());
        editor.putLong(CUR_TIME, stoperViewModel.getCurrentTime());
        editor.apply();
    }
}


