package com.cg.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.cg.service.LoanServiceImpl;

public class LoanJUnitTest {

	static LoanServiceImpl service = null;
	
	@BeforeClass
	public static void checkBefore() {
		service=new LoanServiceImpl();
	}
	 
	@Before
	public void BeforeCheck() {
		System.out.println("\nInitializing JUnit Test..");
	}
	
	@Test
	public void test() {
		
		//login test
		System.out.println("\nLogin Test..");
		
	    assertEquals(true,service.logIn("7939100052600", "kush@12"));
	    assertEquals(false,service.logIn("7939100052998", "kush@12"));
	    assertEquals(false,service.logIn("7939100052600", "kush12"));
	    
	    //signup
	    System.out.println("\nSignup Test..");
	    assertEquals(false,service.createAccount("12345678901", "kush@12","Ankit","M"));
	    assertEquals(false,service.createAccount("7939100052607", "kush@12","Ankit","M"));
	    
	    
	    //password
	    System.out.println("\nPassword Validation");
	    assertEquals(true, service.checkPass("Kush@2"));
	    assertEquals(false, service.checkPass("Kush@"));
	    
	    //name
	    System.out.println("\nName Validation");
	    assertEquals(true, service.checkName("Ankit"));
	    assertEquals(false, service.checkName("Anki12"));
	    
	    //account num
	    System.out.println("\nAccount Num Validation");
	    assertEquals(false, service.checkUserId("Kush@2"));
	    assertEquals(true, service.checkUserId("12345678901"));
	    
	    //gender
	    System.out.println("\nGender Validation");
	    assertEquals(true, service.checkGender("M"));
	    assertEquals(false, service.checkGender("1213"));
	    
	    //payemi
	    System.out.println("\nPay EMI functionality...");
	    assertEquals(false, service.payEmi("1213"));
	    //assertEquals(true, service.payEmi("7939100052600"));
	    
	    //foreclose
	    System.out.println("\nForeclose functionality...");
	    assertEquals(false, service.foreClose("1213"));
	    //assertEquals(true, service.foreClose("7939100052600"));
	}
	
	@After
	public void CheckedAfter() {
		System.out.println("\nVerified all tests..");
	}
	
	@AfterClass
	public static void AfterCheck() {
		System.out.println("\nFinished JUnit Testing..");
	}
}