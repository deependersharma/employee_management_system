package com.example.app_main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class homePage extends AppCompatActivity {
    private Button btn_employee, btn_manager;
    LoginDBHelper DBHelper;
    ManagerDBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        btn_employee= findViewById(R.id.button);
        btn_manager= findViewById(R.id.button2);
        DBHelper= new LoginDBHelper(this);
        db= new ManagerDBHelper(this);
        btn_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(homePage.this, Employee_login.class);
                startActivity(i);
            }
        });
        btn_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db.Insert("gagi",  "123");
                Intent i=new Intent(homePage.this, Manager_login.class);
                startActivity(i);

            }
        });
    }
}