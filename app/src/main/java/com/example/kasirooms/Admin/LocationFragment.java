package com.example.kasirooms.Admin;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirooms.Adapters.CustomerRoomsAdapter;
import com.example.kasirooms.Adapters.LocationAdapter;
import com.example.kasirooms.Models.LocationModel;
import com.example.kasirooms.Models.RoomModel;
import com.example.kasirooms.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LocationFragment extends Fragment {
    private LocationAdapter adapter;
    private ArrayList<LocationModel> mList;
    private RecyclerView recyclerview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_location, container, false);

        recyclerview=view.findViewById(R.id.recyclerview_section);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);

        mList = new ArrayList<>();
        adapter = new LocationAdapter(mList,getContext());
        recyclerview.setAdapter(adapter);

        Query retrieve = FirebaseDatabase.getInstance().getReference("Locations");
        DatabaseReference reference =FirebaseDatabase.getInstance().getReference("Locations");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    LocationModel model = dataSnapshot.getValue(LocationModel.class);
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