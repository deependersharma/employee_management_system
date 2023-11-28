package com.example.app_main.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_main.Database.Login_DB_helper;
import com.example.app_main.R;

public class Employee_login extends AppCompatActivity {
    EditText email, pass;
    Button login;
    public static final String Name = "nameKey";
    Login_DB_helper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);
        email= findViewById(R.id.editTextTextEmailAddress);
        pass= findViewById(R.id.editTextTextPassword);
        login= findViewById(R.id.employee_login);
        dbHelper= new Login_DB_helper(this);
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // Navigate to the dashboard
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailId= email.getText().toString();
                String password= pass.getText().toString();
                boolean login_check= dbHelper.CheckLogin(emailId, password);
                if(login_check==true){
                    SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString("stringValueKey", emailId);
                    editor.putString("stringValueKey1", password);
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "login successful",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Employee_login.this, MainActivity.class);
                    i.putExtra("employeeIdentifier",emailId);
                    i.putExtra("emailId", password);
                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Invalid email or pass", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}