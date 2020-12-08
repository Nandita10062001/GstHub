package com.example.gsthub.Forum;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsthub.R;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<Data> mDataList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mUsername, mDescription, Posted;

        public ViewHolder(View itemView) {
            super(itemView);
            mUsername = itemView.findViewById(R.id.Username);
            mDescription = itemView.findViewById(R.id.Description);
            Posted = itemView.findViewById(R.id.timeStamp);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_post, parent, false );
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Data data = mDataList.get(i);
        holder.mUsername.setText("Posted by: "+data.getUsername());
        holder.mDescription.setText((data.getDescription()));
        holder.Posted.setText("At "+data.getTime()+", "+data.getDate());

    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    

    public DataAdapter(ArrayList<Data> dataList) {
        mDataList = dataList;
    }
}
