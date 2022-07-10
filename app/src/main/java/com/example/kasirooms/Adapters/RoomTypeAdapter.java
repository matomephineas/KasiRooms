package com.example.kasirooms.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirooms.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RoomTypeAdapter extends RecyclerView.Adapter<RoomTypeAdapter.ViewHolder>{
    Context context;
    ArrayList weeksList, images;

    public RoomTypeAdapter(Context context, ArrayList weeksList, ArrayList images) {
        this.context = context;
        this.weeksList = weeksList;
        this.images = images;

    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.roomtype,parent,false);
        //sharedPreferences= context.getSharedPreferences("MyDay",Context.MODE_PRIVATE);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        int res = (int) images.get(position);
        holder.imageView.setImageResource((int)images.get(position));
        holder.weekday.setText((String) weeksList.get(position));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, AddTimeTable.class);
//
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return weeksList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView weekday;
        public ImageView imageView;
        public LinearLayout layout;
        public ViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);
            weekday = itemView.findViewById(R.id.txtApartment);
            imageView = itemView.findViewById(R.id.imageView);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
