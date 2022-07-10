package com.example.kasirooms.Adapters;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.example.kasirooms.Landlord.ViewAvailableRoomsActivity;
import com.example.kasirooms.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ViewHolder> {
    Context context;
    ArrayList provinceList, images;

    public ProvinceAdapter(Context context, ArrayList provinceList, ArrayList images) {
        this.context = context;
        this.provinceList = provinceList;
        this.images = images;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_province,parent,false);
        //sharedPreferences= context.getSharedPreferences("MyDay",Context.MODE_PRIVATE);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        int res = (int) images.get(position);

        holder.imageView.setImageResource(res);
        holder.province.setText((String) provinceList.get(position));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CharSequence options[] = new CharSequence[]{"Apartment","Bachelor","Town house","Flat","Cottage","Single room"};
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                builder.setTitle("Select room type");

                builder.setIcon(R.drawable.ic_baseline_format_list_numbered_24);
                builder.setItems(options, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (which==0)
                        {
                          Intent intent = new Intent(context, ViewAvailableRoomsActivity.class);
                          intent.putExtra("province",(String) provinceList.get(position));
                          intent.putExtra("roomType","Apartment");
                          intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                          context.startActivity(intent);

                        }
                        if (which==1)
                        {
                            Intent intent = new Intent(context, ViewAvailableRoomsActivity.class);
                            intent.putExtra("province",(String) provinceList.get(position));
                            intent.putExtra("roomType","Bachelor");
                            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        if (which==2)
                        {
                            Intent intent = new Intent(context, ViewAvailableRoomsActivity.class);
                            intent.putExtra("province",(String) provinceList.get(position));
                            intent.putExtra("roomType","Town house");
                            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        if (which==3)
                        {
                            Intent intent = new Intent(context, ViewAvailableRoomsActivity.class);
                            intent.putExtra("province",(String) provinceList.get(position));
                            intent.putExtra("roomType","Flat");
                            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        if (which==4)
                        {
                            Intent intent = new Intent(context, ViewAvailableRoomsActivity.class);
                            intent.putExtra("province",(String) provinceList.get(position));
                            intent.putExtra("roomType","Cottage");
                            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        if (which==5)
                        {
                            Intent intent = new Intent(context, ViewAvailableRoomsActivity.class);
                            intent.putExtra("province",(String) provinceList.get(position));
                            intent.putExtra("roomType","Single room");
                            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return provinceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView province;
        public ImageView imageView,arrow_down;
        public RelativeLayout relativeLayout;
        public LinearLayout expandableLayout;
        public RecyclerView recyclerview2;
        public ViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);

            province = itemView.findViewById(R.id.tvProvinceName);
            imageView = itemView.findViewById(R.id.dayImg);
            relativeLayout = itemView.findViewById(R.id.relativelayout);

        }
    }
}
