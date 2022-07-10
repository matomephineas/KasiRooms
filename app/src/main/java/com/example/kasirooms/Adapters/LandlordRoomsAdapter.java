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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirooms.Landlord.BookRoomActivity;
import com.example.kasirooms.Landlord.Fragments.EditRoom;
import com.example.kasirooms.Models.BookedRoom;
import com.example.kasirooms.Models.RoomModel;
import com.example.kasirooms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class LandlordRoomsAdapter extends RecyclerView.Adapter<LandlordRoomsAdapter.viewHolder>
{

    private ArrayList<RoomModel> Items;
    private Context context;
    public LandlordRoomsAdapter(ArrayList<RoomModel> items, Context context) {
        Items = items;
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_landlord_room,parent,false);
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

        Picasso.get().load(Items.get(position).getImage()).resize(390, 230).into(holder.roomImage);
        Picasso.get().load(Items.get(position).getImage2()).into(holder.image2);
        Picasso.get().load(Items.get(position).getImage3()).into(holder.image3);
        holder.editRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context, EditRoom.class);
                intent.putExtra("address",Items.get(position).getAddress());
                intent.putExtra("rent",Items.get(position).getAmount());
                intent.putExtra("status",Items.get(position).getStatus());
                intent.putExtra("deposit",Items.get(position).getDeposit());
                intent.putExtra("town",Items.get(position).getTown());
                intent.putExtra("image",Items.get(position).getImage());
                intent.putExtra("roomID",Items.get(position).getRoomID());
                intent.putExtra("userId",Items.get(position).getUserId());
                intent.putExtra("section",Items.get(position).getSection());
                intent.putExtra("type",Items.get(position).getRoomType());
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.deleteRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Rooms").child(Items.get(position).getRoomType());
                reference.child(Items.get(position).getRoomID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("All rooms");
                            reference.child(Items.get(position).getRoomID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                 if(task.isSuccessful())
                                 {
                                     Toast.makeText(context, "successfully deleted", Toast.LENGTH_SHORT).show();
                                 }
                                 else
                                 {
                                     Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                 }
                                }
                            });
                        }
                    }
                });
            }
        });
        holder.changeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CharSequence options[] = new CharSequence[]{"Apartment","Bachelor","Town house","Flat","Cottage","Single room"};
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                builder.setTitle("UPDATE ROOM STATUS");
                builder.setMessage("Please choose status to update below");

                builder.setPositiveButton("Available", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("All rooms").child(Items.get(position).getRoomID());
                        HashMap<String,Object> map = new HashMap<>();
                        map.put("status","available");
                        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Rooms").child(Items.get(position).getRoomType()).child(Items.get(position).getRoomID());
                                    reference1.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("Not Available", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("All rooms").child(Items.get(position).getRoomID());
                        HashMap<String,Object> map = new HashMap<>();
                        map.put("status","not available");
                        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Rooms").child(Items.get(position).getRoomType()).child(Items.get(position).getRoomID());
                                    reference1.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
                builder.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private TextView txtUserRoomType,txtUserRoomProvince,txtUserRoomTown,txtUserRoomAddress,txtUserRoomAmount,txtUserRoomDeposit
                ,txtParkingStatus,txtBathroom,txtCeiling,txtTile,txtToilet,txtUserRoomSize,changeStatus,txtUserRoomStatus,txtUserRoomSection;
        public ImageView roomImage, editRoom,deleteRoom,image2,image3;
        public LinearLayout single_room_layout;
        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            image2=itemView.findViewById(R.id.imag2);
           image3=itemView.findViewById(R.id.imag3);
            changeStatus=itemView.findViewById(R.id.changeStatus);
            single_room_layout=itemView.findViewById(R.id.single_room_layout);
            txtUserRoomSize=itemView.findViewById(R.id.txtLandlordRoomSize);
            txtUserRoomSection = itemView.findViewById(R.id.txtLandlordRoomSection);
            txtUserRoomStatus=itemView.findViewById(R.id.txtLandlordRoomStatus);
            txtUserRoomDeposit=itemView.findViewById(R.id.txtLandlordRoomDeposit);
            txtUserRoomAmount = itemView.findViewById(R.id.txtLandlordRoomAmount);
            txtUserRoomAddress = itemView.findViewById(R.id.txtLandlordRoomAddress);
            txtUserRoomTown=itemView.findViewById(R.id.txtLandlordRoomTown);
            txtUserRoomType=itemView.findViewById(R.id.txtLandlordRoomType);
            txtUserRoomProvince=itemView.findViewById(R.id.txtLandlordRoomProvince);
            roomImage = itemView.findViewById(R.id.Landlord_image);
            txtToilet=itemView.findViewById(R.id.LandlordtxtToilet);
            txtTile=itemView.findViewById(R.id.LandlordtxtTile);
            txtCeiling=itemView.findViewById(R.id.LandlordtxtCeiling);
            txtBathroom=itemView.findViewById(R.id.LandlordtxtBathroom);
            txtParkingStatus =itemView.findViewById(R.id.LandlordtxtParkingStatus);
            editRoom = itemView.findViewById(R.id.editroom);
            deleteRoom=itemView.findViewById(R.id.deleteroom);
;
        }
    }
}
