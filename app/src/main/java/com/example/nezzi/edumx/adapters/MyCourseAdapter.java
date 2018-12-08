package com.example.nezzi.edumx.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nezzi.edumx.R;
import com.example.nezzi.edumx.models.Course;

import java.util.List;


public class MyCourseAdapter extends RecyclerView.Adapter<MyCourseAdapter.ViewHolder> implements View.OnClickListener {

    private List<Course> list;
    private View.OnClickListener listener;

    public MyCourseAdapter(List<Course> list) {
        this.list = list;
    }

    @Override
    public MyCourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_mycourse_item, parent, false);
        v.setOnClickListener(this);
        return new MyCourseAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mItem = list.get(position);
        holder.mCourseNameView.setText(list.get(position).getName());
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
        public final View mView;
        public final TextView mCourseNameView;
        public Course mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCourseNameView = (TextView) view.findViewById(R.id.txt_courseName);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCourseNameView.getText() + "'";
        }

    }

}
