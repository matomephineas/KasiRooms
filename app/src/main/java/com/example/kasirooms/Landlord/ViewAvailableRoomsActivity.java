package com.example.kasirooms.Landlord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kasirooms.Adapters.CustomerRoomsAdapter;
import com.example.kasirooms.Models.RoomModel;
import com.example.kasirooms.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewAvailableRoomsActivity extends AppCompatActivity {

    TextView province,btnSearch,roomType;
    ImageView backBtn;
    private RecyclerView recyclerCourses;
    private CustomerRoomsAdapter adapter;
    private ArrayList<RoomModel> mList;
    String provinceName,roomTypeName;
    private Button sortby;
    private TextInputLayout search;
    String searchedResult,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_rooms);
        province =findViewById(R.id.province);
        sortby = findViewById(R.id.sortby);
        search = findViewById(R.id.search);
        btnSearch=findViewById(R.id.btnSearch);
        roomType=findViewById(R.id.roomType);
        Bundle bundle =  getIntent().getExtras();
        provinceName = bundle.getString("province");
        province.setText(provinceName);
        roomTypeName=bundle.getString("roomType");
        roomType.setText(roomTypeName);
        backBtn =findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        sortby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        recyclerCourses=findViewById(R.id.recyclerview2);
        recyclerCourses.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerCourses.setHasFixedSize(true);

        mList = new ArrayList<>();
        adapter = new CustomerRoomsAdapter(mList,getApplicationContext());
        recyclerCourses.setAdapter(adapter);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchedResult();
            }
        });

    }
    private boolean validateSearch()
    {
        searchedResult=search.getEditText().getText().toString().trim().toUpperCase();
        if(searchedResult.isEmpty())
        {
            search.setError("filed must not be empty");
            search.requestFocus();
            return false;
        }
        else
        {
            search.setError(null);
            search.requestFocus();
            return true;
        }
    }
    private void searchedResult()
    {
       if(!validateSearch())
           return;
       else
       {
           Query retrieve = FirebaseDatabase.getInstance().getReference("Rooms").child(roomTypeName)
                   .orderByChild("town")
                   .equalTo(searchedResult);
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
       }

    }

}