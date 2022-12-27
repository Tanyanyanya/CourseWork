package com.coursewk.bookapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.coursewk.bookapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CategoryAddActivity extends AppCompatActivity {
    public Button addcat;
    public EditText category;
    public ImageButton back ;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);
        addcat=(Button)findViewById(R.id.submitBtn);
        category=(EditText)findViewById(R.id.categoryEt);
        back=(ImageButton) findViewById(R.id.backBtn);

        firebaseAuth=FirebaseAuth.getInstance();
        //begin upload

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        addcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateDate();
            }
        });
    }

    private void validateDate() {
        //get data
        String cat=category.getText().toString().trim();
        if (TextUtils.isEmpty(cat)){
            Toast.makeText(this," please enter category!!",Toast.LENGTH_LONG).show();
        } else {
            addCategoryFirebase();

        }
    }

    private void addCategoryFirebase() {
        //get timestamp
        long timestamp = System.currentTimeMillis();
        String catname=category.getText().toString().trim();

        //setup info to ad in db
        HashMap<String,Object> hashMap= new HashMap<>();
        hashMap.put("id",""+timestamp);
        hashMap.put("category",""+catname);
        hashMap.put("timestamp",""+timestamp);
        hashMap.put("uid",""+firebaseAuth.getUid());

        //add to db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Categories");
        ref.child(""+timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //category add success
                Toast.makeText(CategoryAddActivity.this," category added sucessfully!!",Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //category add failed
                Toast.makeText(CategoryAddActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }
}