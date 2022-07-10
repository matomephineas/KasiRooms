package com.example.kasirooms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.kasirooms.Adapters.BookedAdapter;
import com.example.kasirooms.Models.BookedRoom;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Bookings extends AppCompatActivity {

    private RecyclerView view_booked;
    private BookedAdapter adapter;
    private ArrayList<BookedRoom> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);
        view_booked =findViewById(R.id.recyclerview7);
        view_booked.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        view_booked.setHasFixedSize(true);

        mList = new ArrayList<>();
        adapter = new BookedAdapter(mList,getApplicationContext());
        view_booked.setAdapter(adapter);

        Query retrieve = FirebaseDatabase.getInstance().getReference("Booked");
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
    }
}