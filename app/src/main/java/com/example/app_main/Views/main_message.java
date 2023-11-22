package com.example.app_main.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.app_main.Database.Message_DB;
import com.example.app_main.Model.main_message_adapter;
import com.example.app_main.Model.Message;
import com.example.app_main.R;

import java.util.ArrayList;
import java.util.List;

public class main_message extends AppCompatActivity {
    private RecyclerView recyclerView;
        private main_message_adapter messageAdapter;
        private List<Message> messages;
        String temp1;
Message_DB messageDb;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_message);
            temp1 = getIntent().getStringExtra("employeeIdentifier");
            // Assuming you have a RecyclerView with the id "recyclerView" in your activity_main.xml
            recyclerView = findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            messageDb=new Message_DB(this);
            // Initialize your messages list with some sample data
            messages = new ArrayList<>();
            messages= messageDb.readMessagesByUserId(temp1);
            // Create an instance of the MessageAdapter and set it to the RecyclerView
            messageAdapter = new main_message_adapter(messages);
            recyclerView.setAdapter(messageAdapter);
        }
}