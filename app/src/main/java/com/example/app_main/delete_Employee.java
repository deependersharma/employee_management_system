package com.example.app_main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class delete_Employee extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Employees_Model> model;
    private List<Bitmap> imageList;
    private deleteRVAdapter deleteRVAdapter;
    private LoginDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_employee);
        recyclerView= findViewById(R.id.recyclerview);
        model= new ArrayList<>();
        dbHelper= new LoginDBHelper(delete_Employee.this);
        model= dbHelper.readData();
        deleteRVAdapter = new deleteRVAdapter(model, delete_Employee.this);
        // setting our adapter to recycler view.
        recyclerView.setAdapter(deleteRVAdapter);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(delete_Employee.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        if(deleteRVAdapter.getItemCount()==0){
            Intent i=new Intent(this
                    , Manager_menu.class
            );
            startActivity(i);
            Toast.makeText(this, "no record found",Toast.LENGTH_SHORT).show();
        }

    }
}