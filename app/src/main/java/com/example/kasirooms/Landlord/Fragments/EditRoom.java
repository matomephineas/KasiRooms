package com.example.kasirooms.Landlord.Fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kasirooms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditRoom extends AppCompatActivity {
    EditText owner_add_houseLocation,owner_add_rent_amount,owner_add_town,owner_add_section,owner_add_rent_deposit;
    String address,rent,status,deposit,image,town,section,roomID,type;
    TextView roomType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);
        owner_add_houseLocation =findViewById(R.id.edit_owner_add_houseLocation);
        owner_add_rent_amount=findViewById(R.id.edit_owner_add_rent_amount);
        owner_add_town = findViewById(R.id.edit_owner_add_town);
        owner_add_rent_deposit =findViewById(R.id.edit_owner_add_rent_deposit);
        owner_add_section =findViewById(R.id.edit_owner_add_section);
        roomType =findViewById(R.id.roomType);


        Bundle bundle = getIntent().getExtras();
        address= bundle.getString("address");
        rent= bundle.getString("rent");
        status= bundle.getString("status");
        image= bundle.getString("image");
        roomID= bundle.getString("roomID");
        deposit =bundle.getString("deposit");
        town =bundle.getString("town");
        section=bundle.getString("section");
        type=bundle.getString("type");
        roomType.setText(type);
        owner_add_houseLocation.setText(address);
        owner_add_rent_amount.setText(rent);
        owner_add_rent_deposit.setText(deposit);
        owner_add_town.setText(town);
        owner_add_section.setText(section);
    }
    private boolean validateLocation()
    {
        address =owner_add_houseLocation.getText().toString().trim();
        if(address.isEmpty())
        {
            owner_add_houseLocation.setError("filed must not be empty");
            owner_add_houseLocation.requestFocus();
            return false;
        }
        else
        {
            owner_add_houseLocation.setError(null);
            owner_add_houseLocation.requestFocus();
            return true;
        }
    }
    private boolean validateDeposit()
    {
        deposit =owner_add_rent_deposit.getText().toString().trim();
        if(deposit.isEmpty())
        {
            owner_add_rent_deposit.setError("filed must not be empty");
            owner_add_rent_deposit.requestFocus();
            return false;
        }
        else
        {
            owner_add_rent_deposit.setError(null);
            owner_add_rent_deposit.requestFocus();
            return true;
        }
    }
    private boolean validateAmount()
    {
        rent =owner_add_rent_amount.getText().toString().trim();
        if(rent.isEmpty())
        {
            owner_add_rent_amount.setError("filed must not be empty");
            owner_add_rent_amount.requestFocus();
            return false;
        }
        else
        {
            owner_add_rent_amount.setError(null);
            owner_add_rent_amount.requestFocus();
            return true;
        }
    }
    private boolean validateTown()
    {
        town=owner_add_town.getText().toString().trim();
        if(town.isEmpty())
        {
            owner_add_town.setError("filed must not be empty");
            owner_add_town.requestFocus();
            return false;
        }
        else
        {
            owner_add_town.setError(null);
            owner_add_town.requestFocus();
            return true;
        }
    }
    private boolean validateSection()
    {
        section=owner_add_section.getText().toString().trim();
        if(section.isEmpty())
        {
            owner_add_section.setError("filed must not be empty");
            owner_add_section.requestFocus();
            return false;
        }
        else
        {
            owner_add_town.setError(null);
            owner_add_town.requestFocus();
            return true;
        }
    }
    public void SubmitChanges(View view)
    {
      if(!validateLocation() |!validateDeposit() | !validateAmount() | !validateTown() | !validateSection())
          return;
      else
      {
          DatabaseReference reference = FirebaseDatabase.getInstance().getReference("All rooms");
          HashMap<String,Object> map=new HashMap<>();
          map.put("address",address);
          map.put("price",rent);
          map.put("deposit",deposit);
          map.put("town",town);
          map.put("section",section);

          reference.child(roomID).updateChildren(map)
                  .addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                          if(task.isSuccessful()) {
                              DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Rooms").child(type);
                              reference1.child(roomID).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                  @Override
                                  public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()){

                                       Toast.makeText(getApplicationContext(), "Room updated successfully", Toast.LENGTH_SHORT).show();
                                   }
                                   else
                                   {
                                       Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                   }
                                  }
                              });
                          }
                          else
                          {
                              Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                          }
                      }
                  });

      }
    }
}