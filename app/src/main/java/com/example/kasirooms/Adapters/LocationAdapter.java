package com.example.kasirooms.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirooms.Models.BookedRoom;
import com.example.kasirooms.Models.LocationModel;
import com.example.kasirooms.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private ArrayList<LocationModel> Items;
    private Context context;

    public LocationAdapter(ArrayList<LocationModel> items, Context context) {
        Items = items;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_town,parent,false);
        //sharedPreferences= context.getSharedPreferences("MyDay",Context.MODE_PRIVATE);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {

        holder.tvTown.setText(Items.get(position).getTown());
        holder.tvSection.setText(Items.get(position).getSection());


    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvSection,tvTown;
        public LinearLayout layout;

        public ViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);

            tvSection = itemView.findViewById(R.id.tvSection);
            tvTown = itemView.findViewById(R.id.tvTown);
            layout = itemView.findViewById(R.id.layout);

        }
    }
}
