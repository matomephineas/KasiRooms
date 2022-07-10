package com.example.kasirooms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kasirooms.LoginActivity;
import com.example.kasirooms.Models.UserModel;
import com.example.kasirooms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    RelativeLayout relative;
    Button btnLandlordLogin, btnLandlordRegister;
    TextView tvCreateAccount,tvLogin,tvLoginDriver;
    EditText editTextLandlordEmailAddress, editLandlordTextPassword,editLandlordTextAddress,editLandlordTextContact,editLandlordTextName;
    private String email,password,address,contact,user,name;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;
    private DatabaseReference serviceProviderDatabaseRef;
    private String onlineServiceProviderID;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnLandlordRegister =findViewById(R.id.btnLandlordRegister);
        tvCreateAccount=findViewById(R.id.tvCreateAccount);
        radioSexGroup=(RadioGroup)findViewById(R.id.radioButtonSex);

        relative =findViewById(R.id.relative);

        editLandlordTextPassword =findViewById(R.id.editLandlordTextPassword);
        editTextLandlordEmailAddress =findViewById(R.id.editTextLandlordEmailAddress);
        editLandlordTextAddress = findViewById(R.id.editLandlordTextAddress);
        editLandlordTextContact=findViewById(R.id.editLandlordTextContact);
        editLandlordTextName= findViewById(R.id.editTextLandlordName);

        tvLogin =findViewById(R.id.tvRegisterLandlord);
        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);

    }


    public void RegisterLandlord(View view)
    {
        name = editLandlordTextName.getText().toString().trim();
        email = editTextLandlordEmailAddress.getText().toString().trim();
        password = editLandlordTextPassword.getText().toString().trim();
        address = editLandlordTextAddress.getText().toString().trim();
        contact= editLandlordTextContact.getText().toString().trim();

        Register(email,password,address,contact);
    }

    private void Register(String email, String password,String address,String contact)
    {
        if(TextUtils.isEmpty(email))
        {
            editTextLandlordEmailAddress.setError("Field cannot be empty");
            editTextLandlordEmailAddress.requestFocus();
        }
        if(TextUtils.isEmpty(password))
        {
            editTextLandlordEmailAddress.setError("Field cannot be empty");
            editTextLandlordEmailAddress.requestFocus();
        }
        else
        {
            int selectedId=radioSexGroup.getCheckedRadioButtonId();
            radioSexButton=(RadioButton)findViewById(selectedId);

            dialog.setTitle("Register Landlord");
            dialog.setMessage("Please wait while checking credentials.......");
            dialog.show();
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                onlineServiceProviderID =mAuth.getCurrentUser().getUid();
                                serviceProviderDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(onlineServiceProviderID);

                                HashMap<String,Object> map = new HashMap<>();

                                map.put("userID",onlineServiceProviderID);
                                map.put("email",email);
                                map.put("name",name);
                                map.put("contact",contact);
                                map.put("address",address);
                                map.put("userType","Tenant");
                                map.put("gender",radioSexButton.getText().toString());
                                map.put("password",password);


                                serviceProviderDatabaseRef.updateChildren(map)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                              if(task.isSuccessful())
                                              {
                                                  Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                                                  Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                  startActivity(intent);

                                                  dialog.dismiss();
                                              }
                                              else
                                              {
                                                  Toast.makeText(getApplicationContext(), "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                 dialog.dismiss();
                                              }
                                            }
                                        });

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    });
        }
    }


    public void ServiceLandlordToLogin(View view)
    {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}