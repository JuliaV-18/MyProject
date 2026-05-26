package com.lian.myproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lian.myproject.services.DatabaseService;

public class LoginActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private EditText etEmail, etPassword;
    private Button btnLogin;

    String email, password;
    private TextView tvLogin;
    private DatabaseService databaseService;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        databaseService = DatabaseService.getInstance();

        /// get the views

        etEmail = findViewById(R.id.etEmailLogIn);
        etPassword = findViewById(R.id.etPswLogIn);
        btnLogin = findViewById(R.id.btnSubmit);

        email = sharedpreferences.getString("email", "");
        password = sharedpreferences.getString("password", "");
        etEmail.setText(email);
        etPassword.setText(password);


        /// set the click listener
        btnLogin.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v ->
                getOnBackPressedDispatcher().onBackPressed()
        );
    }

    @Override
    public void onClick(View v) {

    }
}
