package com.example.android.softlab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pk on 10/31/2017.
 */

public class StudentAdapter extends ArrayAdapter<Student> {

    private final Context context;

    public StudentAdapter(Context context, List<Student> students) {
        super(context, 0, students);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.studentinfo_layout, parent, false);
        }

        Student currentStudent = getItem(position);

        TextView regno, sname, course, pno;
        regno = (TextView) listItemView.findViewById(R.id.studentRegNo);
        sname = (TextView) listItemView.findViewById(R.id.studentName);
        course = (TextView) listItemView.findViewById(R.id.studentCourse);
        pno = (TextView) listItemView.findViewById(R.id.studentPhoneNumber);


        regno.setText(currentStudent.getRegNo());
        sname.setText(currentStudent.getName());
        course.setText(currentStudent.getCourse());
        pno.setText(currentStudent.getPhoneNo());


        return listItemView;
    }


}
