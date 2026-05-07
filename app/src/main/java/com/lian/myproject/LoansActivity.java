package com.lian.myproject;

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

import com.lian.myproject.adapters.LoanAdapter;
import com.lian.myproject.adapters.LoanAdapter;
import com.lian.myproject.model.Loan;
import com.lian.myproject.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class LoansActivity extends AppCompatActivity {

    private static final String TAG = "LoansListActivity";
    private LoanAdapter loanAdapter;
    private TextView tvLoanCount;

    RecyclerView rvLoans;
    DatabaseService databaseService;

    ArrayList<Loan> loans=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loans);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




     rvLoans = findViewById(R.id.rv_Loaned_books);
        rvLoans.setLayoutManager(new LinearLayoutManager(this));


    databaseService = DatabaseService.getInstance();


    loanAdapter = new LoanAdapter(loans,new LoanAdapter.OnLoanClickListener() {
        @Override
        public void onLoanClick(Loan loan) {
            // Handle loan click
            Log.d(TAG, "Loan clicked: " + loan);
            Intent intent = new Intent(LoansActivity.this, ProfileLoan.class);
            intent.putExtra("LOAN_UID", loan.getId());
            startActivity(intent);

        }

        @Override
        public void onLongLoanClick(Loan loan) {
            // Handle long loan click
            Log.d(TAG, "Loan long clicked: " + loan);
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

            loans.addAll(loanList);

            loanAdapter.notifyDataSetChanged();
            //   tvLoanCount.setText("Total loans: " + loanList.size());
        }

        @Override
        public void onFailed(Exception e) {
            Log.e(TAG, "Failed to get loans list", e);
        }


    });
}

}