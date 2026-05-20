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
    private EditText etTitle, etAuthor, etGenre, etDesc;
    private Button btnEdit, btnCancel;

    Intent takeit;

    String bookId;
    Book selectedBook;

    DatabaseService databaseService;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_book);

        databaseService = DatabaseService.getInstance();

        takeit = getIntent();
        bookId = takeit.getStringExtra("BOOK_UID");

        // XML
        imgBookCover = findViewById(R.id.img_profile_book_cover);

        etTitle = findViewById(R.id.tv_profile_book_title);
        etAuthor = findViewById(R.id.tv_profile_book_author);
        etGenre = findViewById(R.id.tv_profile_book_genre);
        etDesc = findViewById(R.id.tv_profile_book_desc);

        btnEdit = findViewById(R.id.btn_edit_book);
        btnCancel = findViewById(R.id.btn_cancel_edit_book);

        // load book
        databaseService.getBook(bookId, new DatabaseService.DatabaseCallback<Book>() {

            @Override
            public void onCompleted(Book thebook) {

                selectedBook = thebook;

                etTitle.setText(selectedBook.getTitle());
                etAuthor.setText(selectedBook.getAuthor());
                etGenre.setText(selectedBook.getCategory());
                etDesc.setText(selectedBook.getDescription());
            }

            @Override
            public void onFailed(Exception e) {

                Toast.makeText(BookProfileActivity.this,
                        "Failed loading book",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // edit button
        btnEdit.setOnClickListener(v -> {

            selectedBook.setTitle(etTitle.getText().toString());
            selectedBook.setAuthor(etAuthor.getText().toString());
            selectedBook.setCategory(etGenre.getText().toString());
            selectedBook.setDescription(etDesc.getText().toString());

            Log.d(TAG, "Updating book");

            databaseService.updateBook(selectedBook,
                    new DatabaseService.DatabaseCallback<Void>() {

                        @Override
                        public void onCompleted(Void result) {

                            Toast.makeText(
                                    BookProfileActivity.this,
                                    "Book updated successfully",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }

                        @Override
                        public void onFailed(Exception e) {

                            Toast.makeText(
                                    BookProfileActivity.this,
                                    "Failed updating book",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    });
        });

        // cancel button
        btnCancel.setOnClickListener(v -> finish());

    }
}