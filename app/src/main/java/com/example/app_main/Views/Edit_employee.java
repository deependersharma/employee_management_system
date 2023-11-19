package com.example.app_main.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.example.app_main.Database.Login_DB_helper;
import com.example.app_main.Model.Edit_RV_adapter;
import com.example.app_main.Model.Employees_model;
import com.example.app_main.R;

import java.util.ArrayList;
import java.util.List;

public class Edit_employee extends AppCompatActivity {
private RecyclerView recyclerView;
private ArrayList<Employees_model> model;
private List<Bitmap> imageList;
private Edit_RV_adapter editRVAdapter;
private Login_DB_helper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);
        recyclerView= findViewById(R.id.recyclerview);
        model= new ArrayList<>();
        dbHelper= new Login_DB_helper(Edit_employee.this);
        model= dbHelper.readData();
        editRVAdapter = new Edit_RV_adapter(model, Edit_employee.this);
        // setting our adapter to recycler view.
        recyclerView.setAdapter(editRVAdapter);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Edit_employee.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        if(editRVAdapter.getItemCount()==0){
            Intent i=new Intent(this
                    , Manager_menu.class
            );
            startActivity(i);
            Toast.makeText(this, "no record found",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent i=new Intent(this
        , Manager_menu.class
        );
        startActivity(i);
    }
}