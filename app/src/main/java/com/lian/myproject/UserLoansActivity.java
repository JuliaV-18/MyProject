package com.lian.myproject;

import static android.widget.Toast.LENGTH_LONG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.lian.myproject.adapters.BookAdapter;
import com.lian.myproject.adapters.LoanAdapter;
import com.lian.myproject.model.Book;
import com.lian.myproject.model.Loan;
import com.lian.myproject.model.User;
import com.lian.myproject.services.DatabaseService;

import java.util.ArrayList;
import java.util.Date;
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

        setContentView(R.layout.activity_loans);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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