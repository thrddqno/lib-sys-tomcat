package net.codejava.registration.model;

import java.io.Serializable;
import java.sql.Date;

public class BookIssuance implements Serializable {
	private static final long serialVersionUID = 1L;
	private int issuanceId;
	private int bookId;
	private int borrowerId;
	private Date issued_date;
	private Date due_date;
	private Date returned_date;
	
	public int getIssuanceId() {
		return issuanceId;
	}
	public void setIssuanceId(int issuanceId) {
		this.issuanceId = issuanceId;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getBorrowerId() {
		return borrowerId;
	}
	public void setBorrowerId(int borrowerId) {
		this.borrowerId = borrowerId;
	}
	public Date getIssued_date() {
		return issued_date;
	}
	public void setIssued_date(Date issued_date) {
		this.issued_date = issued_date;
	}
	public Date getDue_date() {
		return due_date;
	}
	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}
	public Date getReturned_date() {
		return returned_date;
	}
	public void setReturned_date(Date returned_date) {
		this.returned_date = returned_date;
	}
}
