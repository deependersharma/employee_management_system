package com.example.app_main.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.app_main.Database.Login_DB_helper;
import com.example.app_main.Model.Employees_model;
import com.example.app_main.R;

import java.util.List;

public class Employees_lookup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_lookup);

    }
}