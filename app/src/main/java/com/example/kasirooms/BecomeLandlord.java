package com.example.kasirooms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class BecomeLandlord extends AppCompatActivity {
    TextInputLayout textInputName, textInputEmail,textInputPhone,textInputPassword,textInputConfirmPassword;
    String name, email,phone,password,confirm;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_landlord);

        textInputName = findViewById(R.id.textRInputName);
        textInputEmail = findViewById(R.id.textRInputEmail);
        textInputPhone = findViewById(R.id.textRInputPhone);
        textInputPassword = findViewById(R.id.textRInputPassword);
        textInputConfirmPassword=findViewById(R.id.textRCInputPassword);
        progressDialog = new ProgressDialog(this);

    }

    public void goBackToLogin(View view)
    {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
    public void SubmitLandlordDetails(View view)
    {
        if(!validateNames() |!validateEmail() |!validatePhone() |!validatePassword() |!validateConfirmPassword())
        {
            return;
        }
        else
        {
            progressDialog.setTitle("Send Details");
            progressDialog.setMessage("Please wait while sending your details.......");
            progressDialog.show();

            String adminEmail = "pmtumelo1@gmail.com";
            String[] address =adminEmail.split(",");
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL,address);
            intent.putExtra(Intent.EXTRA_SUBJECT,"A REQUEST TO BECOME LANDLORD");
            intent.putExtra(Intent.EXTRA_TEXT,"Good day \n\n My name is :"+ name+ "\nemail :"+email+"\nContact :"+
                    phone+"\nPassword :"+password+ "\n\nI would like to register to become a landlord\n\n\nRegards\n"+name);

            if(intent.resolveActivity(getPackageManager()) !=null)
            {
                startActivity(intent);
                progressDialog.dismiss();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "No app is installed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }
    }
}