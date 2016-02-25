package com.bishwajit.countdown;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer = null;
    TextView msg;
    Button start,pause, stop;
    NumberPicker hourPicker, minPicker, secPicker;
    Counter count = null;
    Time t = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.BLUE);
        setSupportActionBar(toolbar);
        t = new Time();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(i);
            }
        });
        msg = (TextView)findViewById(R.id.msg);
        start = (Button)findViewById(R.id.start);
        stop = (Button)findViewById(R.id.stop);
        pause = (Button)findViewById(R.id.pause);
        hourPicker = (NumberPicker)findViewById(R.id.hourPicker);
        minPicker = (NumberPicker)findViewById(R.id.minPicker);
        secPicker = (NumberPicker)findViewById(R.id.secPicker);
        prepare();

        start.setOnClickListener(
                new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        countdownTimerPlay();
                    }
                }
        );

        stop.setOnClickListener(
                new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        countdownTimerStop();
                    }
                }
        );

        pause.setOnClickListener(
                new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        countdownTimerPause();
                    }
                }
        );
    }

    public  void prepare()
    {
        hourPicker.setMaxValue(99); hourPicker.setMinValue(0);
        minPicker.setMaxValue(59);  minPicker.setMinValue(0);
        secPicker.setMaxValue(59);  secPicker.setMinValue(0);
        stop.setEnabled(false);
        pause.setEnabled(false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.About) {
            Intent i = new Intent(this, Main2Activity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void countdownTimerPlay()
    {
        start.setEnabled(false);
        stop.setEnabled(true);
        pause.setEnabled(true);

        t.setHour(hourPicker.getValue());
        t.setMin(minPicker.getValue());
        t.setSec(secPicker.getValue());
        hourPicker.setEnabled(false);
        minPicker.setEnabled(false);
        secPicker.setEnabled(false);
        /*String s = Integer.toString(t.getHour()) +Integer.toString(t.getMin()) +Integer.toString(t.getSec());
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        */
        long duration = (t.getHour()*3600 + t.getMin()*60 + t.getSec())*1000;
        count = new Counter(duration, 1000);
        count.start();
    }

    public void countdownTimerStop()
    {
        hourPicker.setValue(hourPicker.getMinValue());
        minPicker.setValue(minPicker.getMinValue());
        secPicker.setValue(secPicker.getMinValue());
        stop.setEnabled(false);
        pause.setEnabled(false);
        start.setEnabled(true);
        count.cancel();
        t.setSec(0);
        t.setMin(0);
        t.setHour(0);
        hourPicker.setEnabled(true);
        minPicker.setEnabled(true);
        secPicker.setEnabled(true);
    }

    public void countdownTimerPause()
    {
        start.setEnabled(true);
        pause.setEnabled(false);
        stop.setEnabled(true);
        count.cancel();
    }



    public class Counter extends CountDownTimer
    {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public Counter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            if(mediaPlayer!=null)
            {
                mediaPlayer.release();
                mediaPlayer = null;
            }

        }

        @Override
        public void onTick(long millis) {

            hourPicker.setValue((int) TimeUnit.MILLISECONDS.toHours(millis));
            minPicker.setValue((int) (TimeUnit.MILLISECONDS.toMinutes(millis)- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))));
            secPicker.setValue((int) (TimeUnit.MILLISECONDS.toSeconds(millis)- TimeUnit.MINUTES.toMinutes(TimeUnit.MILLISECONDS.toMinutes(millis))));

        }

        @Override
        public void onFinish() {
            secPicker.setValue(0);
            start.setEnabled(true);
            stop.setEnabled(false);
            pause.setEnabled(false);
            hourPicker.setEnabled(true);
            minPicker.setEnabled(true);
            secPicker.setEnabled(true);
            Toast.makeText(MainActivity.this, "CountDown Finished",Toast.LENGTH_LONG).show();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            });
        }
    }
}
