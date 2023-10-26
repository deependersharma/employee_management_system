package com.example.app_main;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class EditRVAdapter extends RecyclerView.Adapter<EditRVAdapter.ViewHolder>{

    Context context;
    private ArrayList<Employees_Model> model;
    private List<Bitmap> imageList;
public EditRVAdapter(ArrayList<Employees_Model> model, Context context)
{
    this.context=context;
    this.model=model;
}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.edit_employee_items,parent,false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Employees_Model model1= model.get(position);
    holder.textView.setText(model1.getEmployeeName());
    System.out.println(model1.getEmployeeName());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.image);
            textView= itemView.findViewById(R.id.text_name);

        }
    }
}
