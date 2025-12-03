package com.lian.myproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.core.view.View;
import com.lian.myproject.services.DatabaseService;

public class Login extends AppCompatActivity implements android.view.View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private EditText etEmail, etPassword;
    private Button btnLogin;

    String email, password;
    private TextView tvLogin;
    private DatabaseService databaseService;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        databaseService=DatabaseService.getInstance();

        /// get the views

        etEmail = findViewById(R.id.etEmailLogIn);
        etPassword = findViewById(R.id.etPswLogIn);
        btnLogin = findViewById(R.id.btnSubmit);

        email=sharedpreferences.getString("email","");
        password=sharedpreferences.getString("password","");
        etEmail.setText(email);
        etPassword.setText(password);




        /// set the click listener
        btnLogin.setOnClickListener(this);

    }

    private void loginUser(String email, String password) {
        databaseService.LoginUser(email, password, new DatabaseService.DatabaseCallback<String>() {
            /// Callback method called when the operation is completed
           // /// @param email  & password is logged in
            @Override
            public void onCompleted(String  uid) {
                Log.d(TAG, "onCompleted: User logged in: " + uid.toString());
                /// save the user data to shared preferences
                // SharedPreferencesUtil.saveUser(Login.this, user);


                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString("email", email);
                editor.putString("password",password);

                editor.commit();

                /// Redirect to main activity and clear back stack to prevent user from going back to login screen
                Intent mainIntent = new Intent(Login.this, MainActivity.class);
                /// Clear the back stack (clear history) and start the MainActivity
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "onFailed: Failed to retrieve user data", e);
                /// Show error message to user
                etPassword.setError("Invalid email or password");
                etPassword.requestFocus();
                /// Sign out the user if failed to retrieve user data
                /// This is to prevent the user from being logged in again
            //    SharedPreferencesUtil.signOutUser(Login.this);
            }
        });
    }

    @Override
    public void onClick(android.view.View v) {
        if (v== btnLogin) {
            Log.d(TAG, "onClick: Login button clicked");

            /// get the email and password entered by the user
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            /// log the email and password
            Log.d(TAG, "onClick: Email: " + email);
            Log.d(TAG, "onClick: Password: " + password);

            Log.d(TAG, "onClick: Validating input...");
            /// Validate input


            Log.d(TAG, "onClick: Logging in user...");

            /// Login user
            loginUser(email, password);
        }
        //  Intent registerIntent = new Intent(Login.this, Login.class);
        //startActivity(registerIntent);
    }
}
// email2=sharedpreferences.getString("email","");
// pass2=sharedpreferences.getString("password","");
// etEmail2.setText(email2);
// etPass2.setText(pass2);


// /// Method to check if the input is valid
//    /// It checks if the email and password are valid
//    /// @see Validator#isEmailValid(String)
//    /// @see Validator#isPasswordValid(String)
//    private boolean checkInput(String email, String password) {
//        if (!Validator.isEmailValid(email)) {
//            Log.e(TAG, "checkInput: Invalid email address");
//            /// show error message to user
//            etEmail.setError("Invalid email address");
//            /// set focus to email field
//            etEmail.requestFocus();
//            return false;
//        }
//
//        if (!Validator.isPasswordValid(password)) {
//            Log.e(TAG, "checkInput: Invalid password");
//            /// show error message to user
//            etPassword.setError("Password must be at least 6 characters long");
//            /// set focus to password field
//            etPassword.requestFocus();
//            return false;
//        }
//
//        return true;
//    }

// if (!checkInput(email, password)) {
//                /// stop if input is invalid
//                return;
//            }