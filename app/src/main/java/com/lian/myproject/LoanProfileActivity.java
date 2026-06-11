

package com.lian.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lian.myproject.R;
import com.lian.myproject.model.Book;
import com.lian.myproject.model.Loan;
import com.lian.myproject.model.User;
import com.lian.myproject.services.DatabaseService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LoanProfileActivity extends AppCompatActivity {

    private TextView tvUserName;
    private TextView tvBookName;
    private TextView tvBorrowDate;
    private TextView tvReturnDate;
    private TextView tvExtraTime;

    private Button btnCancelLoan;
    private Button btnReturnBook;
    private Button btnExtraTime;

    private DatabaseService databaseService;

    private Loan currentLoan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_loan);

        initViews();

        databaseService = com.lian.myproject.services.DatabaseService.getInstance();

        String loanId = getIntent().getStringExtra("LOAN_UID");

        if (loanId == null) {
            Toast.makeText(this, "Loan not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadLoan(loanId);

        btnCancelLoan.setOnClickListener(v -> finish());

        btnReturnBook.setOnClickListener(v -> returnBook());

        btnExtraTime.setOnClickListener(v -> addExtraTime());
    }

    private void initViews() {

        tvUserName = findViewById(R.id.tv_return_loan_user_name);
        tvBookName = findViewById(R.id.tv_return_loan_book_name);
        tvBorrowDate = findViewById(R.id.tv_borrow_date);
        tvReturnDate = findViewById(R.id.tv_return_date);
        tvExtraTime = findViewById(R.id.tv_user_extra_time);

        btnCancelLoan = findViewById(R.id.btn_cancel_loan);
        btnReturnBook = findViewById(R.id.btn_return_book);
        btnExtraTime = findViewById(R.id.btn_extra_time);
    }

    private void loadLoan(String loanId) {

        databaseService.getLoan(loanId, new DatabaseService.DatabaseCallback<Loan>() {
            @Override
            public void onCompleted(Loan loan) {

                currentLoan = loan;

                if (loan == null) {
                    Toast.makeText(LoanProfileActivity.this,
                            "Loan not found",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                showLoanData();
            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(LoanProfileActivity.this,
                        e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoanData() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        databaseService.getUser(currentLoan.getUserId(), new DatabaseService.DatabaseCallback<User>() {

            @Override
            public void onFailed(Exception e) {

            }

            @Override
            public void onCompleted(User user) {

                tvUserName.setText("User's name: " + user.getFullName());


            }
        });





        tvBookName.setText("Book title: " + currentLoan.getBookName());

        if (currentLoan.getBorrowDate() != null) {
            tvBorrowDate.setText(sdf.format(currentLoan.getBorrowDate()));
        }

        if (currentLoan.getReturnDate() != null) {
            tvReturnDate.setText(sdf.format(currentLoan.getReturnDate()));
        }
    }

    private void returnBook() {

        databaseService.getBook(currentLoan.getBookId(), new DatabaseService.DatabaseCallback<Book>() {

                    @Override
                    public void onCompleted(Book book) {

                        book.setAvailable(true);

                        databaseService.updateBook(book, new DatabaseService.DatabaseCallback<Void>() {

                                    @Override
                                    public void onCompleted(Void object) {

                                        databaseService.deleteLoan(currentLoan, new DatabaseService.DatabaseCallback<Void>() {

                                                    @Override
                                                    public void onCompleted(Void object) {

                                                        Toast.makeText(LoanProfileActivity.this, "Book returned successfully", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    }

                                                    @Override
                                                    public void onFailed(Exception e) {

                                                    }
                                                });
                                    }

                                    @Override
                                    public void onFailed(Exception e) {

                                    }
                                });
                    }

                    @Override
                    public void onFailed(Exception e) {

                    }
                });
    }

    private void addExtraTime() {

        Toast.makeText(this, "Extra time added", Toast.LENGTH_SHORT).show();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentLoan.getReturnDate());
        calendar.add(Calendar.DAY_OF_MONTH, 7);

        currentLoan.setReturnDate(calendar.getTime());


        databaseService.updateLoan(currentLoan, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onFailed(Exception e) {

                Toast.makeText(LoanProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCompleted(Void object) {


                startActivity(new Intent(
                        LoanProfileActivity.this,
                        AllLoansActivity.class));

                finish();            }

            });

    }
}

