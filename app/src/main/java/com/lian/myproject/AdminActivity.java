package com.example.testapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testapp.R;
import com.lian.myproject.model.Book;
import com.lian.myproject.model.Loan;

public class AdminActivity extends com.example.testapp.screens.BaseActivity {

    LinearLayout cardUsers, cardFoods, cardCarts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cardUsers = findViewById(R.id.card_users);
        cardFoods = findViewById(R.id.card_foods);
        cardCarts = findViewById(R.id.card_carts);

        cardUsers.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.example.testapp.screens.UsersListActivity.class);
            startActivity(intent);
        });

        cardFoods.setOnClickListener(v -> {
            Intent intent = new Intent(this, Book.class);
            startActivity(intent);
        });

        cardCarts.setOnClickListener(v -> {
            Intent intent = new Intent(this, Loan.class);
            startActivity(intent);
        });
    }
}