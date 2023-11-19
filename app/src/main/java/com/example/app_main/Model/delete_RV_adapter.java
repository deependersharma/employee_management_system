package com.example.app_main.Model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_main.Database.Login_DB_helper;
import com.example.app_main.Views.Manager_menu;
import com.example.app_main.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class delete_RV_adapter extends RecyclerView.Adapter<delete_RV_adapter.ViewHolder> {

    Context context;
    Login_DB_helper dbHelper;
    private ArrayList<Employees_model> model;
    private List<Bitmap> imageList;

    public delete_RV_adapter(ArrayList<Employees_model> model, Context context)
    {
        this.context=context;
        this.model=model;
        dbHelper= new Login_DB_helper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.edit_employee_items,parent,false);
        return  new delete_RV_adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employees_model model1= model.get(position);
        holder.textView.setText(model1.getEmployeeName());

        Bitmap bitmap = BitmapFactory.decodeByteArray(model1.getImage(), 0, model1.getImage().length);
        holder.imageView.setImageBitmap(bitmap);


        // below line is to add on click listener for our recycler view item.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                View dialogView = LayoutInflater.from(context).inflate(R.layout.employee_info_dialog, null);
                TextView nameTextView = dialogView.findViewById(R.id.dialog_textView);
                TextView employeeIDTextView = dialogView.findViewById(R.id.dialog_textView_employeeId);
                TextView employeeRoleTextView = dialogView.findViewById(R.id.dialog_textView_role);
                nameTextView.setText("Employee Name: " + model1.getEmployeeName());
                employeeIDTextView.setText("Employee ID: " +model1.getEmployeeId());
                employeeRoleTextView.setText("Employee Role: " +model1.getEmployeeRole());


                builder.setView(dialogView);
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Remove the item from the dataset
                                model.remove(position);

                                // Notify the adapter that the dataset has changed
                                notifyItemRemoved(position);
                                dbHelper.deleteEmployee(model1.getEmployeeId());
                                Toast.makeText(v.getContext(), "Employee Deleted", Toast.LENGTH_SHORT).show();
                                if(getItemCount()==0)
                                {
                                    Intent i=new Intent(v.getContext(), Manager_menu.class);
                                    v.getContext().startActivity(i);
                                    if (v.getContext() instanceof Activity) {
                                        ((Activity) v.getContext()).finish();
                                    }
                                }


                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return model.size();
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
