package com.example.kasirooms.Landlord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kasirooms.ConfirmBooking;
import com.example.kasirooms.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BookRoomActivity extends AppCompatActivity {

    String address,price,status,size,type,image,roomID, landlordId,name,emailAddress,phone,section,town;
    TextView bookRoomAddress,UserRoomType,UserRentAmount,UserRoomSize,UserRoomStatus,landlordname,landlordContact,landlordEmail;
    ImageView email,whatsapp,call,imageView,imag5,imag4;
   Button btnSendMessage;
   TextInputLayout editTextMessage;
   String sendMessage,deposit,image3,image2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book_room);
        imag5 =findViewById(R.id.imag5);
        imag4=findViewById(R.id.imag4);
        UserRoomType = findViewById(R.id.UserRoomType);
        bookRoomAddress=findViewById(R.id.bookRoomAddress);
        UserRentAmount=findViewById(R.id.UserRentAmount);
        UserRoomSize=findViewById(R.id.UserRoomSize);
        UserRoomStatus=findViewById(R.id.UserRoomStatus);
        landlordname=findViewById(R.id.landlordname);
        landlordEmail=findViewById(R.id.landlordEmail);
        landlordContact=findViewById(R.id.landlordContact);
        email = findViewById(R.id.email);
        whatsapp =findViewById(R.id.whatsapp);

        btnSendMessage=findViewById(R.id.btnSendMessage);
        editTextMessage = findViewById(R.id.editTextMessage);
        imageView = findViewById(R.id.imageView);

        Bundle bundle = getIntent().getExtras();
        address= bundle.getString("address");
        price= bundle.getString("price");
        status= bundle.getString("status");
        size= bundle.getString("size");
        image= bundle.getString("image");
        image2= bundle.getString("image2");
        image3= bundle.getString("image3");
        roomID= bundle.getString("roomID");
        type= bundle.getString("type");
        landlordId =bundle.getString("userId");
        deposit =bundle.getString("deposit");
        town =bundle.getString("town");
        section=bundle.getString("section");

        bookRoomAddress.setText(address);
        UserRoomType.setText(type);
        UserRentAmount.setText(price);
        UserRoomStatus.setText(status);
        UserRoomSize.setText(size);
        UserRoomType.setText(type);
        Picasso.get().load(image).resize(390, 230).into(imageView);
        Picasso.get().load(image2).into(imag4);
        Picasso.get().load(image3).into(imag5);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(landlordId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {

                    emailAddress = snapshot.child("email").getValue().toString();
                    name = snapshot.child("name").getValue().toString();
                    phone = snapshot.child("contact").getValue().toString();
                    landlordContact.setText(phone);
                    landlordname.setText(name);
                    landlordEmail.setText(emailAddress);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

    }

    public void sendEmilToLandlord(View view)
    {
        String[] address =emailAddress.split(",");
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL,address);
        intent.putExtra(Intent.EXTRA_SUBJECT,"A MESSAGE TO BOOK A ROOM");
        intent.putExtra(Intent.EXTRA_TEXT,"Good day "+ name+ " i would like to book a room");

        if(intent.resolveActivity(getPackageManager()) !=null)
        {
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No app is installed", Toast.LENGTH_SHORT).show();
        }


    }
    public void sendMessageToLandlord(View view)
    {
        if(ContextCompat.checkSelfPermission(BookRoomActivity.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
            sendMessage();
        }
        else
        {
            ActivityCompat.requestPermissions(BookRoomActivity.this,new String[]{Manifest.permission.SEND_SMS},100);

        }
    }
    private void sendMessage()
    {
        sendMessage =editTextMessage.getEditText().getText().toString().trim();
        if(!phone.equals("") && !sendMessage.equals(""))
        {
            SmsManager smsManager= SmsManager.getDefault();
            smsManager.sendTextMessage(phone,null,sendMessage,null,null);
            Toast.makeText(getApplicationContext(), "Sms sent successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Enter value first to send message", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100 && grantResults.length>0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED)
        {
            sendMessage();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Application denied", Toast.LENGTH_SHORT).show();
        }
    }
    public void CustomerMakeCall(View view)
    {
        String s ="tel:" + 0711234027;
        Intent intent= new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(s));
        startActivity(intent);
    }
    public void sendWhatsaMessageToLandlord(View view)
    {
        String message ="Hello\n\nMy name is "+name+"\n\nI would like to rent this "+type;
        boolean installed = appInstalledOrNot("com.whatsapp");
        if(installed){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+27"+phone+"&text="+message));
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "whatsapp app not installed in your device", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean appInstalledOrNot(String url)
    {
        PackageManager packageManager =getPackageManager();
        boolean app_installed;
        try{
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed =true;
        }
        catch(PackageManager.NameNotFoundException e)
        {
          app_installed=false;
        }
        return app_installed;
    }
//    public void bookRoom(View view)
//    {
//        Intent intent = new Intent(getApplicationContext(), ConfirmBooking.class);
//        intent.putExtra("status",status);
//        intent.putExtra("price",price);
//        intent.putExtra("roomID",roomID);
//        intent.putExtra("deposit",deposit);
//        intent.putExtra("type",type);
//        intent.putExtra("address",address);
//        intent.putExtra("town",town);
//        intent.putExtra("section",section);
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//
//    }
    public void ClickToGoBack(View view) {
    }

    public void BtnBookRoom(View view)
    {
        Intent intent = new Intent(getApplicationContext(), ConfirmBooking.class);
        intent.putExtra("status",status);
        intent.putExtra("price",price);
        intent.putExtra("roomID",roomID);
        intent.putExtra("deposit",deposit);
        intent.putExtra("type",type);
        intent.putExtra("address",address);
        intent.putExtra("town",town);
        intent.putExtra("section",section);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}