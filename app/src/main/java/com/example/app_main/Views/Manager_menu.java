package com.example.app_main.Views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app_main.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Manager_menu extends AppCompatActivity {
    private TextView greet;
    private ImageButton addEmployee, editEmployee, deleteEmployee, sendMessage, btn_assign_videos, btn_reports;
    private TextView timeDate;
    ImageView btn_employee_lookup;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_menu);

        greet = findViewById(R.id.Greetings);
        timeDate = findViewById(R.id.time_date);
        addEmployee = findViewById(R.id.add_employee);
        editEmployee = findViewById(R.id.edit_employee);
        deleteEmployee = findViewById(R.id.delete_employee);
        sendMessage= findViewById(R.id.btn_send_message);
        btn_employee_lookup= findViewById(R.id.btn_employee_lookup);
        btn_assign_videos = findViewById(R.id.btn_assign_videos);
        btn_reports = findViewById(R.id.btn_reports);

        updateTimeAndGreeting();

        addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Manager_menu.this, Add_employee.class);
                startActivity(i);
            }
        });

        editEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Manager_menu.this, Edit_employee.class);
                startActivity(i);
            }
        });

        deleteEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Manager_menu.this, Delete_employee.class);
                startActivity(i);
            }
        });
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Manager_menu.this, Send_message.class);
                startActivity(i);
            }
        });
        btn_employee_lookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Manager_menu.this, employee_lookup.class);
                startActivity(i);
            }
        });

        btn_assign_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Manager_menu.this, Assign_videos.class);
                startActivity(i);
            }
        });

        btn_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Manager_menu.this, Generate_reports.class);
                startActivity(i);
            }
        });
    }

    private void updateTimeAndGreeting() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        SimpleDateFormat format1 = new SimpleDateFormat("EEEE");
        String weekDays = format1.format(date);
        timeDate.setText(weekDays + " " + DateFormat.getDateInstance(DateFormat.MEDIUM).format(date));

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        String greeting;
        if (hour < 12) {
            greeting = "Good Morning";
        } else if (hour < 17) {
            greeting = "Good Afternoon";
        } else {
            greeting = "Good Evening";
        }
        greet.setText(greeting);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, Manager_login.class);
        startActivity(i);
    }
}
