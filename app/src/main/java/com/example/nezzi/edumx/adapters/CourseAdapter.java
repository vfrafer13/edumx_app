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
 * Created by ankit on 27/10/17.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<Course> list;
    private View.OnClickListener listener;

    public CourseAdapter(Context context, List<Course> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_course_item, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Course course = list.get(position);

        holder.txtCatName.setText(course.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCatName;

        public ViewHolder(View itemView) {
            super(itemView);

            txtCatName = itemView.findViewById(R.id.txt_catName);
        }
    }

}
