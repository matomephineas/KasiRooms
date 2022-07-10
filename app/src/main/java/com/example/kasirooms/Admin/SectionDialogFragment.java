package com.example.kasirooms.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SectionDialogFragment extends DialogFragment
{
    TextInputLayout textInputSection,textInputTown;
    Button btnSubmitSection;
    String section,town;
    private ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.input_dialog, container, false);
        btnSubmitSection = view.findViewById(R.id.btnSubmitSection);
        textInputSection = view.findViewById(R.id.textInputSection);
        textInputTown = view.findViewById(R.id.textInputTown);
        progressDialog = new ProgressDialog(getContext());

        btnSubmitSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitSectionDetails();
            }
        });
        return view;
    }


    private boolean validateTown()
    {
        town=textInputTown.getEditText().getText().toString().trim().toUpperCase();
        if(town.isEmpty())
        {
            textInputTown.setError("filed must not be empty");
            textInputTown.requestFocus();
            return false;
        }
        else
        {
            textInputTown.setError(null);
            textInputTown.requestFocus();
            return true;
        }
    }
    private boolean validateSection()
    {
        section =textInputSection.getEditText().getText().toString().trim().toUpperCase();
        if(section.isEmpty())
        {
            textInputSection.setError("filed must not be empty");
            textInputSection.requestFocus();
            return false;
        }
        else
        {
            textInputSection.setError(null);
            textInputSection.requestFocus();
            return true;
        }
    }

    private void SubmitSectionDetails()
    {
        if(!validateSection() | !validateTown())
            return;
        else
        {
            progressDialog.setTitle("Create location");
            progressDialog.setMessage("Please wait while adding your location");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Locations").push();
            String id = ref.getKey();

            HashMap<String,Object> map = new HashMap<>();
            map.put("section",section);
            map.put("town",town);
            map.put("locationID",id);

            ref.updateChildren(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getContext(), "Location added successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(),SectionDialogFragment.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                progressDialog.dismiss();
                            }
                            else
                            {
                                Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
        }
    }
}
