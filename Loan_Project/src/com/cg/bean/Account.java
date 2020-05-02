package com.cg.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="user_data")
public class Account implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//primary key
	@Id
	private String accountNum;
	private String password;
	private String name;
	private String gender;
	
	//loan data
	private double loanAmount;
	private double loanBal;
	private double emi;
	private int duration;
	
	//relation with transaction table
	@OneToMany(mappedBy="account",cascade=CascadeType.ALL)
	private List<Transactions> transaction=new ArrayList<>();
	
	public Account() {
		
	}
	
	public Account(String accountNum, String password, String name, String gender) {
		
		this.accountNum = accountNum;
		this.password = password;
		this.name = name;
		this.gender = gender;
	}
	
	//getters and setters

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public double getLoanBal() {
		return loanBal;
	}
	public void setLoanBal(double loanBal) {
		this.loanBal = loanBal;
	}
	public double getEmi() {
		return emi;
	}
	public void setEmi(double emi) {
		this.emi = emi;
	}

	//transactions table connection
	public List<Transactions> getTransaction() {
		return transaction;
	}

	public void setTransaction(List<Transactions> transaction) {
		this.transaction = transaction;
	}
	public void addTransaction(Transactions transaction)
	{
		transaction.setAccount(this);
		this.getTransaction().add(transaction);
	}
}
