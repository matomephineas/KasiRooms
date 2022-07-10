package com.example.kasirooms.Adapters;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirooms.Landlord.BookRoomActivity;
import com.example.kasirooms.Models.RoomModel;
import com.example.kasirooms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomerRoomsAdapter extends RecyclerView.Adapter<CustomerRoomsAdapter.viewHolder>
{

    private ArrayList<RoomModel> Items;
    private Context context;
    public CustomerRoomsAdapter(ArrayList<RoomModel> items, Context context) {
        Items = items;
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_room,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtUserRoomSize.setText(Items.get(position).getRoomSize());
        holder.txtUserRoomAmount.setText(Items.get(position).getAmount());
        holder.txtUserRoomStatus.setText(Items.get(position).getStatus());
        holder.txtUserRoomTown.setText(Items.get(position).getTown());
        holder.txtUserRoomProvince.setText(Items.get(position).getProvince());
        holder.txtUserRoomType.setText(Items.get(position).getRoomType());
        holder.txtUserRoomAddress.setText(Items.get(position).getAddress());
        holder.txtUserRoomDeposit.setText(Items.get(position).getDeposit());
        holder.txtUserRoomSection.setText(Items.get(position).getSection());
        holder.txtUserRoomStatus.setText(Items.get(position).getStatus());
        holder.txtParkingStatus.setText(Items.get(position).getParking());
        holder.txtBathroom.setText(Items.get(position).getParking());
        holder.txtCeiling.setText(Items.get(position).getCeiling());
        holder.txtTile.setText(Items.get(position).getTile());
        holder.txtToilet.setText(Items.get(position).getToilet());

        Picasso.get().load(Items.get(position).getImage().toString()).resize(390, 230).into(holder.roomImage);
        Picasso.get().load(Items.get(position).getImage2()).into(holder.image2);
        Picasso.get().load(Items.get(position).getImage3()).into(holder.image3);
        holder.single_room_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context, BookRoomActivity.class);
                intent.putExtra("address",Items.get(position).getAddress());
                intent.putExtra("price",Items.get(position).getAmount());
                intent.putExtra("status",Items.get(position).getStatus());
                intent.putExtra("size",Items.get(position).getRoomSize());
                intent.putExtra("type",Items.get(position).getRoomType());
                intent.putExtra("image",Items.get(position).getImage());
                intent.putExtra("image2",Items.get(position).getImage2());
                intent.putExtra("image3",Items.get(position).getImage3());
                intent.putExtra("roomID",Items.get(position).getRoomID());
                intent.putExtra("userId",Items.get(position).getUserId());
                intent.putExtra("deposit",Items.get(position).getDeposit());
                intent.putExtra("town",Items.get(position).getTown());
                intent.putExtra("section",Items.get(position).getSection());
                intent.putExtra("deposit",Items.get(position).getDeposit());
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private TextView txtUserRoomType,txtUserRoomProvince,txtUserRoomTown,txtUserRoomAddress,txtUserRoomAmount,txtUserRoomDeposit
                ,txtParkingStatus,txtBathroom,txtCeiling,txtTile,txtToilet,txtUserRoomSize,txtUserRoomStatus,txtUserRoomSection;
        public ImageView roomImage, editRoom,deleteCourse,image2,image3;
        public LinearLayout single_room_layout;
        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            image2=itemView.findViewById(R.id.imag4);
            image3=itemView.findViewById(R.id.imag5);
            single_room_layout=itemView.findViewById(R.id.single_room_layout);
            txtUserRoomSize=itemView.findViewById(R.id.txtUserRoomSize);
            txtUserRoomSection = itemView.findViewById(R.id.txtUserRoomSection);
            txtUserRoomStatus=itemView.findViewById(R.id.txtUserRoomStatus);
            txtUserRoomDeposit=itemView.findViewById(R.id.txtUserRoomDeposit);
            txtUserRoomAmount = itemView.findViewById(R.id.txtUserRoomAmount);
            txtUserRoomAddress = itemView.findViewById(R.id.txtUserRoomAddress);
            txtUserRoomTown=itemView.findViewById(R.id.txtUserRoomTown);
            txtUserRoomType=itemView.findViewById(R.id.txtUserRoomType);
            txtUserRoomProvince=itemView.findViewById(R.id.txtUserRoomProvince);
            roomImage = itemView.findViewById(R.id.user_image);
            txtToilet=itemView.findViewById(R.id.txtToilet);
            txtTile=itemView.findViewById(R.id.txtTile);
            txtCeiling=itemView.findViewById(R.id.txtCeiling);
            txtBathroom=itemView.findViewById(R.id.txtBathroom);
            txtParkingStatus =itemView.findViewById(R.id.txtParkingStatus);
;
        }
    }
}
