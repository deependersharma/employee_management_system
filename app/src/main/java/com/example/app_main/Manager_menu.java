package com.example.app_main;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
public class Manager_menu extends AppCompatActivity {
   TextView greet;
   LinearLayout add_employee, edit_employee;
   TextView time_date;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_menu);
        greet= findViewById(R.id.Greetings);
        time_date =findViewById(R.id.time_date);
        add_employee= findViewById(R.id.add_employee);
        edit_employee=findViewById(R.id.edit_employee);
        //Get the time of day
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        SimpleDateFormat format1 = new SimpleDateFormat("EEEE");
        String week_days= format1.format(date);
        time_date.setText(week_days+ " "+DateFormat.getDateInstance(DateFormat.MEDIUM).format(date));

        int hour = cal.get(Calendar.HOUR_OF_DAY);
System.out.println(hour);
        //Set greeting
        String greeting = null;
        if(hour>=4 && hour<12){
            greeting = "Good Morning";
            greet.setText( greeting);
        } else if(hour>= 12 && hour < 17){
            greeting = "Good Afternoon";
            greet.setText( greeting);
        } else if(hour >= 17 && hour < 21){
            greeting = "Good Evening";
            greet.setText( greeting);
        } else if(hour >= 21 && hour < 24){
            greeting = "Good Night";
            greet.setText( greeting );
        }
        else if (hour >= 0 && hour <4){
            greeting = "Good Night";
            greet.setText( greeting );
        }

add_employee.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
 Intent i=new Intent(Manager_menu.this, Add_employee.class);
 startActivity(i);
    }
});
edit_employee.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent(Manager_menu.this, Edit_employee.class);
        startActivity(i);
    }
});
        }

    }