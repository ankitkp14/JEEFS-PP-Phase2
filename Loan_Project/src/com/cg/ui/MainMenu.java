package com.cg.ui;

import java.util.Scanner;
import com.cg.service.LoanServiceImpl;

class Delay extends Thread{
	public void run() {
		try {
			sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

public class MainMenu {

	public void startApp() {
		
			//for user inputs
			Scanner scan=new Scanner(System.in);
			Scanner scan1=new Scanner(System.in);
			
			System.out.print("\n\n	*********** WELCOME to ABC BANK *************\n\n");
			System.out.print("\n		1.Log In\n		2.Create Account\n		3.Exit\n\n		Enter Choice: ");
			String choice=scan.next();
			
			//to verify login credentials and checking existing accounts making service object
			LoanServiceImpl service=new LoanServiceImpl();
			
			if(choice.equals("1")) {

				System.out.print("\n\n		*****Log In*****");
				
				System.out.print("\n\nEnter Account Number:");
				String accountNum1=scan.next();
				
				System.out.print("\nEnter Password:");
				String password1=scan.next();
				
				System.out.println("\nPlease Wait..");
				new Delay().run();
				
				if(service.logIn(accountNum1, password1)) {
					System.out.println("\nSuccessfully Logged In");
					//redirecting to modules
					LoanMenu.loginMenu(accountNum1);
				}
				
				else {
					System.out.println("\nIncorrect Id or Password !!");
					new MainMenu().startApp();
				}
			}
			
			else if(choice.equals("2")) {
				
				System.out.println("\n\n		*****Sign Up*****");
				System.out.print("\n\nPlease Verify Your Identity..");
				
				System.out.print("\n\nEnter Account Number: ");
				String accountNum=scan1.nextLine();
				
				//account num validation
				while(!service.checkUserId(accountNum)) {
						System.out.println("\nAccount Num should be between 11 to 16 digits and only numbers allowed...\nRe-Enter Account Num: ");
						accountNum=scan1.nextLine();
				}
					
				new Delay().run();
				
				System.out.println("\n\nThanks..Your Account number has Been Verified !!\n\n");
				
				System.out.print("\nEnter Name: ");
				String name=scan1.nextLine();
				
				//name validation
				while(!service.checkName(name)) {
					System.out.println("\nName can contain alphabets only...\nRe-Enter Name: ");
					name=scan1.nextLine();
				}
				
				System.out.print("\nEnter Gender(M/F/O): ");
				String gender=scan1.nextLine();
				
				//gender validation
				while(!service.checkGender(gender)) {
					System.out.println("\nEnter single character as directed...\nRe-Enter Gender: ");
					name=scan1.nextLine();
				}

				System.out.println("\nPlease create your password..\nUse at least one UpperCase,Lower Case,Number and Special Character..\nMin Length 6 Characters and Max Length 20 Characters..\n");
				String password=scan1.nextLine();
				
				//password validation
				while(!service.checkPass(password)) {
					System.out.print("\nPassword doesn't match the required criteria..Try Again: ");
					password=scan1.nextLine();
				}
				
				
				//creating account
				if(service.createAccount(accountNum,password,name,gender)){
					System.out.println("\n\nYour Account has been created Successfully !!");
				}
				else {
					System.out.println("\nAccount Number already registered...Try a different account number");
				}
				new MainMenu().startApp();
			}
			
			else if(choice.equals("3")) {
				System.out.println("\n\n	Thanks for Using Our Services !!\n");
				System.exit(0);
			}
			else {
				System.out.println("\n	Wrong Choice..Try Again!!\n\n");
				new MainMenu().startApp();
			}
		scan.close();
		scan1.close();
	}
	
	public static void main(String[] args) {
		new MainMenu().startApp();
	}
}