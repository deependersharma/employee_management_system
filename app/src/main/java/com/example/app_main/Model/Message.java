package com.example.app_main.Model;

public class Message {
    private int id;
    private String userId;
    private String messageText;

    // Constructor with parameters
    public Message(int id, String userId, String messageText) {
        this.id = id;
        this.userId = userId;
        this.messageText = messageText;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getMessageText() {
        return messageText;
    }
}


