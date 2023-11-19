package com.example.app_main;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class updateEmployee extends AppCompatActivity {
    ImageView update_image_picker;
    TextView update_date_picker;
    LoginDBHelper dbHelper;
    String selectedDate;
    Button add_employee;
    byte[] imageByteArray;
    EditText update_id,update_username,update_role,update_hourly_rate;

    String id1, username1, role1, rate, date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);
        update_image_picker = findViewById(R.id.image_picker);
        update_date_picker= findViewById(R.id.update_date);
        add_employee= findViewById(R.id.btn_update_employee);
        update_id=findViewById(R.id.update_employee_id);
        update_username=findViewById(R.id.update_employee_name);
        update_role=findViewById(R.id.update_employee_role);
        update_hourly_rate=findViewById(R.id.update_employee_rate);

        dbHelper = new LoginDBHelper(this);

        id1 = getIntent().getStringExtra("id");
        username1 = getIntent().getStringExtra("name");
        role1 = getIntent().getStringExtra("role");
        rate = getIntent().getStringExtra("hourly_rate");
        selectedDate= getIntent().getStringExtra("joining_date");
        imageByteArray= getIntent().getByteArrayExtra("image");

        update_id.setText(id1);
        update_username.setText(username1);
        update_role.setText(role1);
        update_date_picker.setText(selectedDate);
        update_hourly_rate.setText(rate);

        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        update_image_picker.setImageBitmap(bitmap);
        update_date_picker.setOnClickListener(new View.OnClickListener() {
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
                        updateEmployee.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                update_date_picker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);


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
        update_image_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        add_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.updateEmployee(id1,update_id.getText().toString(),update_username.getText().toString(), imageByteArray, selectedDate  ,update_role.getText().toString(),update_hourly_rate.getText().toString());
                Toast.makeText(getApplicationContext(),"Employee Added!" ,Toast.LENGTH_SHORT).show();
                Intent i=new Intent(updateEmployee.this, Edit_employee.class);
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
                        update_image_picker.setImageBitmap(
                                selectedImageBitmap);
                        // Convert the bitmap to a byte array (you can use other methods like Base64 encoding as well).
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        imageByteArray = stream.toByteArray();

                    }
                }
            });
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    }