package com.example.app_main.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.app_main.Model.main_message_adapter;
import com.example.app_main.Model.Message;
import com.example.app_main.R;

import java.util.ArrayList;
import java.util.List;

public class Get_assigned_videos extends AppCompatActivity {
    private RecyclerView recyclerView;
    private main_message_adapter messageAdapter;
    private List<Message> messages;
    String temp1;
    Video_link_DB videoLinkDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_assigned_videos);
        temp1 = getIntent().getStringExtra("employeeIdentifier");
        // Assuming you have a RecyclerView with the id "recyclerView" in your activity_main.xml
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        videoLinkDB = new Video_link_DB(this);
        // Initialize your messages list with some sample data
        messages = new ArrayList<>();
        messages= videoLinkDB.readMessagesByUserId(temp1);

        if(messages.size()==0){
            showAlertDialog();
        }
        // Create an instance of the MessageAdapter and set it to the RecyclerView
        messageAdapter = new main_message_adapter(messages);
        recyclerView.setAdapter(messageAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(Get_assigned_videos.this, MainActivity.class);
        startActivity(i);
        finish();
    }
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No message found")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i=new Intent(Get_assigned_videos.this, MainActivity.class);
                        startActivity(i);
                        finish();
                        dialog.dismiss();
                    }
                });

        // Create the AlertDialog object and show it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}