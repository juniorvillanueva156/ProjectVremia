package com.ablsv.vremia;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import android.util.Base64;
import java.util.Calendar;
import java.util.Locale;

import io.kredibel.picker.Picker;
import io.kredibel.picker.PickerListener;
import yuku.ambilwarna.AmbilWarnaDialog;

public class AddTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    Button openDateTimePicker;
    EditText colorpickerhexinput, tasktitleinput, taskdescrinput;
    Button colorpickerbtn;
    TextView colorpicker_preview;
    TextView dateTimePreview;
    ImageView imageSelector_AddTask;
    ImageView savebtn, cancelbtn;
    int DefaultColor, taskColor;

    private static final int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle sis) {
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
        TextView taskcolortext = findViewById(R.id.settaskcolortext);
        Picker imagepick = new Picker(AddTask.this);
        tasktitleinput = findViewById(R.id.tasktitleinput);
        taskdescrinput = findViewById(R.id.taskdescrinput);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                Drawable imageDrawable = imageSelector_AddTask.getDrawable();
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageDrawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] imageBytes = stream.toByteArray();

                String imageData = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                DatabaseHelper svl = new DatabaseHelper(AddTask.this);
                svl.addTask(tasktitleinput.getText().toString(), taskdescrinput.getText().toString(), taskColor, date_year, date_month, date_dayofmonth, time_hour, time_minute, imageData);

                Toast.makeText(AddTask.this, "Your task is created.", Toast.LENGTH_SHORT).show();

                Intent intentToMain = new Intent(AddTask.this, MainActivity.class);
                startActivity(intentToMain);

                finish();
            }
        });
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(AddTask.this);
                alert.setTitle("Confirm");
                alert.setIcon(R.drawable.baseline_warning_amber_24);
                alert.setMessage("You have an unsaved task. Are you sure you want to exit?");
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alert.setPositiveButton("Yes (Discard Task)", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                alert.show();
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
                imagepick.pickGallery(new PickerListener() {
                    @Override
                    public void onPicked(Uri uri, File file, Bitmap bitmap) {
                        String imagepath = file.getAbsolutePath();
                        Bitmap bmap = BitmapFactory.decodeFile(imagepath);
                        imageSelector_AddTask.setImageBitmap(bmap);
                        Toast.makeText(AddTask.this, "Image successfully added", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        colorpickerhexinput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddTask.this, "Type the hexadecimal value of your preferred color\nwithout # and alpha values.", Toast.LENGTH_SHORT).show();
            }
        });
        colorpickerhexinput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String colorinput;
                int colorinput2;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    colorinput = textView.getText().toString();
                    if (colorinput == null) {
                        colorinput = "000000";

                    }
                    if (colorinput.length() >= 7 || colorinput.length() <= 5 || !colorinput.matches("-?[0-9a-fA-F]+")) {
                        colorinput = "000000";

                    }
                    taskColor = Integer.parseInt(colorinput, 16);
                    colorinput2 = Color.parseColor("#FF" + colorinput);
                    colorpicker_preview.setBackgroundColor(colorinput2);
                    DefaultColor = colorinput2;
                    taskcolortext.setText(colorinput);
                    return true;
                } else {
                    return false;
                }


            }
        });
        colorpickerhexinput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String colorinput;
                int colorinput2;
                if (!b) {
                    colorpickerhexinput = (EditText) view;
                    colorinput = colorpickerhexinput.getText().toString();
                    if (colorinput == null) {
                        colorinput = "000000";

                    }
                    if (colorinput.length() >= 7 || colorinput.length() <= 5 || !colorinput.matches("-?[0-9a-fA-F]+")) {
                        colorinput = "000000";

                    }
                    taskColor = Integer.parseInt(colorinput, 16);
                    colorinput2 = Color.parseColor("#FF" + colorinput);
                    colorpicker_preview.setBackgroundColor(colorinput2);
                    DefaultColor = colorinput2;
                    taskcolortext.setText(colorinput);
                }
            }
        });

        colorpickerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });


        //End of onCreate method
    }

    String presentDay, presentTime;
    int date_year, date_month, date_dayofmonth, time_hour, time_minute;

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayofmonth);
        String cdatestr = DateFormat.getDateInstance().format(c.getTime());
        dateTimePreview.setText("Input: " + cdatestr);
        presentDay = cdatestr;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        String currentTime = String.format("%02d:%02d", hour, minute);
        dateTimePreview.setText(dateTimePreview.getText().toString() + ", " + currentTime);
        presentTime = currentTime;

    }

    public void openColorPicker() {
        AmbilWarnaDialog colorpicker = new AmbilWarnaDialog(this, DefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                taskColor = color;
                String newColor_hexadecimal = toHex(color);
                TextView taskcolortext = findViewById(R.id.settaskcolortext);

                int newColor = Color.parseColor("#" + newColor_hexadecimal);
                colorpicker_preview.setBackgroundColor(newColor);
                DefaultColor = newColor;
                taskcolortext.setText(newColor_hexadecimal);

            }
        });
        colorpicker.show();
    }

    public static String toHex(int i) {
        long unsignedDecimal = i & 0xFFFFFFFFL;
        String newcolor_hex = Long.toHexString(unsignedDecimal);
        newcolor_hex.length();
        return newcolor_hex.toUpperCase();
    }


    //End of AddTask Class
}
