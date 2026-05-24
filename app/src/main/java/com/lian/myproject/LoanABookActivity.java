package com.lian.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.lian.myproject.model.Book;
import com.lian.myproject.model.Loan;
import com.lian.myproject.services.DatabaseService;

public class LoanABookActivity extends AppCompatActivity {

    private ImageView imgBookCover;
    private TextView tTitle, tAuthor, tGenre, tDesc;
    private Button btnLoan, btnCancel;
    Intent takeit;
    String bookId;
    Book book;
    DatabaseService databaseService;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loan_a_book);


        databaseService=DatabaseService.getInstance();
        takeit=getIntent();
        bookId=takeit.getStringExtra("BOOK_UID");


        databaseService.getBook(bookId, new DatabaseService.DatabaseCallback<Book>() {
            @Override
            public void onCompleted(Book thebook) {

                book=thebook;

                tTitle.setText(book.getTitle());
                tAuthor.setText(book.getAuthor());
                tGenre.setText(book.getCategory());
                tDesc.setText(book.getDescription());
            }

            @Override
            public void onFailed(Exception e) {

            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v ->
                getOnBackPressedDispatcher().onBackPressed()
        );


        // קישור ל־XML
        imgBookCover = findViewById(R.id.img_profile_book_cover);

        tTitle = findViewById(R.id.tv_profile_book_title);
        tAuthor = findViewById(R.id.tv_profile_book_author);
        tGenre = findViewById(R.id.tv_profile_book_genre);
        tDesc = findViewById(R.id.tv_profile_book_desc);

        btnLoan = findViewById(R.id.btn_loan_book);
        btnCancel = findViewById(R.id.btn_cancel_loan);



        // כפתור השאלה
        btnLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mAuth = FirebaseAuth.getInstance();
                String uid= mAuth.getUid();

                if (book.isAvailable()) {

                    String loanId = databaseService.generateLoanId();
                    Loan newLoan = new Loan(loanId, bookId, book.getTitle(), uid);

                    databaseService.createNewLoan(newLoan, new DatabaseService.DatabaseCallback<Void>() {
                        @Override
                        public void onCompleted(Void object) {

                            book.setAvailable(false);

                            Toast.makeText(LoanABookActivity.this, "Book transaction is complete!", Toast.LENGTH_SHORT).show();
                            Intent go = new Intent(LoanABookActivity.this, BooksListActivity.class);
                            startActivity(go);
                        }

                        @Override
                        public void onFailed(Exception e) {

                        }
                    });
                }
                else Toast.makeText(LoanABookActivity.this, "This book is taken :(", Toast.LENGTH_SHORT).show();


            }
        });

        // כפתור ביטול
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // סוגר את המסך
            }
        });

    }
}