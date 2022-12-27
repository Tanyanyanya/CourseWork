package com.coursewk.bookapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.coursewk.bookapp.adapters.AdapterCategory;
import com.coursewk.bookapp.databinding.ActivityDashboardAdminBinding;
import com.coursewk.bookapp.models.ModelCategory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardAdminActivity extends AppCompatActivity {
    //fire base auth
    private ActivityDashboardAdminBinding binding;
    private FirebaseAuth firebaseAuth;
    //public TextView affuser ;
    // public ImageButton logout;
    // public Button add;
    private ArrayList<ModelCategory> categoryArrayList;
    private AdapterCategory adapterCategory ;
    // public RecyclerView categoriesRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityDashboardAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth=FirebaseAuth.getInstance();
        checkuser();

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                checkuser();



            }
        });

        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    adapterCategory.getFilter().filter(s);

                }
                catch (Exception e){

                }

                if(adapterCategory != null){
                    adapterCategory.getFilter().filter(s);

                }
                //  Filter filter = adapterCategory.getFilter();}
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




        binding.addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardAdminActivity.this, CategoryAddActivity.class));
            }
        });

        //start pdf add screen
        binding.addPdfFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardAdminActivity.this, PdfAddActivity.class));

            }
        });

        loadCategories();

    }



    private void loadCategories() {
        categoryArrayList= new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Categories");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryArrayList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    ModelCategory model = ds.getValue(ModelCategory.class);

                    categoryArrayList.add(model);

                }
                adapterCategory = new AdapterCategory(DashboardAdminActivity.this,categoryArrayList);

                binding.categoriesRv.setAdapter(adapterCategory);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkuser() {
        //get current user
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        if (firebaseUser==null){
            //not logged in ,go main screen
            startActivity(new Intent(DashboardAdminActivity.this, MainActivity.class));
            finish();

        }
        else {
            //logged in , get user info
            String email =firebaseUser.getEmail();
            //affuser.setText(email);
            binding.subtitleTv.setText(email);

        }
    }
}