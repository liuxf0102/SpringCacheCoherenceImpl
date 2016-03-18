package org.spring.cache.defaultcache;

public class MyAccountService { 
   private MyCacheManager<Account> cacheManager; 
  
   public MyAccountService() { 
     cacheManager = new MyCacheManager<Account>();
   } 
  
   public Account getAccountByName(String acctName) { 
     Account result = cacheManager.getValue(acctName);
     if(result!=null) { 
       System.out.println("get from cache..."+acctName); 
       return result;
     } 
     result = getFromDB(acctName);
     if(result!=null) {
       cacheManager.addOrUpdateCache(acctName, result); 
     } 
     return result; 
   } 
  
   public void reload() { 
     cacheManager.evictCache(); 
   } 
  
   private Account getFromDB(String acctName) { 
     System.out.println("real querying db..."+acctName); 
     return new Account(acctName); 
   } 
 }