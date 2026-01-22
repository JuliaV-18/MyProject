package com.lian.myproject.model;

import java.util.Date;

public class Book {
    private String id;
    protected String title;
    protected String author;
    protected boolean isAvailable;
    protected int copiesAvailable;
    protected int copiesTotal;
    protected String category;
    protected String coverUrl;
    protected Date added;
    protected String description;

    public Book(String id, String title, String author, boolean isAvailable, int copiesAvailable, int copiesTotal, String category, String coverUrl, Date added, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
        this.copiesAvailable = copiesAvailable;
        this.copiesTotal = copiesTotal;
        this.category = category;
        this.coverUrl = coverUrl;
        this.added = added;
        this.description = description;
    }

    public Book() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

    public int getCopiesTotal() {
        return copiesTotal;
    }

    public void setCopiesTotal(int copiesTotal) {
        this.copiesTotal = copiesTotal;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isAvailable=" + isAvailable +
                ", copiesAvailable=" + copiesAvailable +
                ", copiesTotal=" + copiesTotal +
                ", category='" + category + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", added=" + added +
                ", description='" + description + '\'' +
                '}';
    }
}
