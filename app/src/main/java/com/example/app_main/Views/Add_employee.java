package com.example.app_main.Views;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.app_main.Database.Login_DB_helper;
import com.example.app_main.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Add_employee extends AppCompatActivity {
    ImageView image_picker;
    TextView date_picker;
    Login_DB_helper dbHelper;
    String selectedDate;
    Button add_employee;
    byte[] imageByteArray;
    EditText id,username,role,hourly_rate;
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        // register the UI widgets with their appropriate IDs
        image_picker = findViewById(R.id.image_picker);
        date_picker= findViewById(R.id.date_picker);
        add_employee= findViewById(R.id.btn_add_employee);
        id=findViewById(R.id.employee_id);
        username=findViewById(R.id.employee_name);
        role=findViewById(R.id.employee_role);
        hourly_rate=findViewById(R.id.employee_salary);

        dbHelper = new Login_DB_helper(this);
        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        Add_employee.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                date_picker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);


                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        // handle the Choose Image button to trigger
        // the image chooser function
        image_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        add_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String employeeId = id.getText().toString().trim();
                String employeeUsername = username.getText().toString().trim();
                String employeeRole = role.getText().toString().trim();
                String employeeHourlyRate = hourly_rate.getText().toString().trim();

                // Check if any of the fields is empty
                if (employeeId.isEmpty() || employeeUsername.isEmpty() || employeeRole.isEmpty() || employeeHourlyRate.isEmpty() || selectedDate == null || imageByteArray == null) {
                    Toast.makeText(getApplicationContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                          return; // Exit the method if any field is empty
                }

                if (employeeId.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Employee ID cannot be less than 6 digits", Toast.LENGTH_SHORT).show();
                    return;
                }
                dbHelper.Insert(id.getText().toString(),username.getText().toString(), imageByteArray, selectedDate  ,role.getText().toString(),hourly_rate.getText().toString());
                Toast.makeText(getApplicationContext(),"Employee Added!" ,Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Add_employee.this, Manager_menu.class);
                startActivity(i);
                finish();

            }
        });
    }

    // this function is triggered when
    // the Select Image Button is clicked
    private void imageChooser()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        image_picker.setImageBitmap(
                                selectedImageBitmap);
                        // Convert the bitmap to a byte array (you can use other methods like Base64 encoding as well).
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        imageByteArray = stream.toByteArray();

                    }
                }
            });
}