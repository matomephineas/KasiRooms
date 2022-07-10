package com.example.kasirooms;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kasirooms.Adapters.CustomerRoomsAdapter;
import com.example.kasirooms.Adapters.ProvinceAdapter;
import com.example.kasirooms.Adapters.SliderAdapter;
import com.example.kasirooms.Landlord.LandLordMainActivity;

import com.example.kasirooms.Models.RoomModel;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Arrays;

import fr.ganfra.materialspinner.MaterialSpinner;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TextView province,btnSearch,roomType;
    ImageView backBtn;
    private RecyclerView recyclerCourses;
    private CustomerRoomsAdapter adapter;
    private ArrayList<RoomModel> mList;
    String provinceName;
    private Button sortby;
    private TextInputLayout search;
    String searchedResult,type;
    MaterialSpinner roomtypespinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        province =findViewById(R.id.province);
        search = findViewById(R.id.search);
        btnSearch=findViewById(R.id.btnSearch);
        roomType=findViewById(R.id.roomType);
        roomtypespinner = findViewById(R.id.spinner);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.roomType));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomtypespinner.setAdapter(adapter);
        roomtypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = adapterView.getItemAtPosition(i).toString();
                //txt_province.setText(choice2);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        provinceName = "Limpopo";


        backBtn =findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        recyclerCourses=findViewById(R.id.recyclerview2);
        recyclerCourses.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerCourses.setHasFixedSize(true);

        mList = new ArrayList<>();
        this.adapter = new CustomerRoomsAdapter(mList,getApplicationContext());
        recyclerCourses.setAdapter(this.adapter);
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
            Query retrieve = FirebaseDatabase.getInstance().getReference("Rooms").child(type)
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId()) {


            case R.id.nav_history:
                Intent intent2 = new Intent(getApplicationContext(), Bookings.class);
                // intent.putExtra("type", "Customers");
                startActivity(intent2);
                finish();
                return true;
            default:
                break;
        }
        return false;
    }
}