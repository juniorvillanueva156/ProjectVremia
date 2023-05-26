package com.ablsv.vremia;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.io.File;
import java.text.DateFormat;
import android.util.Base64;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import io.kredibel.picker.Picker;
import io.kredibel.picker.PickerListener;

public class EditTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

        Button save_changes, delete_task, editTaskOpenDatePicker;
        Bitmap imagebitmap;
        String id, title, description, presentDay, presentTime;
        int taskColor;
        ImageView cancelbtn;
        ImageView editTaskImageSelector;
        EditText editTaskTitleInput, editTaskDescriptionInput;
        TextView editTaskDatePreview, edittasksettaskcolortext, edittaskcolorpickerpreview;

        String taskID, taskTITLE, taskDESCR, taskCOLOR, taskPDPT, taskIMAGEDATA;

        public void getTaskInformation(String id, Bitmap imagedata, String title, String description, String presentDay, String presentTime, int taskColor)
        {
            this.id = id;
            this.imagebitmap = imagedata;
            this.title = title;
            this.description = description;
            this.presentDay = presentDay;
            this.presentTime = presentTime;
            this.taskColor = taskColor;
        }
        public void setData()
        {
            editTaskImageSelector.setImageBitmap(imagebitmap);
            //debug first and check if working
        }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        save_changes = findViewById(R.id.savebtnedittask);
        editTaskOpenDatePicker = findViewById(R.id.editTaskOpenDatePicker);
        delete_task = findViewById(R.id.deletebtnedittask);
        editTaskImageSelector = findViewById(R.id.editTaskImageSelector);
        editTaskDescriptionInput = findViewById(R.id.editTaskDescriptionInput);
        editTaskDatePreview = findViewById(R.id.editTaskDatePreview);
        edittasksettaskcolortext = findViewById(R.id.edittasksettaskcolortext);
        editTaskTitleInput = findViewById(R.id.editTaskTitleInput);
        edittaskcolorpickerpreview = findViewById(R.id.edittaskcolorpicker_preview);
        cancelbtn = findViewById(R.id.canceledittasktoolbar);

        Picker imagepicker = new Picker(EditTask.this);

        setData();

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert2 = new AlertDialog.Builder(EditTask.this);
                alert2.setTitle("Confirm");
                alert2.setIcon(R.drawable.baseline_warning_amber_24);
                alert2.setMessage("You have an unsaved task. Are you sure you want to exit?");
                alert2.setNegativeButton("No", null);
                alert2.setPositiveButton("Yes (Discard Task)", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();

                        Intent intentToMain = new Intent(EditTask.this, MainActivity.class);
                        startActivity(intentToMain);
                        finish();
                    }
                });
                alert2.show();
            }
        });

        editTaskImageSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagepicker.pickGallery(new PickerListener() {
                    @Override
                    public void onPicked(Uri uri, File file, Bitmap bitmap) {
                        String imagepath = file.getAbsolutePath();
                        Bitmap bmp = BitmapFactory.decodeFile(imagepath);
                        editTaskImageSelector.setImageBitmap(bmp);
                        Toast.makeText(EditTask.this, "Image Successfully Added!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditTask.this, "Activity updated", Toast.LENGTH_SHORT).show();

            }
        });
        delete_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditTask.this);
                builder.setTitle("Delete the Task?");
                builder.setIcon(R.drawable.baseline_delete_forever_24);
                builder.setMessage("Do you want to delete this task?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseHelper dbhelp = new DatabaseHelper(EditTask.this);
                        dbhelp.deleteTask(id);
                        finish();
                        Intent intent = new Intent(EditTask.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("NO", null);

                builder.create().show();
            }
        });
        editTaskOpenDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timep = new TimePickerFragment();
                timep.show(getSupportFragmentManager(), "Time Picker");
                DialogFragment datep = new DatePickerFragment();
                datep.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        //TODO: FINISH THIS ACTIVITY AND RUN DEBUGS AND TESTS
    }
    int date_year, date_month, date_dayofmonth, time_hour, time_minute;

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayofmonth);
        String cdatestr = DateFormat.getDateInstance().format(c.getTime());

        editTaskDatePreview.setText("Input: " + cdatestr);
        presentDay = cdatestr;

        date_year = year;
        date_month = month;
        date_dayofmonth = dayofmonth;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        String currentTime = String.format("%02d:%02d", hour, minute);
        editTaskDatePreview.setText(editTaskDatePreview.getText().toString() + ", " + currentTime);
        presentTime = currentTime;

        time_hour = hour;
        time_minute = minute;
    }

    public static String toHex(int i) {
        long unsignedDecimal = i & 0xFFFFFFFFL;
        String newcolor_hex = Long.toHexString(unsignedDecimal);
        newcolor_hex.length();
        return newcolor_hex.toUpperCase();
    }
}
