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

import com.example.kasirooms.Models.UserModel;
import com.example.kasirooms.R;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.viewHolder>
{

    private ArrayList<UserModel> Items;
    private Context context;
    public UsersAdapter(ArrayList<UserModel> items, Context context) {
        Items = items;
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_users,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.usertype.setText(Items.get(position).getUserType());
        holder.contact.setText(Items.get(position).getContact());
        holder.usernames.setText(Items.get(position).getName());
        holder.email.setText(Items.get(position).getEmail());


    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private TextView usertype, usernames,contact, email;
        public LinearLayout bookedLayout;
        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            usertype =itemView.findViewById(R.id.usertype);
            usernames =itemView.findViewById(R.id.usernames);
            contact = itemView.findViewById(R.id.usercontacts);
            email=itemView.findViewById(R.id.useremail);

        }
    }
}
