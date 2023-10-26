package com.example.app_main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private Button btnClockIn, btnClockOut, btnTakeBreak, btnResumeWork, btnlogout;
    private TextView user, tvStatus, tvClockInTime, tvBreakInTime, tvBreakOutTime, tvTotalHours ,tvClockOutTime;
    private Date clockInTime=null, clockOutTime=null, breakInTime=null, breakOutTime=null;
    String totalHours="";
    String hello="hello ";
    private boolean isClockedIn = false;
    private boolean isOnBreak = false;
    private boolean isBreaktaken=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnClockIn = findViewById(R.id.btnClockIn);
        btnClockOut = findViewById(R.id.btnClockOut);
        btnTakeBreak = findViewById(R.id.btnTakeBreak);
        btnResumeWork = findViewById(R.id.btnResumeWork);
        btnlogout = findViewById(R.id.logout_btn);

        tvStatus = findViewById(R.id.textView);
        tvClockInTime = findViewById(R.id.clockin);
        tvBreakInTime = findViewById(R.id.breakin);
        tvBreakOutTime = findViewById(R.id.breakout);
        tvClockOutTime = findViewById(R.id.clockout);
        tvTotalHours = findViewById(R.id.textView2);
        user= findViewById(R.id.userId);

        dbHelper = new DBHelper(this);

        String temp1= getIntent().getStringExtra("employeeIdentifier");
        user.setText("hello "+temp1);
        btnClockIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClockedIn = true;
                updateStatus();
                // insert clock in time to the database
                updateClockInTime();
                // btnClockIn.setVisibility(View.GONE);
                btnClockIn.setClickable(false);
                btnClockIn.getBackground().setAlpha(126);
                btnClockOut.setVisibility(View.VISIBLE);
                btnTakeBreak.setVisibility(View.VISIBLE);
            }
        });
        btnClockOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isClockedIn && !isOnBreak && isBreaktaken) {
                            isClockedIn = false;
                            updateStatus();
                            updateClockOutTime();

                            //btnClockIn.setVisibility(View.VISIBLE);
                            btnClockIn.setClickable(false);
                            btnClockIn.getBackground().setAlpha(126);
                            //btnClockOut.setVisibility(View.GONE);
                            btnClockOut.setClickable(false);
                            btnClockOut.getBackground().setAlpha(126);
                            //btnTakeBreak.setVisibility(View.GONE);
                            btnTakeBreak.setClickable(false);
                            btnTakeBreak.getBackground().setAlpha(126);
                            //btnResumeWork.setVisibility(View.GONE);
                            btnResumeWork.setClickable(false);
                            btnResumeWork.getBackground().setAlpha(126);
                            calculateTotalHours();
                            dbHelper.insertWorkTime(clockInTime, clockOutTime, breakInTime, breakOutTime, totalHours, temp1);
                        } else if(isBreaktaken) {
                            Toast.makeText(MainActivity.this, "please end your break", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(MainActivity.this, "please clock in first", Toast.LENGTH_SHORT).show();
                    }
                });

                btnTakeBreak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isClockedIn) {
                            isOnBreak = true;
                            isBreaktaken= true;
                            updateStatus();
                            updateBreakInTime();
                            //btnTakeBreak.setVisibility(View.GONE);
                            btnTakeBreak.setClickable(false);
                            btnTakeBreak.getBackground().setAlpha(126);
                            btnResumeWork.setVisibility(View.VISIBLE);
                        } else
                            Toast.makeText(MainActivity.this, "please clock in first", Toast.LENGTH_SHORT).show();

                    }
                });

                    btnResumeWork.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(isOnBreak && isClockedIn){
                            isOnBreak = false;
                            updateStatus();
                            updateBreakOutTime();
                            btnTakeBreak.setVisibility(View.VISIBLE);
                            // btnResumeWork.setVisibility(View.GONE);
                            btnResumeWork.setClickable(false);
                            btnResumeWork.getBackground().setAlpha(126);
                            btnResumeWork.setClickable(false);
                        }
                                else if(isClockedIn){
                                    Toast.makeText(MainActivity.this, "punch your break first", Toast.LENGTH_SHORT).show();
                                }
                                else

                                        Toast.makeText(MainActivity.this, "please clock in or punch break first", Toast.LENGTH_SHORT).show();

                        }
                    });
                    btnlogout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i =new Intent(MainActivity.this, homePage.class);
                            startActivity(i);
                            finish();
                        }
                    });
        }


    private void updateStatus(){
        String status = "Status: ";
        if (isClockedIn) {
            status += "Clocked In";
            if (isOnBreak) {
                status += " (On Break)";
            }
        } else {
            status += "Not Clocked In";
        }
        tvStatus.setText(status);
    }
    private void updateClockInTime() {
        clockInTime=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String formattedTime = sdf.format(clockInTime);
        tvClockInTime.setText(formattedTime);
    }
    private void updateClockOutTime() {
        clockOutTime=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String formattedTime = sdf.format(clockOutTime);
        tvClockOutTime.setText(formattedTime);
    }
    private void updateBreakInTime() {
        breakInTime= new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String formattedTime = sdf.format(breakInTime);
        tvBreakInTime.setText(formattedTime);
    }
    private void updateBreakOutTime() {
        breakOutTime =new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String formattedTime = sdf.format(breakOutTime);
        tvBreakOutTime.setText(formattedTime);
    }
    private void calculateTotalHours() {
        if (clockInTime != null) {
            long currentTimeMillis = System.currentTimeMillis();

            // Calculate the total work time without considering breaks
            long totalWorkTimeMillis = currentTimeMillis - clockInTime.getTime();

            // Calculate the total break time
            long breakTimeMillis = 0;
            if (breakInTime != null && breakOutTime != null) {
                breakTimeMillis = breakOutTime.getTime() - breakInTime.getTime();
            }

            // Subtract break time from the total work time
            long totalWorkTimeMillisAfterBreaks = totalWorkTimeMillis - breakTimeMillis;

            // Calculate hours and minutes
            long hours = (totalWorkTimeMillisAfterBreaks / (1000 * 60 * 60)) % 24;
            long minutes = (totalWorkTimeMillisAfterBreaks / (1000 * 60)) % 60;

            totalHours = String.format("%02d:%02d", hours, minutes);
            tvTotalHours.setText("Total hours: " + totalHours);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        }
    }


