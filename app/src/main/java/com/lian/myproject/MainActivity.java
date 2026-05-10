package com.lian.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lian.myproject.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Find the admin card
        adminCard = findViewById(R.id.admin_card);

        // Hide it by default
        adminCard.setVisibility(View.GONE);

        // Get current Firebase user
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser.isAdmin()) {
            adminCard.setVisibility(View.VISIBLE);
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

