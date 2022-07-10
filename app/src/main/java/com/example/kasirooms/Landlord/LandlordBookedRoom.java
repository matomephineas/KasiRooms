package com.example.kasirooms.Landlord;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kasirooms.Adapters.BookedAdapter;
import com.example.kasirooms.Adapters.CustomerRoomsAdapter;
import com.example.kasirooms.Models.BookedRoom;
import com.example.kasirooms.Models.RoomModel;
import com.example.kasirooms.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LandlordBookedRoom extends Fragment {

    private RecyclerView view_booked;
    private BookedAdapter adapter;
    private ArrayList<BookedRoom> mList;
    FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_landlord_booked_room, container, false);

        view_booked =view.findViewById(R.id.view_booked);
        view_booked.setLayoutManager(new LinearLayoutManager(getContext()));
        view_booked.setHasFixedSize(true);
        mAuth =FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        mList = new ArrayList<>();
        adapter = new BookedAdapter(mList,getContext());
        view_booked.setAdapter(adapter);

        Query retrieve = FirebaseDatabase.getInstance().getReference("Booked").child(user.getUid());
        retrieve.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    BookedRoom model = dataSnapshot.getValue(BookedRoom.class);
                    mList.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}