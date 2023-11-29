package com.example.app_main.Views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_main.Database.DB_helper;
import com.example.app_main.Database.Login_DB_helper;
import com.example.app_main.Model.Employees_model;
import com.example.app_main.R;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;



public class Generate_reports extends AppCompatActivity {

    private ArrayList<Employees_model> model;
    private Login_DB_helper dbHelper;
    private DB_helper dbHelper2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_reports);

        dbHelper = new Login_DB_helper(this);
        dbHelper2 = new DB_helper(this);
        model = dbHelper.readData();

        Spinner employeeSpinner = findViewById(R.id.employee_reports_spinner);
        Button btnGeneratePDF = findViewById(R.id.btn_generate_pdf);

        ArrayList<String> employeeNames = new ArrayList<>();
        for (Employees_model employee : model) {
            employeeNames.add(employee.getEmployeeName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, employeeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeSpinner.setAdapter(adapter);

        btnGeneratePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = employeeSpinner.getSelectedItemPosition();
                Employees_model selectedEmployee = model.get(position);
                String totalHours = dbHelper2.getTotalHoursForEmployee(selectedEmployee.getEmployeeId());
                generatePDF(selectedEmployee, totalHours);
            }
        });
    }

    private void generatePDF(Employees_model employee, String totalHours) {
        try {
            String[] hoursMinutes = totalHours.split(":");
            int hours = Integer.parseInt(hoursMinutes[0]);
            int minutes = Integer.parseInt(hoursMinutes[1]);

            // Calculate total earnings
            double hourlyRate = Double.parseDouble(employee.getHourly_rate());
            double totalEarnings = (hours + minutes / 60.0) * hourlyRate;

            File pdfFile = new File(getExternalFilesDir(null), "EmployeeReport.pdf");
            pdfFile.createNewFile();
            PdfWriter pdfWriter = new PdfWriter(new FileOutputStream(pdfFile));
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            float fontSize = 20f; // You can adjust the font size as needed
            document.setFontSize(fontSize);

            document.add(new Paragraph("Employee Report\n"));
            document.add(new Paragraph("Employee Name: " + employee.getEmployeeName()));
            document.add(new Paragraph("Employee ID: " + employee.getEmployeeId()));
            document.add(new Paragraph("Hourly Rate: " + employee.getHourly_rate()));
            document.add(new Paragraph("Total Hours: " + totalHours));
            document.add(new Paragraph("Gross Salary: $" +totalEarnings));

            document.close();

            Toast.makeText(this, "PDF generated successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error generating PDF", Toast.LENGTH_SHORT).show();
        }
    }
}

