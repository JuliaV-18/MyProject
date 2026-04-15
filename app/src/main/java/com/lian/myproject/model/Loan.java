package com.lian.myproject.model;

import java.util.Calendar;
import java.util.Date;

public class Loan {

        private String id;
        private String bookId;


        private String bookName;

        private String userId;
        private Date borrowDate;
        private Date returnDate;
        private boolean returned;

        // בנאי


    public Loan(String id, String bookId, String bookName, String userId, Date borrowDate, Date returnDate, boolean returned) {
        this.id = id;
        this.bookId = bookId;
        this.bookName = bookName;
        this.userId = userId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.returned = returned;
    }

    public Loan(String id, String bookId, String bookName, String userId) {
        this.id = id;
        this.bookId = bookId;
        this.bookName = bookName;
        this.userId = userId;
        Calendar cal = Calendar.getInstance();;

        this.borrowDate = cal.getTime();


        setReturnDate(borrowDate);
        this.returned = false;
    }


    public Loan() {
    }

    // Getters
        public String getId() {
            return id;
        }


    public void setId(String id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public Date getBorrowDate() {
            return borrowDate;
        }

        public Date getReturnDate() {
            return returnDate;
        }

        public boolean isReturned() {
            return returned;
        }

        // החזרת ספר
        public void returnBook(Date returnDate) {
            this.returnDate = returnDate;
            this.returned = true;
        }

        // בדיקה אם מאוחר
        public boolean isOverdue() {
            if (returned) return false;

            Date today = new Date();
            long diff = today.getTime() - borrowDate.getTime();

            long days = diff / (1000 * 60 * 60 * 24);

            return days > 14; // לדוגמה: השאלה ל-14 ימים
        }


    public void setReturnDateAutomatically() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(borrowDate);
        cal.add(Calendar.DAY_OF_MONTH, 14);
        this.returnDate = cal.getTime();
    }


    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", userId='" + userId + '\'' +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", returned=" + returned +
                '}';
    }
}




