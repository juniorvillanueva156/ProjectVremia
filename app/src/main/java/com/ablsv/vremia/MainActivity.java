package com.ablsv.vremia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CustomAdapter cadapter;
    TextView nodataText;
    DatabaseHelper myDB;
    ArrayList<String> task_id,
            task_title,
            task_description,
            task_color,
            task_year,
            task_month,
            task_dayofmonth,
            task_hour,
            task_minute,
            task_imagedata;

    ImageView deletetasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addtaskbtnfloat = findViewById(R.id.addtaskbtnfloat);
        recyclerView = findViewById(R.id.main_recyclerview);
        nodataText = findViewById(R.id.no_data);
        addtaskbtnfloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTask.class);
                startActivity(intent);
                finish();
            }
        });
        deletetasks = (ImageView) findViewById(R.id.delete_tasks);
        deletetasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete All Tasks?")
                        .setIcon(R.drawable.baseline_delete_forever_24)
                        .setMessage("Do you want to delete all tasks? This operation cannot be undone.")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseHelper myDB = new DatabaseHelper(MainActivity.this);
                                myDB.deleteAllData();
                                dialogInterface.dismiss();

                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.show();
            }
        });

        myDB = new DatabaseHelper(MainActivity.this);
        task_title = new ArrayList<>();
        task_description = new ArrayList<>();
        task_color = new ArrayList<>();
        task_year = new ArrayList<>();
        task_month = new ArrayList<>();
        task_dayofmonth = new ArrayList<>();
        task_hour = new ArrayList<>();
        task_minute = new ArrayList<>();
        task_imagedata = new ArrayList<>();
        task_id = new ArrayList<>();

        storedatainarrays();

        cadapter = new CustomAdapter(MainActivity.this, this, task_id, task_title, task_description, task_color, task_year, task_month, task_dayofmonth, task_hour, task_minute, task_imagedata);
        recyclerView.setAdapter(cadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        //TODO: Perform debugging at application build and run, spot errors, fix shit, ask stackov and cgpt, etc.
    }
    void storedatainarrays()
    {
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            nodataText.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                task_id.add(cursor.getString(0));
                task_title.add(cursor.getString(1));
                task_description.add(cursor.getString(2));
                task_color.add(cursor.getString(3));
                task_year.add(cursor.getString(4));
                task_month.add(cursor.getString(5));
                task_dayofmonth.add(cursor.getString(6));
                task_hour.add(cursor.getString(7));
                task_minute.add(cursor.getString(8));
                task_imagedata.add(cursor.getString(9));

            }
            nodataText.setVisibility(View.GONE);
        }


    }


}