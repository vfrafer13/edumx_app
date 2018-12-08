package com.example.nezzi.edumx.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nezzi.edumx.R;
import com.example.nezzi.edumx.models.Category;

import java.util.List;

/**
 * Created by nezzi on 27/10/18.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> implements View.OnClickListener {

    private List<Category> list;
    private View.OnClickListener listener;

    public CategoryAdapter(List<Category> list) {
        this.list = list;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_category_item, parent, false);
        v.setOnClickListener(this);
        return new CategoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mItem = list.get(position);
        holder.mCatNameView.setText(list.get(position).getName());
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
        public final TextView mCatNameView;
        public Category mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCatNameView = (TextView) view.findViewById(R.id.txt_catName);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCatNameView.getText() + "'";
        }

    }

}
