package com.example.kasirooms.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasirooms.Adapters.LocationAdapter;
import com.example.kasirooms.Adapters.UsersAdapter;
import com.example.kasirooms.Models.LocationModel;
import com.example.kasirooms.Models.UserModel;
import com.example.kasirooms.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LandlordFragment extends Fragment {
FloatingActionButton addLandLord;
RecyclerView recyclerView;
    private UsersAdapter adapter;
    private ArrayList<UserModel> mList;
    private RecyclerView recyclerview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_landlord, container, false);
        addLandLord = view.findViewById(R.id.addlondlord);
        recyclerView = view.findViewById(R.id.recyclerview_landlord);

        addLandLord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddLandlordDialogFragment fragment = new AddLandlordDialogFragment();
                fragment.show(getActivity().getSupportFragmentManager(),"My  Fragment");

            }
        });

        recyclerview=view.findViewById(R.id.recyclerview_landlord);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);

        mList = new ArrayList<>();
        adapter = new UsersAdapter(mList,getContext());
        recyclerview.setAdapter(adapter);

        Query retrieve = FirebaseDatabase.getInstance().getReference("Users")
         .orderByChild("userType")
                .equalTo("Landlord");
        retrieve.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    UserModel model = dataSnapshot.getValue(UserModel.class);
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