package com.lian.myproject;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserLoanActivity extends AppCompatActivity {
    private static final String TAG = "UserLoanBooksListActivity";
    private LoanAdapter loanAdapter;


    RecyclerView rvLoans;
    DatabaseService databaseService;

    ArrayList<Loan> loans=new ArrayList<>();

    FirebaseAuth firebaseAuth;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loans_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseAuth=FirebaseAuth.getInstance();
        userId=firebaseAuth.getUid();


        rvLoans = findViewById(R.id.rv_UserLoaned_books);
        rvLoans.setLayoutManager(new LinearLayoutManager(this));


        databaseService = DatabaseService.getInstance();


        loanAdapter = new LoanAdapter(loans, new LoanAdapter.OnLoanClickListener() {
            @Override
            public void onLoanClick(Loan loan) {
                // Handle book click
                Log.d(TAG, "Book clicked: " + loan);
//                Intent intent = new Intent(UserLoanActivity.this, com.lian.myproject.BookProfileActivity.class);
//                intent.putExtra("BOOK_UID", book.getId());
//                startActivity(intent);
            }

            @Override
            public void onLongLoanClick(Loan loan) {
                // Handle long book click
                Log.d(TAG, "Book long clicked: " + loan);
                //החזרת ספר

                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();


                loan.setReturnDate(date);
                loan.setReturned(true);

                databaseService.updateLoan(loan, new DatabaseService.DatabaseCallback<Void>() {
                    @Override
                    public void onCompleted(Void object) {

                    }

                    @Override
                    public void onFailed(Exception e) {

                    }
                });



            }
        });


        rvLoans.setAdapter(loanAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        databaseService.getUserBookLoan( userId, new DatabaseService.DatabaseCallback<List<Loan>>() {
            @Override
            public void onCompleted(List<Loan> loanList) {

                loans.addAll(loanList);

                loanAdapter.notifyDataSetChanged();
                //   tvBookCount.setText("Total books: " + bookList.size());
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "Failed to get loan list", e);
            }


        });
    }

}
