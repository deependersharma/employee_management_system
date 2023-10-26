package com.example.app_main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Edit_employee extends AppCompatActivity {
private RecyclerView recyclerView;
private ArrayList<Employees_Model> model;
private List<Bitmap> imageList;
private EditRVAdapter editRVAdapter;
private LoginDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);
        recyclerView= findViewById(R.id.recyclerview);
        model= new ArrayList<>();
        dbHelper= new LoginDBHelper(Edit_employee.this);
        model= dbHelper.readData();
        editRVAdapter = new EditRVAdapter(model, Edit_employee.this);
        // setting our adapter to recycler view.
        recyclerView.setAdapter(editRVAdapter);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Edit_employee.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}