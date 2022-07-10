package com.example.kasirooms.Landlord;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kasirooms.Adapters.BookedAdapter;
import com.example.kasirooms.Adapters.LandlordRoomsAdapter;
import com.example.kasirooms.Landlord.Fragments.LandlordHome;
import com.example.kasirooms.Models.BookedRoom;
import com.example.kasirooms.Models.RoomModel;

import java.util.ArrayList;
import java.util.List;

import com.example.kasirooms.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class LandLordMainActivity extends AppCompatActivity {

    private RecyclerView recyclerCourses;
    private String course_name,course_code,choice, userId;
    private LandlordRoomsAdapter adapter;
    private ArrayList<RoomModel> mList;
    private BookedAdapter bookedAdapter;
    private ArrayList<BookedRoom> mList2;
    FloatingActionButton addRoom;
    TextView landlord_name;


    private ViewPager viewPager;
    private TabLayout tabLayout;

    private LandlordHome landlordHome;
    private LandlordBookedRoom landlordBookedRoom;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_lord_main);
        landlord_name =findViewById(R.id.landlord_name);
        Bundle bundle =  getIntent().getExtras();
        userId = bundle.getString("userId");
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    landlord_name.setText(snapshot.child("name").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        viewPager = findViewById(R.id.landlord_viewpager);
        tabLayout = findViewById(R.id.landlord_tabs);

        landlordHome = new LandlordHome();
        landlordBookedRoom = new LandlordBookedRoom();
        tabLayout.setupWithViewPager(viewPager);


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(landlordHome, "Home", R.color.white);
        viewPagerAdapter.addFragment(landlordBookedRoom, "Booked room", R.color.white);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_home_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_format_list_numbered_24);


    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitles = new ArrayList<>();
        private List<Integer> fragmentTextColor = new ArrayList<>();

        public ViewPagerAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title, int black) {
            fragments.add(fragment);
            fragmentTitles.add(title);
            fragmentTextColor.add(R.color.black);
        }

        @NonNull
        @NotNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @org.jetbrains.annotations.Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
    }
    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Click again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}