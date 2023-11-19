package com.example.app_main.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_main.Database.DB_helper;
import com.example.app_main.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private DB_helper dbHelper;
    private Button btnClockIn, btnClockOut, btnTakeBreak, btnResumeWork, btnlogout;
    private TextView user, tvStatus, tvClockInTime, tvBreakInTime, tvBreakOutTime, tvTotalHours ,tvClockOutTime;
    private Date clockInTime=null, clockOutTime=null, breakInTime=null, breakOutTime=null;
    String totalHours="";
    private boolean isClockedIn = false;
    private boolean isOnBreak = false;
    private boolean isBreaktaken=false;

    private boolean isClockedIn_prep, isOnBreak_prep, isbreakended_prep,isclockout_prep;

    private long i;
    SharedPreferences preferences;
String temp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnClockIn = findViewById(R.id.btn_clock_in);
        btnClockOut = findViewById(R.id.btn_clock_out);
        btnTakeBreak = findViewById(R.id.btn_break_start);
        btnResumeWork = findViewById(R.id.btn_break_end);
        btnlogout = findViewById(R.id.btn_employee_logout);

        tvClockInTime = findViewById(R.id.text_clock_in_editable);
        tvBreakInTime = findViewById(R.id.text_break_start_editable);
        tvBreakOutTime = findViewById(R.id.text_break_end_editable);
        tvClockOutTime = findViewById(R.id.text_clock_out_editable);
        tvTotalHours = findViewById(R.id.text_total_hours);
        user= findViewById(R.id.employee_name);
        tvStatus = findViewById(R.id.text_current_status_editable);

        dbHelper = new DB_helper(this);
        preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String retrievedValue = preferences.getString("stringValueKey", "DefaultFallbackValue");
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
        temp1 = getIntent().getStringExtra("employeeIdentifier");
        isClockedIn_prep= preferences.getBoolean("isclockin", false);
        isOnBreak_prep= preferences.getBoolean("isbreakon",false);
        isbreakended_prep= preferences.getBoolean("isbreakended", false);
        isclockout_prep= preferences.getBoolean("isclockout", false);

        if(isLoggedIn){
            retrievedValue = retrievedValue.substring(0, 1).toUpperCase() + retrievedValue.substring(1).toLowerCase();
            user.setText(retrievedValue);
        }
        else {
            user.setText(temp1);
            editor.putBoolean("isLoggedIn", true);
            editor.apply();

        }
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
                i = dbHelper.insertWorkTime(clockInTime, clockOutTime, breakInTime, breakOutTime, totalHours, temp1);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String formattedTime = sdf.format(clockInTime);
                editor.putString("clock_in_time_key", formattedTime.toString());
                editor.putBoolean("isclockin", true);
                editor.commit();

            }
        });
        if(isClockedIn_prep)
        {
            String retrieved_clock_in = preferences.getString("clock_in_time_key", "DefaultFallbackValue1");
            tvClockInTime.setText(retrieved_clock_in);
            btnClockIn.setClickable(false);
            isClockedIn=true;
            btnClockIn.getBackground().setAlpha(126);
        }

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
                            dbHelper.updateClockOutTime(i,clockOutTime,totalHours);

                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                            String formattedTime = sdf.format(clockOutTime);
                            editor.putString("clock_out_time_key", formattedTime.toString());
                            editor.putBoolean( "isclockout",true);
                            editor.commit();

                        } else if(isBreaktaken) {
                            Toast.makeText(MainActivity.this, "Please end your break", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(MainActivity.this, "Please clock in first", Toast.LENGTH_SHORT).show();
                    }
                });
        if(isclockout_prep)
        {
            String retrieved_clock_in = preferences.getString("clock_out_time_key", "DefaultFallbackValue1");
            tvClockOutTime.setText(retrieved_clock_in);
            btnClockOut.setClickable(false);
            btnClockOut.getBackground().setAlpha(126);
        }

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
                            dbHelper.updateBreakInTime(i, breakInTime);

                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                            String formattedTime = sdf.format(breakInTime);
                            editor.putString("break_in_time_key", formattedTime.toString());
                            editor.putBoolean( "isbreakon",true);
                            editor.commit();
                        } else
                            Toast.makeText(MainActivity.this, "please clock in first", Toast.LENGTH_SHORT).show();

                    }
                });
                if(isOnBreak_prep)
                {
                    String retrieved_clock_in = preferences.getString("break_in_time_key", "DefaultFallbackValue1");
                    tvBreakInTime.setText(retrieved_clock_in);
                    isOnBreak = false;
                    isBreaktaken= true;
                    btnTakeBreak.setClickable(false);
                    btnTakeBreak.getBackground().setAlpha(126);
                }

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
                            dbHelper.updateBreakOutTime(i,breakOutTime);

                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                String formattedTime = sdf.format(breakOutTime);
                                editor.putString("break_out_time_key", formattedTime.toString());
                                editor.putBoolean( "isbreakended",true);
                                editor.commit();
                        }
                                else if(isClockedIn){
                                    Toast.makeText(MainActivity.this, "punch your break first", Toast.LENGTH_SHORT).show();
                                }
                                else

                                        Toast.makeText(MainActivity.this, "please clock in or punch break first", Toast.LENGTH_SHORT).show();

                        }
                    });
        if(isClockedIn_prep && isOnBreak_prep && isbreakended_prep)
        {
            String retrieved_clock_in = preferences.getString("break_out_time_key", "DefaultFallbackValue1");
            tvBreakOutTime.setText(retrieved_clock_in);
            isOnBreak=false;
            btnResumeWork.setClickable(false);
            btnResumeWork.getBackground().setAlpha(126);
        }


                    btnlogout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            logout();
                        }
                    });


        }


    private void updateStatus(){
        String status = "Currently: ";
        if (isClockedIn) {
            status += "Clocked In";
            if (isOnBreak) {
                status = " ";
                status = "Currently: On Break";
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
        preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);


        if (isLoggedIn) {
            String retrievedClockIn = preferences.getString("clock_in_time_key", null);
            String retrievedBreakIn = preferences.getString("break_in_time_key", null);
            String retrievedBreakOut = preferences.getString("break_out_time_key", null);
            Log.d("Debug", "retrievedClockIn: " + retrievedClockIn);
            Log.d("Debug", "retrievedBreakIn: " + retrievedBreakIn);
            Log.d("Debug", "retrievedBreakOut: " + retrievedBreakOut);
            if (retrievedClockIn != null && !retrievedClockIn.equals("DefaultFallbackValue1")
                    && retrievedBreakIn != null && !retrievedBreakIn.equals("DefaultFallbackValue1")
                    && retrievedBreakOut != null && !retrievedBreakOut.equals("DefaultFallbackValue1")) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

                try {
                    Date clockInTime = sdf.parse(retrievedClockIn);
                    Date breakInTime = sdf.parse(retrievedBreakIn);
                    Date breakOutTime = sdf.parse(retrievedBreakOut);

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
                    tvTotalHours.setText(totalHours);
                } catch (ParseException e) {
                    e.printStackTrace();
                    // Handle the ParseException (log or show an error message)
                }
            } else {
                // Handle the case where one or more preferences have null or fallback values
                // Log or show an error message
                tvTotalHours.setText("Total hours: N/A");
            }
        }
    }




    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Logout Confirmation")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform logout or any other action here

                            logout();


                            }


                })
                .setNegativeButton(android.R.string.no, null)
                .show();

        }
    private void logout() {
        preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        isClockedIn_prep = preferences.getBoolean("isclockin", false);
        isclockout_prep = preferences.getBoolean("isclockout", false);
        if (isClockedIn_prep && isclockout_prep) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.putBoolean("isclockin", false);
            editor.putBoolean("isbreakon", false);
            editor.putBoolean("isclockout", false);
            editor.putBoolean("isbreakended", false);
            editor.apply();
            Intent i = new Intent(MainActivity.this, Homepage.class);
            startActivity(i);
            finish();
        }
        else{

            Toast.makeText(MainActivity.this, "invalid input", Toast.LENGTH_SHORT).show();
        }
    }

    }


