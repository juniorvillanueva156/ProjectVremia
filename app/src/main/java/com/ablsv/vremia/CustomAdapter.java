package com.ablsv.vremia;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Activity activity;
    private Context context;
    private ArrayList task_id, task_title, task_description, task_color, task_year, task_month, task_dayofmonth, task_hour, task_minute, task_imagedata;
    private android.text.format.DateFormat df;

    CustomAdapter(Activity activity, Context context, ArrayList id, ArrayList title, ArrayList description, ArrayList color, ArrayList year, ArrayList month, ArrayList dayofmonth, ArrayList hour, ArrayList minute, ArrayList imagedata)
    {
        this.activity = activity;
        this.task_id = id;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title_task.setText((CharSequence) task_title.get(position));
        holder.color_task.setBackgroundColor(Integer.parseInt(String.valueOf(task_color.get(position))));

        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        int nichi = Integer.parseInt(String.valueOf(task_dayofmonth.get(position)));
        int gatsu = Integer.parseInt(String.valueOf(task_month.get(position)));
        int nen = Integer.parseInt(String.valueOf(task_year.get(position)));

        c.set(nen, gatsu, nichi);

                String presentDay = format.format(c.getTime());

                @SuppressLint("DefaultLocale")
                String presentTime = String.format("%02d:%02d",
                Integer.parseInt(String.valueOf(task_hour.get(position))),
                Integer.parseInt(String.valueOf(task_minute.get(position))));

                holder.date_task.setText(presentDay +", "+ presentTime);

        String base64String = String.valueOf(task_imagedata.get(position));
        byte[] byteArray = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);


        holder.image_task.setImageBitmap(bitmap);

        //TODO: Set Custom View in Main Activity, run the app and debug.
    }

    @Override
    public int getItemCount() {
        return task_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView title_task, date_task;
        CircleImageView image_task;
        RelativeLayout color_task;
        LinearLayout mainlayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title_task = itemView.findViewById(R.id.tasktitleView);
            color_task = itemView.findViewById(R.id.customtaskview);
            image_task = itemView.findViewById(R.id.taskimageView);
            date_task = itemView.findViewById(R.id.taskdatetimeView);


            mainlayout = itemView.findViewById(R.id.mainLayout);
        }
    }

}
