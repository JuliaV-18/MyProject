package com.lian.myproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.lian.myproject.R;
import com.lian.myproject.model.Book;
import com.lian.myproject.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {


    public interface OnBookClickListener {
        void onBookClick(Book book);

        void onLongBookClick(Book book);
    }

    private final List<Book> bookList;
    private final BookAdapter.OnBookClickListener onBookClickListener;

    public BookAdapter(List<Book> bookList, OnBookClickListener onBookClickListener) {
        this.bookList = bookList;
        this.onBookClickListener = onBookClickListener;
    }

    public BookAdapter(@Nullable final BookAdapter.OnBookClickListener onBookClickListener) {
        bookList = new ArrayList<>();
        this.onBookClickListener = onBookClickListener;
    }

    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        Book book = bookList.get(position);
        if (book == null) return;

        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvCategory.setText(book.getCategory());
        holder.ivBookCover.setImageBitmap(ImageUtil.convertFrom64base(book.getCoverUrl()));


        /*
        // Set initials
        String initials = "";
        if (book.getTitle() != null && !book.getTitle().isEmpty()) {
            initials += book.getFirstName().charAt(0);
        }
        if (book.getLastName() != null && !book.getLastName().isEmpty()) {
            initials += book.getLastName().charAt(0);
        }
        holder.tvInitials.setText(initials.toUpperCase());
        */


        // Show admin chip if book is admin
//        if (book.isAvailable()) {
//            holder.chipRole.setVisibility(View.VISIBLE);
//            holder.chipRole.setText("Admin");
//        } else {
//            holder.chipRole.setVisibility(View.GONE);
//        }

        holder.itemView.setOnClickListener(v -> {
            if (onBookClickListener != null) {
                onBookClickListener.onBookClick(book);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (onBookClickListener != null) {
                onBookClickListener.onLongBookClick(book);
            }
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void setBookList(List<Book> books) {
        bookList.clear();
        bookList.addAll(books);
        notifyDataSetChanged();
    }

    public void addBook(Book book) {
        bookList.add(book);
        notifyItemInserted(bookList.size() - 1);
    }

    public void updateBook(Book book) {
        int index = bookList.indexOf(book);
        if (index == -1) return;
        bookList.set(index, book);
        notifyItemChanged(index);
    }

    public void removeBook(Book book) {
        int index = bookList.indexOf(book);
        if (index == -1) return;
        bookList.remove(index);
        notifyItemRemoved(index);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory, tvTitle, tvAuthor, tvCopies;

        ImageView ivBookCover;
       // Chip chipRole;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_book_title);
            tvAuthor = itemView.findViewById(R.id.tv_item_book_author);
            tvCopies = itemView.findViewById(R.id.tv_item_book_copies);
            tvCategory = itemView.findViewById(R.id.tv_item_book_genre);
            ivBookCover = itemView.findViewById(R.id.img_book_cover);
        }
    }
}
