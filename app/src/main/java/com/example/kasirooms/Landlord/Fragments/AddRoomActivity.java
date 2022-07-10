package com.example.kasirooms.Landlord.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.cardview.widget.CardView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kasirooms.Landlord.LandLordMainActivity;
import com.example.kasirooms.Models.RoomModel;
import com.example.kasirooms.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddRoomActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    AppCompatSpinner roomtypespinner;
    TextView txt_room_type,txt_province,txt_town;
    private String  currentTime, currentDate,section,town,amount,address, roomType, province, deposit,parking,bathroom,toilet,ceiling,tile, roomFor,family,working;
    CardView card1,card2,card3;
    EditText owner_add_houseLocation,owner_add_rent_amount,owner_add_town,owner_add_section,owner_add_rent_deposit;
    private static final int GalleryPick1 = 1,GalleryPick2 = 2,GalleryPick3 = 3;
    private Uri ImageUri2,ImageUri3,ImageUri;
    private ImageView first_image,second_image,third_image;
    private Button owner_btn_register_room;
    private ProgressDialog progressDialog;

    RadioGroup radioGroup,radioGroup2;
    RadioButton radioButton,radioButton2;
    CheckBox checkBoxBathroom,checkBoxParking,checkBoxTile,checkBoxCeiling;
    StorageReference filePath;

    private String productRandomKey,userId,downloadImageUrl,downloadImageUrl2,downloadImageUrl3,endMonth,endTime;
    private StorageReference ProductImageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        roomtypespinner = findViewById(R.id.roomtypespinner);
        checkBoxBathroom = findViewById(R.id.bathroom);
        checkBoxParking = findViewById(R.id.parking);
        checkBoxTile = findViewById(R.id.tile);
        checkBoxCeiling = findViewById(R.id.ceiling);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup2=findViewById(R.id.radioGroup2);

        owner_add_houseLocation =findViewById(R.id.owner_add_houseLocation);
        owner_add_rent_amount=findViewById(R.id.owner_add_rent_amount);
        owner_add_town = findViewById(R.id.owner_add_town);
        owner_btn_register_room = findViewById(R.id.owner_btn_register_room);
        owner_add_rent_deposit =findViewById(R.id.owner_add_rent_deposit);
        owner_add_section =findViewById(R.id.owner_add_section);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        first_image =findViewById(R.id.first_image);
        second_image =findViewById(R.id.second_image);
        third_image =findViewById(R.id.third_image);

        progressDialog = new ProgressDialog(this);
        Bundle bundle = getIntent().getExtras();
        userId =bundle.getString("userId");

        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.roomType));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomtypespinner.setAdapter(adapter);
        roomtypespinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter2 =new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.province));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery1();
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery2();
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery3();
            }
        });
        owner_btn_register_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddRoomDetails();
                int selected= radioGroup.getCheckedRadioButtonId();
                radioButton =(RadioButton)findViewById(selected);

                int selectTwo = radioGroup2.getCheckedRadioButtonId();
                radioButton2=(RadioButton)findViewById(selectTwo);
            }
        });
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Room Images");
    }

    private boolean validateLocation()
    {
        address =owner_add_houseLocation.getText().toString().trim().toUpperCase();
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
        deposit =owner_add_rent_deposit.getText().toString().trim().toUpperCase();
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
        amount =owner_add_rent_amount.getText().toString().trim().toUpperCase();
        if(amount.isEmpty())
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
        town=owner_add_town.getText().toString().trim().toUpperCase();
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
        section=owner_add_section.getText().toString().trim().toUpperCase();
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
    private void StoreProductInformation()
    {
        progressDialog.setTitle("Create Room");
        progressDialog.setMessage("Please wait while adding your room");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat CurrentDate = new SimpleDateFormat("yyyy/MM/dd");
        currentDate = CurrentDate.format(calendar.getTime());

        SimpleDateFormat CurrentTime = new SimpleDateFormat("HH:MM:SS a");
        currentTime = CurrentTime.format(calendar.getTime());

        productRandomKey = currentDate + currentTime;
        filePath = ProductImageRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");
        final UploadTask uploadTask = filePath.putFile(ImageUri);

         filePath = ProductImageRef.child(ImageUri2.getLastPathSegment() + productRandomKey + ".jpg");
        final UploadTask uploadTask2 = filePath.putFile(ImageUri2);

        filePath = ProductImageRef.child(ImageUri2.getLastPathSegment() + productRandomKey + ".jpg");
        final UploadTask uploadTask3 = filePath.putFile(ImageUri3);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(getApplicationContext(), "Error: "+message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
                {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();

                        }
                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return  filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if(task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();
                            //Toast.makeText(getContext(), "Got the product image Uri successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Upload failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        });

        uploadTask2.addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(getApplicationContext(), "Error: "+message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                //Toast.makeText(getApplicationContext(), "image1 uploaded successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = uploadTask2.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
                {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();

                        }
                        downloadImageUrl2 = filePath.getDownloadUrl().toString();
                        return  filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if(task.isSuccessful())
                        {
                            downloadImageUrl2 = task.getResult().toString();
                            //Toast.makeText(getContext(), "Got the product image Uri successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Upload failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        });


        uploadTask3.addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(getApplicationContext(), "Error: "+message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                //Toast.makeText(getApplicationContext(), "image1 uploaded successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = uploadTask3.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
                {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();

                        }
                        downloadImageUrl3 = filePath.getDownloadUrl().toString();
                        return  filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if(task.isSuccessful())
                        {
                            downloadImageUrl3 = task.getResult().toString();
                            //Toast.makeText(getContext(), "Got the product image Uri successfully", Toast.LENGTH_SHORT).show();
                            if(radioButton2.equals(R.id.radioButton4))
                            {
                                roomFor ="Student";
                            }
                            if(radioButton2.equals(R.id.radioButton5))
                            {
                                roomFor ="Family";
                            }
                            if(radioButton2.equals(R.id.radioButton6))
                            {
                                roomFor ="Working";
                            }

                            if(checkBoxBathroom.isChecked()){
                                bathroom= "available";
                            }
                            if(checkBoxParking.isChecked()){
                                parking="available";
                            }
                            if(checkBoxTile.isChecked()){
                                tile ="available";
                            }
                            if(checkBoxCeiling.isChecked()){
                                ceiling="available";
                                Toast.makeText(getApplicationContext(), ceiling, Toast.LENGTH_SHORT).show();
                            }
                            DatabaseReference user = FirebaseDatabase.getInstance().getReference("Users");
                            user.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists())
                                    {
                                        String name = snapshot.child("name").getValue().toString();
                                        String contact = snapshot.child("contact").getValue().toString();
                                        String email = snapshot.child("email").getValue().toString();

                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Rooms").child(roomType).push();
                                        String roomID = reference.getKey();
                                        RoomModel model = new RoomModel(deposit,roomID,userId,name,email,contact,downloadImageUrl,downloadImageUrl2,downloadImageUrl3,radioButton2.getText().toString(),
                                                town,amount,address,"available",section, roomType, "Limpopo", radioButton.getText().toString(),
                                                parking,bathroom,toilet,ceiling,tile);

                                        reference.setValue(model)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful())
                                                        {
                                                            DatabaseReference other =FirebaseDatabase.getInstance().getReference("All rooms");
                                                            other.child(roomID).setValue(model)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if(task.isSuccessful())
                                                                            {
                                                                                Intent intent = new Intent(getApplicationContext(), LandLordMainActivity.class);
                                                                                Toast.makeText(getApplicationContext(), "successfully registered", Toast.LENGTH_SHORT).show();
                                                                                progressDialog.dismiss();
                                                                            }
                                                                            else
                                                                            {
                                                                                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                                                                                progressDialog.dismiss();
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                            progressDialog.dismiss();
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
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Upload failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        });

    }
    private void AddRoomDetails()
    {
      if(!validateLocation() | !validateAmount() | !validateTown() | !validateSection() |!validateDeposit())
        return;
      else
      {
          StoreProductInformation();
      }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GalleryPick1 && resultCode == RESULT_OK &&data !=null)
        {
            ImageUri = data.getData();
            first_image.setImageURI(ImageUri);
        }
        if(requestCode == GalleryPick2 && resultCode == RESULT_OK &&data !=null)
        {
            ImageUri2 = data.getData();
            second_image.setImageURI(ImageUri2);
        }
        if(requestCode == GalleryPick3 && resultCode == RESULT_OK &&data !=null)
        {
            ImageUri3 = data.getData();
            third_image.setImageURI(ImageUri3);
        }

    }
    private void OpenGallery1()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick1);
    }
    private void OpenGallery2()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick2);
    }
    private void OpenGallery3()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick3);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        roomType = adapterView.getItemAtPosition(i).toString();
        //txt_room_type.setText(choice);
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}