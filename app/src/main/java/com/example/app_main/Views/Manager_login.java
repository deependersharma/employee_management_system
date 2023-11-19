package com.example.app_main.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_main.Database.Manager_DB_helper;
import com.example.app_main.R;

public class Manager_login extends AppCompatActivity {
    private Button login;
    private EditText email, pass;
    private Manager_DB_helper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);
        email = findViewById(R.id.editTextEmailAddress);
        pass = findViewById(R.id.editTextPassword);
        login = findViewById(R.id.Manager_login);
        dbHelper = new Manager_DB_helper(this);

        email.setText("gagi");
        pass.setText("123");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailId = email.getText().toString();
                String password = pass.getText().toString();
                boolean login_check = dbHelper.CheckLogin(emailId, password);
                if (login_check) {
                    Toast.makeText(getApplicationContext(), "login successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Manager_login.this, Manager_menu.class);
                    i.putExtra("ManagerIdentifier", emailId);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid email or pass", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
        @Override
        public void onBackPressed() {
            super.onBackPressed();
            Intent i=new Intent(this, Homepage.class);
            startActivity(i);

        }


    }