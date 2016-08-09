package com.marta.stoper.stopwatch;

import java.util.concurrent.TimeUnit;

public class StopWatch {

    private static final String TAG = "marta-tag";
    public long startTime;
    public boolean running = false;
    private long currentTime = 0;
    private boolean isContinue = false;

    public StopWatch(long startTime, Boolean running, Boolean isContinue, long curTime) {
        this.startTime = startTime;
        this.running = running;
        this.isContinue = isContinue;
        this.currentTime = curTime;
    }

    public static String miliToTimeMin(long milliseconds) {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)),
                (milliseconds - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(milliseconds))) / 10
        );
    }

    public static String miliToTimeSec(Long milliseconds) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)),
                (milliseconds - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(milliseconds))) / 10
        );
    }

    public long getStartTime() {
        return startTime;
    }
    public long getCurrentTime(){
        return currentTime;
    }

    public void setIsContinue(boolean value) {
        this.isContinue = value;
    }

    public void start() {
        startTime = System.currentTimeMillis();
        this.running = true;
    }

    public void stop() {
        this.running = false;
    }

    public void pause() {
        this.running = false;
        currentTime = System.currentTimeMillis() - startTime;
        isContinue = true;
    }

    public void resume() {
        this.running = true;
        isContinue = false;
        startTime = System.currentTimeMillis() - currentTime;
    }

    public String elapsedTime() {

        if (this.running) {
            long milliseconds = System.currentTimeMillis() - startTime;
            String time;
            if (TimeUnit.MILLISECONDS.toMinutes(milliseconds) > 0) {
                time = miliToTimeMin(milliseconds);
            } else
                time = miliToTimeSec(milliseconds);

            isContinue = false;
            return time;
        }
        else if(isContinue){
            long milliseconds =currentTime;
            String time;
            if (TimeUnit.MILLISECONDS.toMinutes(milliseconds) > 0) {
                time = miliToTimeMin(milliseconds);
            } else
                time = miliToTimeSec(milliseconds);
            return time;
        }
        else {
            return "00:00";

        }
    }

    public void clear() {
        startTime = System.currentTimeMillis();
        currentTime = 0;
        this.running = false;
    }
}
