package com.lian.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lian.myproject.model.User;

public class MainActivity extends AppCompatActivity {
    public CardView btnAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnAdmin = findViewById(R.id.admin_card);
        btnAdmin.setVisibility(View.GONE);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {

            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(firebaseUser.getUid());

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    User currentUser = snapshot.getValue(User.class);

                    if (currentUser != null && currentUser.isAdmin()) {
                        btnAdmin.setVisibility(View.VISIBLE);
                    } else {
                        btnAdmin.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v ->
                getOnBackPressedDispatcher().onBackPressed()
        );
    }


    public void goBookList(View view){
        Intent go=new Intent(this, BooksListActivity.class);
        startActivity(go);
    }
    public void goUserLoansBook(View view) {
        Intent go= new Intent( this, UserLoansActivity.class);
        startActivity(go);
    }
    public void goProfile(View view){
        Intent go= new Intent( this, UserProfileActivity.class);
        startActivity(go);
    }
    public void goAdmin(View view){
        Intent go=new Intent(this, AdminActivity.class);
        startActivity(go);
    }
}

