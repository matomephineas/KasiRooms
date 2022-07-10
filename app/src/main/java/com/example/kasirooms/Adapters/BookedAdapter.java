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
import com.example.kasirooms.Landlord.ViewAvailableRoomsActivity;
import com.example.kasirooms.Models.BookedRoom;
import com.example.kasirooms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class BookedAdapter extends RecyclerView.Adapter<BookedAdapter.viewHolder>
{

    private ArrayList<BookedRoom> Items;
    private Context context;
    public BookedAdapter(ArrayList<BookedRoom> items, Context context) {
        Items = items;
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_booked,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name_surname.setText(Items.get(position).getName_surname());
        holder.address.setText(Items.get(position).getAddress());
        holder.contact.setText(Items.get(position).getContact());
        holder.status.setText(Items.get(position).getStatus());
        holder.email.setText(Items.get(position).getAddress());
        holder.town.setText(Items.get(position).getTown());
        holder.section.setText(Items.get(position).getSection());
        holder.deposit.setText(Items.get(position).getDeposit());
        holder.date.setText(Items.get(position).getDate());
        holder.bookedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CharSequence options[] = new CharSequence[]{"Apartment","Bachelor","Town house","Flat","Cottage","Single room"};
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                builder.setTitle("Accept booking");
                builder.setMessage("Do you want to accept this booking");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BookedRoom bookedRoom = new BookedRoom(Items.get(position).getUserId(),
                                Items.get(position).getRoomId(),
                                "confirmed",
                                Items.get(position).getName_surname(),
                                Items.get(position).getContact(),
                                Items.get(position).getEmail(),
                                Items.get(position).getAddress(),
                                Items.get(position).getTown(),
                                Items.get(position).getSection(),
                                Items.get(position).getDeposit(),
                                Items.get(position).getDate(),Items.get(position).getType());
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Booked").child(Items.get(position).getUserId());
                        reference.setValue(bookedRoom).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("All rooms").child(Items.get(position).getRoomId());
                                    HashMap<String,Object> map = new HashMap<>();
                                    map.put("status","not available");
                                    reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Rooms").child(Items.get(position).getType()).child(Items.get(position).getRoomId());
                                                reference1.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){

                                                            Toast.makeText(context, "Booking accepted", Toast.LENGTH_SHORT).show();
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
                                else
                                {
                                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Bye bye,you cancelled", Toast.LENGTH_SHORT).show();
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
        private TextView status,name_surname,contact, email, address, town,section,deposit,date;
        public LinearLayout bookedLayout;
        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            bookedLayout =itemView.findViewById(R.id.bookedLayout);
            name_surname=itemView.findViewById(R.id.name_surname);
            contact = itemView.findViewById(R.id.contact);
            email=itemView.findViewById(R.id.email);
            town =itemView.findViewById(R.id.txtLandlordRoomDeposit);
            address = itemView.findViewById(R.id.address);
            section = itemView.findViewById(R.id.section);
            status =itemView.findViewById(R.id.status);
;            deposit =itemView.findViewById(R.id.deposit);
            town =itemView.findViewById(R.id.town);
           date = itemView.findViewById(R.id.date);
        }
    }
}
