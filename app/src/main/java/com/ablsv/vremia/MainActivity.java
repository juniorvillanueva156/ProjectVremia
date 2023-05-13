package com.ablsv.vremia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addtaskbtnfloat = findViewById(R.id.addtaskbtnfloat);
        recyclerView = findViewById(R.id.main_recyclerview);
        addtaskbtnfloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTask.class);
                startActivity(intent);
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

        //TODO: Code a Custom Adapter and bind it to RecyclerView.
        //TODO: Successfully add a task and show it on menu.
    }
}