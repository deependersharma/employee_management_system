package com.example.app_main.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.app_main.Database.Employee;
import com.example.app_main.Model.Employee_lookup_adapter;
import com.example.app_main.Database.DB_helper;
import com.example.app_main.R;

import java.util.ArrayList;

public class Employee_lookup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spino;
    DB_helper dbHelper_main;
    Employee_lookup_adapter editRVAdapter;
    private ArrayList<Employee> model;
    RecyclerView recyclerView;
    // create array of Strings
    // and store the name of courses
    String[] courses = {"Clocked In", "On Break", "Clocked Out"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_lookup);

        recyclerView = findViewById(R.id.recyclerview);
        dbHelper_main = new DB_helper(this);

        // Initialize your RecyclerView and set its adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Employee_lookup.this, RecyclerView.VERTICAL, false);
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

        // Use .equals() for string comparison
        if ("clocked In".equals(courses[position])) {
            ArrayList<Employee> employeesWithClockInValues = dbHelper_main.getEmployeesWithClockInValues();
            editRVAdapter = new Employee_lookup_adapter(employeesWithClockInValues, Employee_lookup.this);
            // setting our adapter to recycler view.
            recyclerView.setAdapter(editRVAdapter);
        }
        if ("On Break".equals(courses[position])) {
            ArrayList<Employee> employeesOnBreak = dbHelper_main.getEmployeesOnBreak();
            editRVAdapter = new Employee_lookup_adapter(employeesOnBreak, Employee_lookup.this);
            recyclerView.setAdapter(editRVAdapter);
        }
        if ("clocked Out".equals(courses[position])) {
            ArrayList<Employee> employeesFinishedShift = dbHelper_main.getEmployeesFinishedShift();
            editRVAdapter = new Employee_lookup_adapter(employeesFinishedShift, Employee_lookup.this);
            recyclerView.setAdapter(editRVAdapter);
        }


    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
