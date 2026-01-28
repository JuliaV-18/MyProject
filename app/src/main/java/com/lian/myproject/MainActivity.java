package com.lian.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
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


    public void goRegister(View view) {
        Intent go= new Intent( this, Register.class);
        startActivity(go);
    }

    public void goLogin(View view) {
        Intent go= new Intent( this, Login.class);
        startActivity(go);
    }

    public void goAddBook(View view) {

        Intent go= new Intent( this, AddBook.class);
        startActivity(go);
    }

}

