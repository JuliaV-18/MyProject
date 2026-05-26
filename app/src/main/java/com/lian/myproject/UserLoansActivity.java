package com.lian.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.lian.myproject.adapters.LoanAdapter;
import com.lian.myproject.model.Loan;
import com.lian.myproject.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class UserLoansActivity extends AppCompatActivity {

    private static final String TAG = "BooksListActivity";
    private LoanAdapter loanAdapter;

    RecyclerView rvLoans;
    DatabaseService databaseService;


    ArrayList<Loan> loanArrayList=new ArrayList<>();
    private FirebaseAuth mAuth;
    private String selectedUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_loans);


        mAuth = FirebaseAuth.getInstance();
        selectedUid = mAuth.getUid();


        rvLoans = findViewById(R.id.rv_Loaned_books);
        rvLoans.setLayoutManager(new LinearLayoutManager(this));


        databaseService = DatabaseService.getInstance();


        loanAdapter = new LoanAdapter(loanArrayList,new LoanAdapter.OnLoanClickListener() {
            @Override
            public void onLoanClick(Loan loan) {
                Log.d(TAG, "Book clicked: " + loan);
                Intent intent = new Intent(UserLoansActivity.this, LoanABookActivity.class);
                intent.putExtra("BOOK_UID", loan.getId());
                startActivity(intent);
            }

            @Override
            public void onLongLoanClick(Loan loan) {
                Log.d(TAG, "Book long clicked: " + loan);
            }


        });
        rvLoans.setAdapter(loanAdapter);

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
    protected void onResume() {
        super.onResume();
        databaseService.getUserBookLoan(  selectedUid,new DatabaseService.DatabaseCallback<List<Loan>>() {
            @Override
            public void onCompleted(List<Loan> loanList) {

                loanArrayList.clear();


            loanArrayList.addAll(loanList);

                loanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "Failed to get books list", e);
            }


        });
    }



}