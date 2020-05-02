package com.cg.service;

import java.util.List;
import com.cg.bean.Account;
import com.cg.bean.Transactions;
import com.cg.dao.LoanDAOImpl;

public class LoanServiceImpl implements ILoanService {
	
	//to store account details when fetched
	Account account=new Account();
	
	//dao layer access
	LoanDAOImpl dao;
	
	public LoanServiceImpl() {
		dao=new LoanDAOImpl();
	}
	
	@Override
	public Account getAccount(String accountNum) {
		account=dao.getAccount(accountNum);
		return account;
	}
	
	@Override
	public boolean applyLoan(String accountNum,double loanAmount,int time){
		
		dao.beginTransaction();
		boolean result=dao.applyLoan(accountNum, loanAmount, time);
		dao.commitTransaction();
		return result;
	}

	@Override
	public Account showBal(String accountNum) {
		account=dao.showBal(accountNum);
		return account;
	}

	@Override
	public boolean payEmi(String accountNum) {
		
		dao.beginTransaction();
		boolean result=dao.payEmi(accountNum);
		dao.commitTransaction();
		return result;
	}

	@Override
	public boolean foreClose(String accountNum) {
		
		dao.beginTransaction();
		boolean result=dao.foreClose(accountNum);
		dao.commitTransaction();
		return result;
	}

	@Override
	public double calEmi(double amount,int month) {
		double emi=dao.calEmi(amount,month);
		return emi;
	}

	@Override
	public List<Transactions> printTransactions(String accountNum) {
		List<Transactions> transaction=dao.printTransactions(accountNum);
		return transaction;
	}
	
	
	//creating a new account
	@Override
	public boolean createAccount(String accountNum, String password, String name, String gender) {
		dao.beginTransaction();
		boolean result=dao.createAccount(accountNum, password, name, gender);
		dao.commitTransaction();
		return result;
	}

	
	// login to existing account
	@Override
	public boolean logIn(String accountNum, String password) {
		return dao.logIn(accountNum, password);
	}	
	
	//Validations
	
	//for accountNum
	public boolean checkUserId(String accountNum) {
		String checkAccNum="([0-9]+)";

		if(accountNum.matches(checkAccNum) && accountNum.length()>=11) return true;
		return false;
	}
	
	//for password
	public boolean checkPass(String password) {
		String checkPassword="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
		
		if(password.matches(checkPassword)) return true;
		return false;
	}
	
	//for name
	public boolean checkName(String name) {
		String checkNam="([A-Za-z_ ]+)";
		
		if(name.matches(checkNam)) return true;
		return false;
	}
	
	//for gender
	public boolean checkGender(String gender) {
		String checkGend="([A-Za-z_])";
		
		if(gender.matches(checkGend)) return true;
		return false;
	}
}