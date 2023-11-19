package com.example.app_main.Views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.app_main.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Manager_menu extends AppCompatActivity {
    private TextView greet;
    private ImageButton addEmployee, editEmployee, deleteEmployee;
    private TextView timeDate;

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
