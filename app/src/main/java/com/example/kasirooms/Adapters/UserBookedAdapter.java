package com.example.kasirooms.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirooms.Models.BookedRoom;
import com.example.kasirooms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class UserBookedAdapter extends RecyclerView.Adapter<UserBookedAdapter.viewHolder>
{

    private ArrayList<BookedRoom> Items;
    private Context context;
    public UserBookedAdapter(ArrayList<BookedRoom> items, Context context) {
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
