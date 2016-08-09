package com.marta.stoper.viewmodel;

import android.databinding.ObservableField;
import android.os.Handler;
import android.os.Message;

import com.marta.stoper.stopwatch.StopWatch;

public class StoperViewModel {

    public ObservableField<String> elapsedTime = new ObservableField<>("dupa");
    public ObservableField<Boolean> running = new ObservableField<>(false);

    final int MSG_START_TIMER = 0;
    final int MSG_STOP_TIMER = 1;
    final int MSG_UPDATE_TIMER = 2;


    final int REFRESH_RATE = 10;
    private long startTime = -2;
    private boolean isContinue = false;

    StopWatch timer;

    public StoperViewModel(long startTime, Boolean running, long curTime){
        this.running.set(running);
        if(startTime == 0) //nie mam nic zapisane
            System.currentTimeMillis();
        else {
            this.startTime = startTime;

            if(!running) {
                isContinue = true;
            }
        }

        timer = new StopWatch(startTime,this.running.get(),this.isContinue, curTime);
   /*     if(startTime!=System.currentTimeMillis())
            setIsContinue(true);
        else setIsContinue(false);*/
        if(isContinue)
            refresh();
        else {
            elapsedTime.set(timer.elapsedTime());
            sendMessageRefresh();
        }
    }
public long getStartTime(){
    return timer.getStartTime();
}
    public long getCurrentTime(){
        return timer.getCurrentTime();
    }


     Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_START_TIMER:
                    mHandler.sendEmptyMessage(MSG_UPDATE_TIMER);
                    break;

                case MSG_UPDATE_TIMER:
                    refresh();
                    mHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIMER, REFRESH_RATE); //text view is updated every second,
                    break;                                  //though the timer is still running

                case MSG_STOP_TIMER:
                    mHandler.removeMessages(MSG_UPDATE_TIMER); // no more updates.
                    break;

                default:
                    break;
            }
        }
    };

   /* public void getElapsedTime() {
        StoperViewModel.elapsedTime.set(Long.toString(System.currentTimeMillis()));
    }

    public void sendMessageStart() {
        timer.start();
        running.set(true);
        mHandler.sendEmptyMessage(MSG_START_TIMER);
    }

    public void sendMessageStop() {
        timer.stop();
        running.set(false);
        mHandler.sendEmptyMessage(MSG_STOP_TIMER);
}
*/
    public void refresh() {
           elapsedTime.set(timer.elapsedTime());
    }

    public void sendMessageRefresh(){
        mHandler.sendEmptyMessage(MSG_UPDATE_TIMER);
    }
    public void sendMessageClear() {
        timer.clear();
        running.set(false);
        elapsedTime.set("00:00");
        mHandler.sendEmptyMessage(MSG_STOP_TIMER);
    }

    public void sendMessageResume() {

        timer.resume();
        running.set(true);
        mHandler.sendEmptyMessage(MSG_START_TIMER);
    }
    public void sendMessagePause(){
        timer.pause();
        running.set(false);
        mHandler.sendEmptyMessage(MSG_STOP_TIMER);
    }
   // public void setIsContinue(boolean value){
//        timer.setIsContinue(value);
//    }
}
