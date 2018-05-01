package com.davidmaceda.projecttimerapp;

import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    DatabaseHelper mDatabaseHelper;
    private Button stop_save_btn, view_records_btn;
    private EditText editText;

    private Chronometer mChronometer;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.timerID);
        stop_save_btn = (Button) findViewById(R.id.stop_save_btn);
        view_records_btn = (Button) findViewById(R.id.view_records_btn);
        mDatabaseHelper = new DatabaseHelper(this);

        mChronometer = findViewById(R.id.mChronometer);
        mChronometer.setFormat("Time: %s");

        stop_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                if (editText.length() != 0){
                    AddData(newEntry);
                    editText.setText("");
                }else{
                    toastMessage("You must enter a Timer ID.!");
                }
            }
        });
            
    }
    public void AddData(String newEntry){
        boolean insertData = mDatabaseHelper.addData(newEntry);
        if (insertData){
            toastMessage("Data Successfully Inserted.!");
        }else{
            toastMessage("Something Went Wrong..!");
        }
    }
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void showElapsedTime() {
        long elapsedMillis = SystemClock.elapsedRealtime() - mChronometer.getBase();
        String chronoText = mChronometer.getText().toString();
        Toast.makeText(MainActivity.this, "Elapsed Time: " + chronoText,
                Toast.LENGTH_LONG).show();
    }

    private void showResetMsg() {
        String messageReset = "Reset..!";
        Toast.makeText(MainActivity.this, "Timer Has Been " + messageReset, Toast.LENGTH_LONG).show();
    }

    private void showPausedMsg() {
        String chronoText = mChronometer.getText().toString();
        String messagePause = chronoText;
        Toast.makeText(MainActivity.this, "Timer Paused At: " + messagePause, Toast.LENGTH_LONG).show();
    }


    public void startChronometer(View v) {
        int stoppedMilliseconds = 0;

        String chronoText = mChronometer.getText().toString();
        String array[] = chronoText.split(":");
        if (array.length == 2) {
            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000
                    + Integer.parseInt(array[1]) * 1000;
        } else if (array.length == 3) {
            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000
                    + Integer.parseInt(array[1]) * 60 * 1000
                    + Integer.parseInt(array[2]) * 1000;
        }

        mChronometer.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
        mChronometer.start();
    }

    public void pauseChronometer(View v) {
        mChronometer.stop();
        showPausedMsg();
    }

    public void stopSaveChronometer(View v) {
        mChronometer.stop();
        showResetMsg();
    }

    public void viewRecords(View v) {
        mChronometer.stop();
        showResetMsg();
    }


    public void resetChronometer(View v) {
        mChronometer.setBase(SystemClock.elapsedRealtime());
        showResetMsg();
    }

    private void formatText() {
        String chronoText = mChronometer.getText().toString();

    }
}





    /*//private TextView text_view_chronometer; // Text view to display the chronometer value
    private Chronometer chronometer; // instance of chronometer class
    // Timer variables
    private long startTime; // used to store the system's time when timer started
    private long pauseOffset;   //USED TO CALCULATE THE TIME DIFFERENCE BETWEEN THE TIME IT STARTED AND THE TIME WE PAUSED IT
    private boolean running;    // Indicates if the chronometer is running or if it's paused

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chronometer = findViewById(R.id.chronometer); // reference the XML chronometer
        chronometer.setFormat("Time: %s");  // Time format
        chronometer.setBase(SystemClock.elapsedRealtime()); //Returns milliseconds since boot, including time spent in sleep. resets it back to zero

        System.currentTimeMillis(); //Returns System Current Time

        *//*chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {  // Used to do something every second passed
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 10000) {  // Resets the chronometer every 10k milliseconds
                    chronometer.setBase(SystemClock.elapsedRealtime()); // resets it back to zero
                    Toast.makeText(MainActivity.this, "Restarted..!!", Toast.LENGTH_SHORT).show();
                }
            }
        });*//*
    }
    // Method  to  Start
    public void startChronometer(View v) {
        if (!running) {     // check if the chronometer is running
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset); // sets the base back to the "time" when the clock start'd
            chronometer.start();    //Starts the chronometer
            running = true;
        }
    }
    // Method  to  Pause
    public void pauseChronometer(View v) {
        if (running) {      // check if the chronometer is running
            chronometer.stop(); //Stops the chronometer
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase(); // computes the calculation
            running = false;
        }
    }
    //Method to Stop
    *//*public void stopChronometer(View v){
        if (running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase(); // computes the calculation
            running = false;
        }
    }*//*
    // Method  to  Reset
    public void resetChronometer(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime()); // This resets it back to zero
        pauseOffset = 0;    // erase the time to offset
    }
    *//*private void updateText(){
        int minutes = (int) (pauseOffset / 1000) / 60;
        int seconds = (int) (pauseOffset / 1000) % 60;
        String chronometerFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        text_view_chronometer.setText(chronometerFormatted);
    }*//*

    @Override
    protected void onStop(){
        super.onStop();

        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong("timeStarted", startTime);
        editor.putBoolean("chronoRunning", running);
        editor.putLong("offset", pauseOffset);
        editor.apply();
    }
    @Override
    protected void onStart(){
        super.onStart();

        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        startTime = preferences.getLong("timeStarted", 0);
        running = preferences.getBoolean("chronoRunning", false);

        if (running){
            pauseOffset = preferences.getLong("offset", 0);
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            //updateText();
        }else {
            chronometer.start();
        }
    }*/

