package com.example.kasirooms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kasirooms.Models.BookedRoom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConfirmBooking extends AppCompatActivity {

    String price,status,roomID,deposit,type,startDate,name,contact,userType,email, address, town,section;
    TextInputLayout enterStartDate;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);

        enterStartDate=findViewById(R.id.enterStartDate);
        Bundle bundle = getIntent().getExtras();
        price= bundle.getString("price");
        status= bundle.getString("status");
        roomID=bundle.getString("roomID");
        deposit=bundle.getString("deposit");
        address = bundle.getString("address");
        town =bundle.getString("town");
        section =bundle.getString("section");
        type = bundle.getString("type");

        mAuth = FirebaseAuth.getInstance();
        mUser =mAuth.getCurrentUser();

        DatabaseReference user = FirebaseDatabase.getInstance().getReference("Users");
        user.child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                   name= snapshot.child("name").getValue().toString();
                   contact= snapshot.child("contact").getValue().toString();
                   userType=snapshot.child("userType").getValue().toString();
                   email = snapshot.child("email").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private boolean validateStartDate()
    {
        startDate =enterStartDate.getEditText().getText().toString().trim().toUpperCase();
        if(startDate.isEmpty())
        {
            enterStartDate.setError("filed must not be empty");
            enterStartDate.requestFocus();
            return false;
        }
        else
        {
            enterStartDate.setError(null);
            enterStartDate.requestFocus();
            return true;
        }
    }

    public void confrimRoomBookings(View view)
    {
        if( !validateStartDate())
        {
            return;
        }
        else
        {
            if(status.equals("not available"))
            {
                Toast.makeText(getApplicationContext(), "Room is not available at the moment", Toast.LENGTH_SHORT).show();
            }

            else if(userType.equals("Landlord") )
            {
                Toast.makeText(getApplicationContext(), "Landlord cannot make a booking", Toast.LENGTH_SHORT).show();
            }
            else
            {
                DatabaseReference booked= FirebaseDatabase.getInstance().getReference("Booked");
                booked.child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            Toast.makeText(getApplicationContext(), "You have already booked", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            DatabaseReference booked= FirebaseDatabase.getInstance().getReference("Booked");
                            BookedRoom bookedRoom = new BookedRoom(mUser.getUid(),roomID,status,name,contact, email, address, town,section,deposit,startDate,type);
                            booked.child(mUser.getUid()).setValue(bookedRoom)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(getApplicationContext(), "room booked successfully", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        }
    }
}