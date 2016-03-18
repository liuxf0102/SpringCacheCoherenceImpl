 package org.spring.cache.defaultcache; 

 import org.springframework.context.ApplicationContext; 
 import org.springframework.context.support.ClassPathXmlApplicationContext; 

 public class Tester { 
   public static void main(String[] args) { 
     ApplicationContext context = new ClassPathXmlApplicationContext( 
        "spring-cache.xml");
    
     AccountService s = (AccountService) context.getBean("accountServiceBean"); 

	 System.out.println();
     System.out.print("first query..."); 
     s.getAccountByName("somebody"); 

     System.out.println("second query..."); 
     s.getAccountByName("somebody"); 
	 
     System.out.println("start testing clear cache...");
     Account account1 = s.getAccountByName("somebody1"); 
     Account account2 = s.getAccountByName("somebody2"); 

	 account1.setId(1212);
     s.updateAccount(account1);

     s.getAccountByName("somebody1");    
	 s.getAccountByName("somebody2");    
	 s.getAccountByName("somebody1");
     s.reload(); 
     s.getAccountByName("somebody1");    
	 s.getAccountByName("somebody2");    
	 s.getAccountByName("somebody1");    
	 s.getAccountByName("somebody2");
   } 
 }