package com.example.kasirooms.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.kasirooms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddLandlordDialogFragment extends DialogFragment
{
    TextInputLayout textInputName, textInputEmail,textInputPhone,textInputPassword,textInputConfirmPassword;
    Button btnLSubmitLandlord;
    String name, email,phone,password,confirm;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.input_landlord_dialog, container, false);
        btnLSubmitLandlord = view.findViewById(R.id.btnLSubmitLandlord);
        textInputName = view.findViewById(R.id.textInputName);
        textInputEmail = view.findViewById(R.id.textInputEmail);
        textInputPhone = view.findViewById(R.id.textInputPhone);
        textInputPassword = view.findViewById(R.id.textInputPassword);
        textInputConfirmPassword=view.findViewById(R.id.textInputConfirmPassword);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());
        btnLSubmitLandlord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitDetails();
            }
        });
        return view;
    }

    private boolean validatePhone()
    {
        phone = textInputPhone.getEditText().getText().toString().trim().toUpperCase();
        if(phone.isEmpty())
        {
            textInputPhone.setError("filed must not be empty");
            textInputPhone.requestFocus();
            return false;
        }
        else
        {
            textInputPhone.setError(null);
            textInputPhone.requestFocus();
            return true;
        }
    }
    private boolean validatePassword()
    {
        password = textInputPassword.getEditText().getText().toString().trim();
        if(phone.isEmpty())
        {
            textInputPassword.setError("filed must not be empty");
            textInputPassword.requestFocus();
            return false;
        }
        else
        {
            textInputPassword.setError(null);
            textInputPassword.requestFocus();
            return true;
        }
    }
    private boolean validateConfirmPassword()
    {
        confirm = textInputConfirmPassword.getEditText().getText().toString().trim();
        if(confirm.isEmpty())
        {
            textInputConfirmPassword.setError("filed must not be empty");
            textInputConfirmPassword.requestFocus();
            return false;
        }
        else if(!confirm.equals(password))
        {
            textInputConfirmPassword.setError("passwords do not match");
            textInputConfirmPassword.requestFocus();
            return false;
        }
        else
        {
            textInputConfirmPassword.setError(null);
            textInputConfirmPassword.requestFocus();
            return true;
        }
    }
    private boolean validateEmail()
    {
        email = textInputEmail.getEditText().getText().toString().trim();
        if(email.isEmpty())
        {
            textInputEmail.setError("filed must not be empty");
            textInputEmail.requestFocus();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputEmail.setError("email address is not valid");
            textInputEmail.requestFocus();
            return false;
        }
        else
        {
            textInputEmail.setError(null);
            textInputEmail.requestFocus();
            return true;
        }
    }
    private boolean validateNames()
    {
        name = textInputName.getEditText().getText().toString().trim();
        if(name.isEmpty())
        {
            textInputName.setError("filed must not be empty");
            textInputName.requestFocus();
            return false;
        }
        else
        {
            textInputName.setError(null);
            textInputName.requestFocus();
            return true;
        }
    }

    private void SubmitDetails()
    {
        if(!validateNames() |!validateEmail() |!validatePhone() |!validatePassword() |!validateConfirmPassword())
        {
            return;
        }
        else
        {
            progressDialog.setTitle("Register Landlord");
            progressDialog.setMessage("Please wait while checking credentials.......");
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                               String onlineServiceProviderID =mAuth.getCurrentUser().getUid();
                              DatabaseReference serviceProviderDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(onlineServiceProviderID);

                                HashMap<String,Object> map = new HashMap<>();

                                map.put("userID",onlineServiceProviderID);
                                map.put("email",email);
                                map.put("name",name);
                                map.put("contact",phone);
                                map.put("userType","Landlord");
                                map.put("password",password);


                                serviceProviderDatabaseRef.updateChildren(map)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                if(task.isSuccessful())
                                                {
                                                    Toast.makeText(getContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getContext(), AdminMainActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);

                                                    progressDialog.dismiss();
                                                }
                                                else
                                                {
                                                    Toast.makeText(getContext(), "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    progressDialog.dismiss();
                                                }
                                            }
                                        });

                            }
                            else
                            {
                                Toast.makeText(getContext(), "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    });

        }
    }
}
