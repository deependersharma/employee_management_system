package com.example.app_main.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.app_main.Database.Login_DB_helper;
import com.example.app_main.Model.Employees_model;
import com.example.app_main.R;

import java.util.ArrayList;
import java.util.List;

public class Assign_videos extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    EditText message;
    private Login_DB_helper dbHelper;
    private Video_link_DB videoLinkDB;
    List<String> employeeNames,employeeId;
    private ArrayList<Employees_model> model;
    Button send_btn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_videos);
        message= findViewById(R.id.editText_video_link);
        send_btn= findViewById(R.id.btn_assign_videos);
        spinner = findViewById(R.id.assign_videos_spinner);

        model= new ArrayList<>();
        employeeNames = new ArrayList<>();
        employeeId = new ArrayList<>();

        dbHelper= new Login_DB_helper(this);
        videoLinkDB=new Video_link_DB(this);
        model= dbHelper.readData();


        for (Employees_model employee : model) {
            employeeNames.add(employee.getEmployeeName());
        }
        for (Employees_model employeeId1 : model) {
            employeeId.add(employeeId1.getEmployeeId());
        }
        // Take the instance of Spinner and
        // apply OnItemSelectedListener on it which
        // tells which item of spinner is clicked

        spinner.setOnItemSelectedListener(this);

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
        spinner.setAdapter(ad);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoLinkDB.Insert(employeeId.get(position).toString(), message.getText().toString());
                Toast.makeText(getApplicationContext(),
                                employeeNames.get(position)+" "+ employeeId.get(position),
                                Toast.LENGTH_LONG)
                        .show();
                Intent i = new Intent(Assign_videos.this, Manager_menu.class);
                startActivity(i);
                finish();
            }
        });

    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }
}