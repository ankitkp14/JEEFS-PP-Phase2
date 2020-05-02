package com.cg.dao;

import java.util.List;

import com.cg.bean.Account;
import com.cg.bean.Transactions;

public interface ILoanDAO {
	
	boolean createAccount(String accountNum,String password, String name, String gender);
	boolean logIn(String accountNum,String password);
	
	Account getAccount(String accountNum);
	Account showBal(String accountNum);
	
	boolean applyLoan(String accountNum,double loanAmount,int time);
	boolean payEmi(String accountNum);
	boolean foreClose(String accountNum);
	double calEmi(double amount,double duration);
	List<Transactions> printTransactions(String accountNum);
	
	void beginTransaction();
	void commitTransaction();
}
