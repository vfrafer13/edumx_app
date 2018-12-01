package com.example.nezzi.edumx.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.nezzi.edumx.R;
import com.example.nezzi.edumx.models.Course;

import java.util.List;

/**

 * TODO: Replace the implementation with code for your data type.
 */

public class MyCoursesRecyclerViewAdapter extends RecyclerView.Adapter<MyCoursesRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Course> list;

    public MyCoursesRecyclerViewAdapter(Context context, List<Course> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_courses, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Course course = list.get(position);

        holder.textName.setText(String.valueOf(course.getName()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textName, textInstructor;

        public ViewHolder(View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.tv_courseName);
            textInstructor = itemView.findViewById(R.id.tv_courseInstructor);
        }
    }
}
