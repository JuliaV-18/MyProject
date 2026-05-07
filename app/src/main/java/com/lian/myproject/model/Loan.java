package com.lian.myproject.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Loan {

    protected String id;
    protected String bookId;
    protected String bookName;
    protected String userId;
    protected Date borrowDate;
    protected Date returnDate;
    protected boolean returned;
    protected boolean isOverdue=false;

    protected  String message;

    public Loan(String id, String bookId, String bookName, String userId, Date borrowDate, Date returnDate, boolean returned, boolean isOverdue, String message) {
        this.id = id;
        this.bookId = bookId;
        this.bookName = bookName;
        this.userId = userId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.returned = returned;
        this.isOverdue = isOverdue;
        this.message = message;
    }

    public Loan(String id, String bookId, String bookName, String userId, Date borrowDate, Date returnDate, boolean returned) {
        this.id = id;
        this.bookId = bookId;
        this.bookName = bookName;
        this.userId = userId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.returned = returned;

    }

    public Loan(String id, String bookId, String bookName, String userId, Date borrowDate, Date returnDate, boolean returned, String message) {
        this.id = id;
        this.bookId = bookId;
        this.bookName = bookName;
        this.userId = userId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.returned = returned;
        this.message = message;
    }

    public Loan(String id, String bookId, String bookName, String userId) {
        this.id = id;
        this.bookId = bookId;
        this.bookName = bookName;
        this.userId = userId;

        Calendar cal = Calendar.getInstance();
        this.borrowDate = cal.getTime();
        this.isOverdue=false;

       // setReturnDateAutomatically();
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

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public boolean isReturned() {
            return returned;
        }

        // החזרת ספר
        public void returnBook() {

            this.returned = true;
        }

        // בדיקה אם מאוחר
        public boolean isOverdue2() {

            Date currentDate = new Date();
            if (returned) return false;
            if (this.getReturnDate().before(currentDate)) { // late books
                return true;
            }
            else return false;

//                Date today = new Date();
//                long diff = today.getTime() - borrowDate.getTime();
//
//                long days = diff / (1000 * 60 * 60 * 24);
//
//                return days > 14; // לדוגמה: השאלה ל-14 ימים}

        }

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }

    public void setReturnDateAutomatically() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(borrowDate);
        cal.add(Calendar.DAY_OF_MONTH, 14);


        this.returnDate = cal.getTime();
    }



//    public String getBorrowDateString() {
//        if (this.borrowDate == null) return "";
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        return sdf.format(borrowDate);
//    }
//    public String getReturnDateString() {
//        if (this.returnDate == null) return "";
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        return sdf.format(returnDate);
//    }
//


    public String convertDateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(date);


    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id='" + id + '\'' +
                ", bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", userId='" + userId + '\'' +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", returned=" + returned +
                ", message='" + message + '\'' +
                '}';
    }
}




