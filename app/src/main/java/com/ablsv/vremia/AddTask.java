package com.ablsv.vremia;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import java.text.DateFormat;
import java.util.Calendar;

public class AddTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    Button openDateTimePicker;
    EditText colorpickerhexinput;
    Button colorpickerbtn;
    TextView colorpicker_preview;
    TextView dateTimePreview;
    de.hdodenhof.circleimageview.CircleImageView imageSelector_AddTask;
    ImageView savebtn, cancelbtn;

    @Override
    protected void onCreate(Bundle sis)
    {
        super.onCreate(sis);
        setContentView(R.layout.activity_addtask);

        openDateTimePicker = findViewById(R.id.button_opendateandtime);
        colorpickerhexinput = findViewById(R.id.colorpickerhexinput);
        colorpickerbtn = findViewById(R.id.colorpickerbtn);
        colorpicker_preview = findViewById(R.id.colorpicker_preview);
        imageSelector_AddTask = findViewById(R.id.imageSelector_addtask);
        dateTimePreview = findViewById(R.id.datetimepreview);
        savebtn = findViewById(R.id.saveaddtasktoolbar);
        cancelbtn = findViewById(R.id.canceladdtasktoolbar);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddTask.this, "Your task is created.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        openDateTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timep = new TimePickerFragment();
                timep.show(getSupportFragmentManager(), "Time Picker");
                DialogFragment datep = new DatePickerFragment();
                datep.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        colorpickerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imageSelector_AddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayofmonth);
        String cdatestr = DateFormat.getDateInstance().format(c.getTime());
        dateTimePreview.setText("Input: "+cdatestr);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        String currentTime = String.format("%02d:%02d", hour, minute);
        dateTimePreview.setText(dateTimePreview.getText().toString() +", "+ currentTime);

    }
}
