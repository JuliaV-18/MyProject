package com.lian.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lian.myproject.adapters.BookAdapter;
import com.lian.myproject.model.Book;
import com.lian.myproject.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class LoansActivity extends AppCompatActivity {

    private static final String TAG = "BooksListActivity";
    private BookAdapter bookAdapter;
    private TextView tvBookCount;

    RecyclerView rvBooks;
    DatabaseService databaseService;

    ArrayList<Book> books=new ArrayList<>();

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




     rvBooks = findViewById(R.id.rv_Loaned_books);
        rvBooks.setLayoutManager(new LinearLayoutManager(this));


    databaseService = DatabaseService.getInstance();


    bookAdapter = new BookAdapter(books,new BookAdapter.OnBookClickListener() {
        @Override
        public void onBookClick(Book book) {
            // Handle book click
            Log.d(TAG, "Book clicked: " + book);
            Intent intent = new Intent(LoansActivity.this, com.lian.myproject.BookProfileActivity.class);
            intent.putExtra("BOOK_UID", book.getId());
            startActivity(intent);
        }

        @Override
        public void onLongBookClick(Book book) {
            // Handle long book click
            Log.d(TAG, "Book long clicked: " + book);
        }
    });
        rvBooks.setAdapter(bookAdapter);
}


@Override
protected void onResume() {
    super.onResume();
    databaseService.getBookList(new DatabaseService.DatabaseCallback<List<Book>>() {
        @Override
        public void onCompleted(List<Book> bookList) {

            books.addAll(bookList);

            bookAdapter.notifyDataSetChanged();
            //   tvBookCount.setText("Total books: " + bookList.size());
        }

        @Override
        public void onFailed(Exception e) {
            Log.e(TAG, "Failed to get books list", e);
        }


    });
}

}