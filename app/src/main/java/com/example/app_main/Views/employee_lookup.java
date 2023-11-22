package com.example.app_main.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.app_main.Database.Employee;
import com.example.app_main.Database.Login_DB_helper;
import com.example.app_main.Database.Message_DB;
import com.example.app_main.Model.Edit_RV_adapter;
import com.example.app_main.Model.EmployeeLookupAdapter;
import com.example.app_main.Model.Employees_model;
import com.example.app_main.Database.DB_helper;
import com.example.app_main.R;

import java.util.ArrayList;
import java.util.List;

public class employee_lookup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spino;
    DB_helper dbHelper_main;
    EmployeeLookupAdapter editRVAdapter;
    private ArrayList<Employee> model;
    RecyclerView recyclerView;
    // create array of Strings
    // and store the name of courses
    String[] courses = {"clocked In", "On Break", "clocked Out"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_lookup);

        recyclerView = findViewById(R.id.recyclerview);
        dbHelper_main = new DB_helper(this);

        // Initialize your RecyclerView and set its adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(employee_lookup.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(editRVAdapter);

        // Take the instance of Spinner and
        // apply OnItemSelectedListener on it which
        // tells which item of spinner is clicked
        spino = findViewById(R.id.employee_select_spinner);
        spino.setOnItemSelectedListener(this);

        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter<String> ad
                = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                courses);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spino.setAdapter(ad);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        // Make a toast of the name of the selected employee from the spinner
        Toast.makeText(getApplicationContext(), courses[position], Toast.LENGTH_LONG).show();

        // Use .equals() for string comparison
        if ("clocked In".equals(courses[position])) {
            ArrayList<Employee> employeesWithClockInValues = dbHelper_main.getEmployeesWithClockInValues();
            editRVAdapter = new EmployeeLookupAdapter(employeesWithClockInValues, employee_lookup.this);
            // setting our adapter to recycler view.
            recyclerView.setAdapter(editRVAdapter);
            Toast.makeText(getApplicationContext(), employeesWithClockInValues.toString(), Toast.LENGTH_LONG).show();
        }
        if ("On Break".equals(courses[position])) {
            ArrayList<Employee> employeesOnBreak = dbHelper_main.getEmployeesOnBreak();
            editRVAdapter = new EmployeeLookupAdapter(employeesOnBreak, employee_lookup.this);
            recyclerView.setAdapter(editRVAdapter);
            Toast.makeText(getApplicationContext(), employeesOnBreak.toString(), Toast.LENGTH_LONG).show();
        }
        if ("clocked Out".equals(courses[position])) {
            ArrayList<Employee> employeesFinishedShift = dbHelper_main.getEmployeesFinishedShift();
            editRVAdapter = new EmployeeLookupAdapter(employeesFinishedShift, employee_lookup.this);
            recyclerView.setAdapter(editRVAdapter);
            Toast.makeText(getApplicationContext(), employeesFinishedShift.toString(), Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
