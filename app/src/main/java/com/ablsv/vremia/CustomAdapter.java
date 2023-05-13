package com.ablsv.vremia;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Activity activity;
    private Context context;
    private ArrayList task_title, task_description, task_color, task_year, task_month, task_dayofmonth, task_hour, task_minute, task_imagedata;

    CustomAdapter(Activity activity, Context context, ArrayList title, ArrayList description, ArrayList color, ArrayList year, ArrayList month, ArrayList dayofmonth, ArrayList hour, ArrayList minute, ArrayList imagedata)
    {
        this.activity = activity;
        this.context = context;
        this.task_title = title;
        this.task_description = description;
        this.task_color = color;
        this.task_year = year;
        this.task_month = month;
        this.task_dayofmonth = dayofmonth;
        this.task_hour = hour;
        this.task_minute = minute;
        this.task_imagedata = imagedata;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.taskdisplay, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
