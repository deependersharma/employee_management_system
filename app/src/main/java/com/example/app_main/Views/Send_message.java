package com.example.app_main.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;

import com.example.app_main.R;

import java.util.List;

public class Send_message extends AppCompatActivity {

    Spinner employeeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        employeeSpinner = findViewById(R.id.employee_select_spinner);

    }
}