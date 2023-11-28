package com.example.app_main.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.app_main.Database.Login_DB_helper;
import com.example.app_main.Database.Manager_DB_helper;
import com.example.app_main.R;

public class Homepage extends AppCompatActivity {
    private Button btn_employee, btn_manager;
    Login_DB_helper DBHelper;
    Manager_DB_helper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        btn_employee= findViewById(R.id.button);
        btn_manager= findViewById(R.id.button2);
        DBHelper= new Login_DB_helper(this);
        db= new Manager_DB_helper(this);

        //shared preference
        // Optional: Finish the current activity to prevent going back
//        } else {
//            // Show the login screen
//            Intent intent = new Intent(this, .class);
//            startActivity(intent);
//            finish();  // Optional: Finish the current activity to prevent going back
//        }



        btn_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Homepage.this, Employee_login.class);
                startActivity(i);
            }
        });
        btn_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db.Insert("gagi",  "123");
                Intent i=new Intent(Homepage.this, Manager_login.class);
                startActivity(i);

            }
        });
    }
}