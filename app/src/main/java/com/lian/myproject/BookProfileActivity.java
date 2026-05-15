package com.lian.myproject;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.lian.myproject.model.Book;
import com.lian.myproject.model.Loan;
import com.lian.myproject.model.User;
import com.lian.myproject.services.DatabaseService;

public class BookProfileActivity extends AppCompatActivity {

    private ImageView imgBookCover;
    private EditText etTitle, etAuthor, etCopies, etGenre, etDesc;
    private Button btnEdit, btnCancel;

    Intent takeit;

    String bookId;
    Book selectedBook;
    Book book;
    DatabaseService databaseService;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_book);

        databaseService=DatabaseService.getInstance();
        takeit=getIntent();
        bookId=takeit.getStringExtra("BOOK_UID");


        databaseService.getBook(bookId, new DatabaseService.DatabaseCallback<Book>() {
            @Override
            public void onCompleted(Book thebook) {

                selectedBook=thebook;


                etTitle.setText(book.getTitle());
                etAuthor.setText(book.getAuthor());
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
        etGenre = findViewById(R.id.tv_profile_book_genre);
        etDesc = findViewById(R.id.tv_profile_book_desc);

        btnEdit = findViewById(R.id.btn_edit_book);
        btnCancel = findViewById(R.id.btn_cancel_edit_book);

        // כפתור השאלה
        btnEdit.setOnClickListener(new View.OnClickListener() {
            private void updateBookInDatabase(User user) {
                Log.d(TAG, "Updating book in database: " + book.getId());
                databaseService.updateBook(book, new DatabaseService.DatabaseCallback<Void>() {
                    @Override
                    public void onCompleted(Void result) {
                        Log.d(TAG, "Book profile updated successfully");
                        Toast.makeText(BookProfileActivity.this, "Profile updated successfully  "+ selectedBook.toString(), Toast.LENGTH_SHORT).show();
                        //showUserProfile(); // Refresh the profile view
                    }

                    @Override
                    public void onFailed(Exception e) {
                        Log.e(TAG, "Error updating book profile", e);
                        Toast.makeText(BookProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onClick(View v) {


                mAuth = FirebaseAuth.getInstance();
                java.lang.String uid= mAuth.getUid();




//                  String loanId= databaseService.generateLoanId();
//                    Loan newLoan=new Loan(loanId,bookId,book.getTitle(),uid);
//
//                    databaseService.createNewLoan(newLoan, new DatabaseService.DatabaseCallback<Void>() {
//                        @Override
//                        public void onCompleted(Void object) {
//
//                            book.setAvailable(false);
//
//                            Intent go= new Intent( BookProfileActivity.this, BooksListActivity.class);
//                            startActivity(go);
//                        }
//
//                        @Override
//                        public void onFailed(Exception e) {
//
//                        }
//                    });


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