package com.example.app_main.Model;

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

import com.example.app_main.R;
import com.example.app_main.Views.Update_employee;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Edit_RV_adapter extends RecyclerView.Adapter<Edit_RV_adapter.ViewHolder> {

    private Context context;
    private ArrayList<Employees_model> model;

    public Edit_RV_adapter(ArrayList<Employees_model> model, Context context) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.edit_employee_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employees_model model1 = model.get(position);

        // Capitalize the first letter of the employee name
        String originalText = model1.getEmployeeName();
        String capitalizedText = originalText.substring(0, 1).toUpperCase() + originalText.substring(1).toLowerCase();
        holder.textView.setText(capitalizedText);

        // Set the employee ID
        holder.textEmployeeId.setText(model1.getEmployeeId());

        // Set the employee image
        Bitmap bitmap = BitmapFactory.decodeByteArray(model1.getImage(), 0, model1.getImage().length);
        holder.imageView.setImageBitmap(bitmap);

        // Compress the image for passing to the next activity
        byte[] compressedByteArray = compressBitmap(model1.getImage());

        // Set onClickListener for the RecyclerView item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent for the next activity
                Intent i = new Intent(context, Update_employee.class);

                // Pass values to the intent
                i.putExtra("id", model1.getEmployeeId());
                i.putExtra("name", model1.getEmployeeName());
                i.putExtra("image", compressedByteArray);
                i.putExtra("role", model1.getEmployeeRole());
                i.putExtra("joining_date", model1.getJoining_date());
                i.putExtra("hourly_rate", model1.getHourly_rate());

                // Start the activity
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView, textEmployeeId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textEmployeeId = itemView.findViewById(R.id.text_employee_id_editable);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text_name);
        }
    }

    // Helper method to compress the Bitmap
    private byte[] compressBitmap(byte[] imageByteArray) {
        Bitmap originalBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        int compressionQuality = 50;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        originalBitmap.compress(Bitmap.CompressFormat.JPEG, compressionQuality, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
