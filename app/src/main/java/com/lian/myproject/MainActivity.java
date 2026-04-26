package com.lian.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    }


    public void goBookList(View view){
        Intent go=new Intent(this, BooksListActivity.class);
        startActivity(go);
    }



    public void goAddBook(View view) {
        Intent go= new Intent( this, UserLoanActivity.class);
        startActivity(go);
    }
    public void goLoanBook(View view) {

    }

    public void goProfile(View view){
        Intent go= new Intent( this, UserProfileActivity.class);
        startActivity(go);
    }
    public void goAdmin(View view){
        Intent go=new Intent(this, AdminActivity.class);
        startActivity(go);
    }

    public void goUserLoansBook(View view) {

        Intent go= new Intent( this, UserLoanActivity.class);
        startActivity(go);
    }
}

