package com.example.app_main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        Bitmap bitmap = BitmapFactory.decodeByteArray(model1.getImage(), 0, model1.getImage().length);
    holder.imageView.setImageBitmap(bitmap);
        // below line is to add on click listener for our recycler view item.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on below line we are calling an intent.
                Intent i = new Intent(context, updateEmployee.class);

                // below we are passing all our values.
                i.putExtra("id", model1.getEmployeeId());
                i.putExtra("name", model1.getEmployeeName());
                i.putExtra("image", model1.getImage());
                i.putExtra("role", model1.getEmployeeRole());
                i.putExtra("joining_date", model1.getJoining_date());
                i.putExtra("hourly_rate", model1.getHourly_rate());

                // starting our activity.
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
return  model.size();
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
