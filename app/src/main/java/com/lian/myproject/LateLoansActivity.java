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

import com.lian.myproject.adapters.BookAdapter;
import com.lian.myproject.adapters.LoanAdapter;
import com.lian.myproject.model.Book;
import com.lian.myproject.model.Loan;
import com.lian.myproject.services.DatabaseService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LateLoansActivity extends AppCompatActivity {

    private static final String TAG = "BooksListActivity";
    private LoanAdapter loanAdapter ;


    RecyclerView rvLoans;
    DatabaseService databaseService;

    ArrayList<Loan> loanArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loans);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




     rvLoans = findViewById(R.id.rv_Loaned_books);
        rvLoans.setLayoutManager(new LinearLayoutManager(this));


    databaseService = DatabaseService.getInstance();


    loanAdapter = new LoanAdapter(loanArrayList,new LoanAdapter.OnLoanClickListener() {
        @Override
        public void onLoanClick(Loan loan) {
            Log.d(TAG, "Book clicked: " + loan);
            Intent intent = new Intent(LateLoansActivity.this, LoanABookActivity.class);
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
    databaseService.getBookLoan(new DatabaseService.DatabaseCallback<List<Loan>>() {
        @Override
        public void onCompleted(List<Loan> loanList) {

            loanArrayList.clear();


            Date currentDate = new Date();

            for (Loan loan : loanList) {

              //  if (loan.getReturnDate().before(currentDate)) { // late books
              //      loanArrayList.add(loan);
              //  }

                if (loan.isOverdue()) {
                    loanArrayList.add(loan);

                    Toast.makeText(LateLoansActivity.this,loan.getBorrowDate().toString()+"  "+loan.getReturnDate().toString(),LENGTH_LONG).show();

                }
            }

            loanAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFailed(Exception e) {
            Log.e(TAG, "Failed to get books list", e);
        }


    });
}



}