package com.example.app_main.Views;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.app_main.Database.Login_DB_helper;
import com.example.app_main.Database.Message_DB;
import com.example.app_main.Model.Employees_model;
import com.example.app_main.R;

import java.util.ArrayList;
import java.util.List;

public class Send_message extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spino;
    EditText message;
    private Login_DB_helper dbHelper;
    private Message_DB messageDb;
    List<String> employeeNames,employeeId;
    private ArrayList<Employees_model> model;
    Button send_btn;
    // create array of Strings
    // and store name of courses
    String[] courses = {"C", "Data structures",
            "Interview prep", "Algorithms",
            "DSA with java", "OS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
message= findViewById(R.id.editTextTextMultiLine);
send_btn= findViewById(R.id.button3);
        model= new ArrayList<>();
        dbHelper= new Login_DB_helper(this);
        messageDb=new Message_DB(this);
        model= dbHelper.readData();
        employeeNames = new ArrayList<>();
        employeeId = new ArrayList<>();
        for (Employees_model employee : model) {
            employeeNames.add(employee.getEmployeeName());
        }
        for (Employees_model employeeId1 : model) {
            employeeId.add(employeeId1.getEmployeeId());
        }
        // Take the instance of Spinner and
        // apply OnItemSelectedListener on it which
        // tells which item of spinner is clicked
        spino = (Spinner) findViewById(R.id.employee_select_spinner);
        spino.setOnItemSelectedListener(this);

        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter<String> ad
                = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                employeeNames);

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
        // Log the name of the selected employee for debugging
        Log.d("EmployeeName", employeeNames.get(position));

        // Make a toast of the name of the selected employee from the spinner
        Toast.makeText(getApplicationContext(),
                        employeeNames.get(position)+" "+ employeeId.get(position),
                        Toast.LENGTH_LONG)
                .show();
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageDb.Insert(employeeId.get(position).toString(), message.getText().toString());
                Toast.makeText(getApplicationContext(),
                                employeeNames.get(position)+" "+ employeeId.get(position),
                                Toast.LENGTH_LONG)
                        .show();
                Intent i = new Intent(Send_message.this, Manager_menu.class);
                startActivity(i);
                finish();
            }
        });

    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }
}