package com.lian.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.lian.myproject.model.Book;
import com.lian.myproject.model.Loan;
import com.lian.myproject.services.DatabaseService;

public class EditBookProfileActivity extends AppCompatActivity {

    private ImageView imgBookCover;
    private EditText etTitle, etAuthor, etCopies, etGenre, etDesc;
    private Button btnEdit, btnCancel;

    Intent takeit;

    String bookId;

    Book book;

    DatabaseService databaseService;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_edit_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        databaseService=DatabaseService.getInstance();
        takeit=getIntent();
        bookId=takeit.getStringExtra("BOOK_UID");


        databaseService.getBook(bookId, new DatabaseService.DatabaseCallback<Book>() {
            @Override
            public void onCompleted(Book thebook) {

                book=thebook;


                etTitle.setText(book.getTitle());
                etAuthor.setText(book.getAuthor());
               etCopies.setText(book.getCopiesAvailable()+"");
                 etGenre.setText(book.getCategory());
                 etDesc.setText(book.getDescription());

            }

            @Override
            public void onFailed(Exception e) {

            }
        });



        // קישור ל־XML
        imgBookCover = findViewById(R.id.img_profile_book_cover);

        etTitle = findViewById(R.id.tv_profile_book_title);
        etAuthor = findViewById(R.id.tv_profile_book_author);
        etCopies = findViewById(R.id.tv_profile_book_copies);
        etGenre = findViewById(R.id.tv_profile_book_genre);
        etDesc = findViewById(R.id.tv_profile_book_desc);

        btnEdit = findViewById(R.id.btn_loan_book);
        btnCancel = findViewById(R.id.btn_cancel_loan);

        // כפתור השאלה
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mAuth = FirebaseAuth.getInstance();
                java.lang.String uid= mAuth.getUid();




                  String loanId= databaseService.generateLoanId();
                    Loan newLoan=new Loan(loanId,bookId,book.getTitle(),uid);

                    databaseService.createNewLoan(newLoan, new DatabaseService.DatabaseCallback<Void>() {
                        @Override
                        public void onCompleted(Void object) {

                            book.setAvailable(false);







                            Intent go= new Intent( EditBookProfileActivity.this, BooksListActivity.class);
                            startActivity(go);
                        }

                        @Override
                        public void onFailed(Exception e) {

                        }
                    });


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