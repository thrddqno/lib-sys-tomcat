package net.codejava.registration.model;

import java.io.Serializable;

public class Books implements Serializable{
	private static final long serialVersionUID = 1L;
	private int bookId;
	private String title;
	private String author;
	private String description;
	private String categories;
	private boolean isAvailable;
	
	public Books(int bookId, String title, String author, String description, String categories, boolean isAvailable){
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.description = description;
		this.categories = categories;
		this.isAvailable = isAvailable;
	}
	
	public Books(String title, String author, String description, String categories, boolean isAvailable) {
		super();
		this.title = title;
		this.author = author;
		this.description = description;
		this.categories = categories;
		this.isAvailable = isAvailable;
	}

	
	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategories() {
		return categories;
	}
	public void setCategories(String categories) {
		this.categories = categories;
	}
	public Boolean getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
}
