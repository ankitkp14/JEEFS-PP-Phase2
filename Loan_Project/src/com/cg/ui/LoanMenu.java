package com.cg.ui;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.cg.bean.Account;
import com.cg.bean.Transactions;
import com.cg.service.LoanServiceImpl;

public class LoanMenu {
	public static void loginMenu(String accountNum) {
		
		//to accept user data
		Scanner scan=new Scanner(System.in);
		Scanner scan1=new Scanner(System.in);
		
		//service class object
		LoanServiceImpl module=new LoanServiceImpl();
		
		//bean object
		Account account=new Account();
		
		System.out.println("\n\n*******Our Services********");
		System.out.println("\n\n1.Apply Loan		2.ForeClose\n3.Show Balance		4.Print Transactions\n5.Pay EMI		6.Calculate EMI\n7.Logout\n\nEnter Choice:");
		
		//exception handling for wrong choices
		int choice=0;
		
		try {
		choice=scan1.nextInt();
		}
		catch(Exception e) {
			System.out.println("\nWrong choice !!..Try again");
			loginMenu(accountNum);
		}
		
		switch(choice) {
		case 1:
			
			account=module.getAccount(accountNum);
			if(account.getLoanBal()>0) {
				System.out.println("Can't apply for another loan...already taken !!");
				loginMenu(accountNum);
			}
			else {
				System.out.println("Min loan applied should be Rs 5000...");
				System.out.print("\nEnter Amount you wish to apply for: ");
				double loanAmount=0;
				
				
				//exception
				try{
					loanAmount=scan1.nextDouble();
				}
				catch(Exception e) {
					System.out.println("\nInvalid Input !!..Try again");
					loginMenu(accountNum);
				}
				
				//invalid loan amount
				if(loanAmount<5000){
					System.out.println("Invalid Amount applied...");
					loginMenu(accountNum);
				}
				
				System.out.println("Enter number of months for repayment\n(Choose from the options)\n6 Month\n12 Month\n24 Month\n36 Month\n60 Month\n120 Month");
				int time=0;
				
				//exception
				try{
					time=scan1.nextInt();
				}
				catch(Exception e) {
					System.out.println("\nInvalid Input !!..Try again");
					loginMenu(accountNum);
				}
				
				//invalid input
				while(!(time==6 || time==12 || time==24 || time==36 || time==60 || time==120)) {
					System.out.println("\nWrong duration..Try again");
					time=scan1.nextInt();
				}
				
				//application of loan
				module.applyLoan(accountNum,loanAmount,time);
				System.out.println("\nYour loan has been applied !!");
				loginMenu(accountNum);
			}
			break;
			
		case 2:
			account=module.getAccount(accountNum);
			//if no loan is already applied
			if(account.getLoanAmount()==0) {
				System.out.println("You have not taken any loans...");
				loginMenu(accountNum);
			}
			
			//calculating amount for foreclosure
			
			double balance=account.getLoanBal();
			double foreCloseAmount=balance+balance*0.04;
			System.out.print("\nLoan Balance: "+balance);
			System.out.println("\tForeclosure Amount: "+foreCloseAmount);
			
			//confirmation
			System.out.print("\nPress Y to confirm or enter something else to cancel...");
			String option=scan.nextLine();
			
			if(option.equals("Y") || option.equals("y")) {
				module.foreClose(accountNum);
				System.out.println("\nYour Loan is cleared successfully!!");
			}
			
			else {
				System.out.println("\nForeClosure cancelled...");
				loginMenu(accountNum);
			}
			
			//redirecting
			System.out.print("\n\nPress any key to continue:");
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			loginMenu(accountNum);
			break;
		case 3:
			account=module.showBal(accountNum);
			
			//printing account details and balances
			System.out.print("\nName :"+account.getName());
			System.out.print("\tAccount Number :"+account.getAccountNum());
			System.out.println("\tGender :"+account.getGender());
			
			System.out.print("Applied Amount: "+account.getLoanAmount());
			System.out.print("\tCurrent Loan Balance: "+account.getLoanBal());
			System.out.print("\tCurrent EMI: "+account.getEmi());
			
			System.out.print("\n\nPress any key to continue:");
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			loginMenu(accountNum);
			break;
		case 4:
			//printing transaction statements
			System.out.println("\n\nLoan Statement\n");
			List<Transactions> transaction= module.printTransactions(accountNum);
			
			//if no transactions are done so far
			if(transaction.isEmpty()) {
				System.out.println("No Statement Available...");
			}
			
			//printing
			for(Transactions t:transaction) {
				System.out.println(t.getStatement());
			}
			
			System.out.print("\nPress any key to continue:");
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			loginMenu(accountNum);
			break;
		case 5:
			account=module.getAccount(accountNum);
			//if no loans taken
			if(account.getLoanAmount()==0) {
				System.out.println("You have not taken any loans...");
				loginMenu(accountNum);
			}
			
			//showing amount for payment
			
			System.out.print("Applied Amount: "+account.getLoanAmount());
			System.out.print("\tCurrent Loan Balance: "+account.getLoanBal());
			System.out.print("\tCurrent EMI: "+account.getEmi());
			
			//confirmation
			System.out.println("\nEnter any key to pay emi...");
			
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			module.payEmi(accountNum);
			System.out.println("\nYour EMI is paid successfully!!");
			
			//redirecting
			System.out.print("\n\nEnter any char to continue:");
			
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			loginMenu(accountNum);
			break;
		case 6:
			
			//emi calculator
			System.out.print("\nEnter the Amount of Loan:");
			double amount=0;
			
			//exception for input mismatch
			try{
				amount=scan1.nextDouble();
			}
			catch(Exception e) {
				System.out.println("\nInvalid Input !!..Try again");
				loginMenu(accountNum);
			}
			
			if(amount<5000) {
				System.out.println("\nThis amount is less than amount that can be applied...\nMin loan that can be applied is 5000...");
				loginMenu(accountNum);
			}
			
			double emi=module.calEmi(amount,6);
			System.out.println("\nAmount: "+amount+"      No. of Months: 6"+"       ROI: "+10+"%\nEMI :  "+emi);
			
			emi=module.calEmi(amount,12);
			System.out.println("\nAmount: "+amount+"      No. of Months: 12"+"       ROI: "+10+"%\nEMI :  "+emi);
			
			emi=module.calEmi(amount,24);
			System.out.println("\nAmount: "+amount+"      No. of Months: 24"+"       ROI: "+10+"%\nEMI :  "+emi);
			
			emi=module.calEmi(amount,36);
			System.out.println("\nAmount: "+amount+"      No. of Months: 36"+"       ROI: "+10+"%\nEMI :  "+emi);
			
			emi=module.calEmi(amount,60);
			System.out.println("\nAmount: "+amount+"      No. of Months: 60"+"       ROI: "+10+"%\nEMI :  "+emi);
			
			emi=module.calEmi(amount,120);
			System.out.println("\nAmount: "+amount+"      No. of Months: 120"+"       ROI: "+10+"%\nEMI :  "+emi);
			
			System.out.print("\nPress any key to continue:");
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			loginMenu(accountNum);
			break;
		case 7:
			new MainMenu().startApp();
			break;
		default:
			System.out.println("\nWrong Option..Try Again!!");
			loginMenu(accountNum);
			break;
		}
		scan.close();
		scan1.close();
	}
}