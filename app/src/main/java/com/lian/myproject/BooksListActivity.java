package com.lian.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
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

public class BooksListActivity extends AppCompatActivity {

    private static final String TAG = "BooksListActivity";
    private BookAdapter bookAdapter;
    private TextView tvBookCount;


    DatabaseService databaseService;

    ArrayList<Book>books=new ArrayList<>();
    private SearchView searchView;
    private RecyclerView rvBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_books_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

         rvBooks = findViewById(R.id.rv_books_list);

         searchView = findViewById(R.id.searchView);
        rvBooks.setLayoutManager(new LinearLayoutManager(this));


        databaseService = DatabaseService.getInstance();


        bookAdapter = new BookAdapter(books,new BookAdapter.OnBookClickListener() {
            @Override
            public void onBookClick(Book book) {
                // Handle book click
                Log.d(TAG, "Book clicked: " + book);
                Intent intent = new Intent(BooksListActivity.this, com.lian.myproject.BookProfileActivity.class);
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


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterBooks(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               filterBooks(newText);
                return false;
            }



        });
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

    public void filterBooks(String query) {
        if (query == null || query.isEmpty()) {
            bookAdapter.setBookList(books);
            bookAdapter.notifyDataSetChanged();
            return;
        }

        String lowerQuery = query.toLowerCase();
        List<Book> filteredList = new ArrayList<>();

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(lowerQuery) ||
                    book.getAuthor().toLowerCase().contains(lowerQuery)) {

                filteredList.add(book);
            }
        }


        bookAdapter.setBookList(filteredList);
        bookAdapter.notifyDataSetChanged();
    }



}