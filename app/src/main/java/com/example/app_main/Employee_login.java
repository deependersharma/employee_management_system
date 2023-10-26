package com.example.app_main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Employee_login extends AppCompatActivity {
    EditText email, pass;
    Button login;
    LoginDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);
        email= findViewById(R.id.editTextTextEmailAddress);
        pass= findViewById(R.id.editTextTextPassword);
        login= findViewById(R.id.employee_login);
        dbHelper= new LoginDBHelper(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailId= email.getText().toString();
                String password= pass.getText().toString();
                boolean login_check= dbHelper.CheckLogin(emailId, password);
                if(login_check==true){
                    Toast.makeText(getApplicationContext(), "login successful",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Employee_login.this, MainActivity.class);
                    i.putExtra("employeeIdentifier",emailId);
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