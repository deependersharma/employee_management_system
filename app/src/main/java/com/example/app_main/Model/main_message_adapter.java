package com.example.app_main.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_main.R;

import java.util.List;

public class main_message_adapter extends RecyclerView.Adapter<main_message_adapter.MessageViewHolder> {

    private List<Message> messages;

    public main_message_adapter(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message1 = messages.get(position);

        // Set the ID and message to the TextViews
        holder.textViewId.setText("ID: "+ message1.getId());
        holder.textViewMessage.setText("Message: "+ message1.getMessageText());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView textViewId;
        TextView textViewMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.textViewId);
            textViewMessage = itemView.findViewById(R.id.textViewMessage);
        }
    }
}