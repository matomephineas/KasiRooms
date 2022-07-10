package com.example.kasirooms.Landlord.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kasirooms.Adapters.BookedAdapter;
import com.example.kasirooms.Adapters.LandlordRoomsAdapter;
import com.example.kasirooms.Models.BookedRoom;
import com.example.kasirooms.Models.RoomModel;
import com.example.kasirooms.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class LandlordHome extends Fragment {

    private RecyclerView recyclerCourses;
    private String course_name,course_code,choice, userId;
    private LandlordRoomsAdapter adapter;
    private ArrayList<RoomModel> mList;
    private BookedAdapter bookedAdapter;
    private ArrayList<BookedRoom> mList2;
    FloatingActionButton addRoom;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_landlord_home, container, false);
        recyclerCourses=view.findViewById(R.id.recyclerview6);
        addRoom=view.findViewById(R.id.addRoom);


        recyclerCourses.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerCourses.setHasFixedSize(true);

        Bundle bundle =  getActivity().getIntent().getExtras();
        userId = bundle.getString("userId");
        mList = new ArrayList<>();
        adapter = new LandlordRoomsAdapter(mList,getContext());
        recyclerCourses.setAdapter(adapter);
        Query retrieve = FirebaseDatabase.getInstance().getReference("All rooms")
                .orderByChild("userId")
                .equalTo(userId);
        retrieve.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    RoomModel model = dataSnapshot.getValue(RoomModel.class);
                    mList.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),AddRoomActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });



        return view;
    }
}