package com.lian.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lian.myproject.R;
import com.lian.myproject.model.Book;
import com.lian.myproject.model.Loan;

public class AdminActivity extends com.lian.myproject.BaseActivity {

    LinearLayout cardUsers, cardFoods, cardCarts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        }

        public void goEditUsers(View view){
            Intent go= new Intent( this, UsersListActivity.class);
            startActivity(go);
        }
        public void goEditBooks(View view){
            Intent go=new Intent(this, BooksListActivity.class);
            startActivity(go);
        }
        public void goAddBook(View view){
            Intent go=new Intent(this, AddBook.class);
            startActivity(go);
        }


    }