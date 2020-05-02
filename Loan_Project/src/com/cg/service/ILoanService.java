package com.cg.service;

import java.util.List;

import com.cg.bean.Account;
import com.cg.bean.Transactions;

public interface ILoanService {
	
	boolean createAccount(String accountNum,String password, String name, String gender);
	boolean logIn(String accountNum,String password);
	
	Account getAccount(String accountNum); //getting account details
	Account showBal(String accountNum);
	
	boolean applyLoan(String accountNum,double loanAmount,int time);
	boolean payEmi(String accountNum);
	boolean foreClose(String accountNum);
	double calEmi(double amount,int month);
	List<Transactions> printTransactions(String accountNum);
}