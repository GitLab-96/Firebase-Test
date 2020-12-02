package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Student> {

    private Activity context;
    private List<Student> studentList;

    public CustomAdapter(Activity context, List<Student> studentList) {
        super(context, R.layout.sample_layout, studentList);
        this.context = context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.sample_layout,null,true);

        Student student = studentList.get(position);

        TextView  textView1 = view.findViewById(R.id.nameTextViewId);
        TextView textView2 = view.findViewById(R.id.ageTextViewId);

        textView1.setText(String.format("Name : %s", student.getName()));
        textView2.setText(String.format("Age : %s", student.getAge()));

        return view;


    }
}
