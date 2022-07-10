package com.example.kasirooms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.kasirooms.Admin.AdminMainActivity;
import com.example.kasirooms.Landlord.LandLordMainActivity;
import com.example.kasirooms.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button btnLandlordLogin;
    RelativeLayout relative;
    EditText editTextLandlordEmailAddress, editLandlordTextPassword;
    private String email,password;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;
    private DatabaseReference serviceProviderDatabaseRef;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editLandlordTextPassword =findViewById(R.id.editLandlordTextPassword);
        editTextLandlordEmailAddress =findViewById(R.id.editTextLandlordEmailAddress);
        relative =findViewById(R.id.relative);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser =mAuth.getCurrentUser();
        dialog = new ProgressDialog(this);
    }

    public void LandlordRegisterLink(View view)
    {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }

    public void LoginUser(View view)
    {
        email = editTextLandlordEmailAddress.getText().toString().trim();
        password = editLandlordTextPassword.getText().toString().trim();
        LoginUser(email,password);

    }

    private void LoginUser(String email, String password)
    {
        dialog.setTitle("Login");
        dialog.setMessage("Please wait while checking credentials....");
        dialog.show();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            firebaseUser =mAuth.getCurrentUser();
                            checkUserType(firebaseUser.getUid());

                        }
                        else
                        {
                            Snackbar.make(relative,"Login failed: "+task.getException().getMessage(),Snackbar.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                });
    }

    private void checkUserType(String uid)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            if(snapshot.exists())
                            {

                                UserModel userProfile = snapshot.getValue(UserModel.class);
                                //assert userProfile != null;
                                String userType = (userProfile.getUserType());
                                switch (userType) {
                                    case "Landlord":
                                        Intent intent = new Intent(getApplicationContext(), LandLordMainActivity.class);
                                        intent.putExtra("userId",userProfile.getUserID());
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        dialog.dismiss();
                                        break;
                                    case "Tenant":
                                        Intent intent0 = new Intent(getApplicationContext(), MainActivity.class);
                                        intent0.putExtra("userId",userProfile.getUserID());
                                        intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent0);
                                        dialog.dismiss();
                                        break;
                                    case "Admin":
                                        Intent intent1 = new Intent(getApplicationContext(), AdminMainActivity.class);
                                        intent1.putExtra("userId",userProfile.getUserID());
                                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent1);
                                        dialog.dismiss();
                                        break;

                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "User: "+ email +" does not exits", Toast.LENGTH_SHORT).show();
                               dialog.dismiss();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void ClickToBecomeLandlord(View view)
    {
      Intent intent = new Intent(getApplicationContext(),BecomeLandlord.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
    }
}