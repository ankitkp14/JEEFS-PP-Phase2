package com.cg.dao;

import com.cg.bean.Account;
import com.cg.bean.Transactions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class LoanDAOImpl implements ILoanDAO {

	//creating entity objects 
	Account account=new Account();
	Transactions transaction=new Transactions();
	
	//for date and time
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
	DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("hh:mm");
	
	//object to persist the data in database
	private EntityManager entityManager;
	
	public LoanDAOImpl() {
		entityManager = JPAUtil.getEntityManager();
	}
	
	//getting account details
	public Account getAccount(String accountNum) {
		account=entityManager.find(Account.class, accountNum);
		return account;
	}
	
	// apply for loan
	@Override
	public boolean applyLoan(String accountNum,double loanAmount,int time) {
		account=entityManager.find(Account.class, accountNum);
		
		//if loan already applied
		if(account==null || account.getLoanAmount()!=0 || account.getLoanBal()!=0) {
			return false;
		}
		
		double RATE=10;
		double emi=Math.round((loanAmount * Math.pow((1+RATE/100),(double)time/12))/time);
		account.setLoanBal(emi*time);
		account.setLoanAmount(loanAmount);
		account.setDuration(time);
		account.setEmi(emi);
		transaction.setStatement(formatter.format(LocalDate.now())+", "+formatter1.format(LocalTime.now())+" * Loan applied for the amount :"+loanAmount+" for "+time+" months.");
		account.addTransaction(transaction);
		entityManager.persist(account);
		return true;
	}

	// show balance of user
	@Override
	public Account showBal(String accountNum) {
		account=entityManager.find(Account.class, accountNum);
		return account;
	}

	@Override
	public boolean payEmi(String accountNum) {
		
		account=entityManager.find(Account.class, accountNum);
		
		//if no loan exists
		if(account==null || account.getEmi()<=0) {
			return false;
		}
		
		double newBal=account.getLoanBal()-account.getEmi();
		account.setLoanBal(newBal);
		transaction.setStatement(formatter.format(LocalDate.now())+", "+formatter1.format(LocalTime.now())+" * Paid emi amount :"+account.getEmi()+". Updated loan balance is Rs. "+newBal+".");
		account.addTransaction(transaction);
		entityManager.persist(account);
		return true;
	}

	//foreclosure of loan
	@Override
	public boolean foreClose(String accountNum) {
		account=entityManager.find(Account.class,accountNum);
		
		//if no loan exists
		if(account==null || account.getLoanAmount()==0) {
			return false;
		}
		
		account.setLoanBal(0);
		account.setEmi(0);
		account.setDuration(0);
		account.setLoanAmount(0);
		transaction.setStatement(formatter.format(LocalDate.now())+", "+formatter1.format(LocalTime.now())+" * Loan foreclosure initiated..Successfully paid whole amount.");
		account.addTransaction(transaction);
		entityManager.persist(account);
		return true;
	}

	//to calculate emi
	@Override
	public double calEmi(double amount,double duration) {
		double RATE=10;
		double emi=Math.round((amount * Math.pow((1+RATE/100),duration/12))/duration);
		return emi;
	}

	//printing transactions of user
	@Override
	public List<Transactions> printTransactions(String accountNum) {
		String qStr = "SELECT transaction FROM Transactions transaction WHERE accountNum LIKE :paccount order by transaction.transactionId";
		TypedQuery<Transactions> query = entityManager.createQuery(qStr, Transactions.class);
		query.setParameter("paccount", "%"+accountNum+"%");
		return query.getResultList();
	}
	
	//creating a new account
	@Override
	public boolean createAccount(String accountNum, String password, String name, String gender) {
		
		if(entityManager.find(Account.class, accountNum)!=null) {
			return false;
		}
		account=new Account(accountNum,password,name,gender);
		entityManager.persist(account);
		return true;
	}
	
	// login to existing account
	@Override
	public boolean logIn(String accountNum, String password) {
		account=entityManager.find(Account.class, accountNum);
		if(account!=null && account.getAccountNum().equals(accountNum) && account.getPassword().equals(password)){
			return true;
		}
		return false;
	}
	
	// transaction for database
	@Override
	public void beginTransaction() {
		entityManager.getTransaction().begin();
	}

	@Override
	public void commitTransaction() {
		entityManager.getTransaction().commit();
	}
}
