package com.example.sano;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    //this class has no usage, just for reserved.

    List<String> diary_content;
    List<String> diary_date;

    public RecyclerViewAdapter(List<String> diary_content, List<String> diary_date){
        this.diary_content = diary_content;
        this.diary_date = diary_date;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_rview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.diary_content.setText(diary_content.get(position));
        holder.diary_date.setText(diary_date.get(position));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "You clicked this.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return diary_content.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView diary_content;
        TextView diary_date;
        View view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            diary_content = itemView.findViewById(R.id.diary_content);
            diary_date = itemView.findViewById(R.id.diary_date);
            view = itemView;
        }
    }

}
