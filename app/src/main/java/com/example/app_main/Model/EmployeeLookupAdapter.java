package com.example.app_main.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_main.Database.Employee;
import com.example.app_main.R;

import java.util.ArrayList;
import java.util.List;

public class EmployeeLookupAdapter extends RecyclerView.Adapter<EmployeeLookupAdapter.EmployeeViewHolder> {

    private ArrayList<Employee> employees;
    Context context;

    public EmployeeLookupAdapter(ArrayList<Employee> employees, Context context) {
        this.employees = employees;
        this.context= context;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_employee_items, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employees.get(position);

        // Set employee details to the TextViews
        holder.textViewId.setText("ID: " + employee.getId());
        holder.textViewName.setText("Name: " + employee.getName());
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewId;
        TextView textViewName;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.text_employee_id_editable);
            textViewName = itemView.findViewById(R.id.text_name);
        }
    }
}
