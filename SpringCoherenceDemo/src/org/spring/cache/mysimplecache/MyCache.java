package org.spring.cache.mysimplecache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.spring.cache.defaultcache.Account;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper; 

public class MyCache implements Cache { 
  private String name; 
  private ConcurrentMap<String,Account> store = new ConcurrentHashMap<String,Account>();; 
  
  public MyCache() { 
  } 
 
  public MyCache(String name) { 
    this.name = name; 
  } 
 
  @Override 
  public String getName() { 
    return name; 
  } 
 
  public void setName(String name) { 
    this.name = name; 
  } 

  @Override 
  public Object getNativeCache() { 
    return store; 
  } 

  @Override 
  public ValueWrapper get(Object key) { 
	  
	System.out.println("here");
    ValueWrapper result = null; 
    Account thevalue = store.get(key); 
    if(thevalue!=null) { 
      thevalue.setPassword("from mycache:"+name); 
      result = new SimpleValueWrapper(thevalue); 
    } 
    return result; 
  } 

  @Override 
  public void put(Object key, Object value) { 
    Account thevalue = (Account)value; 
    store.put((String)key, thevalue); 
  } 

  @Override 
  public void evict(Object key) { 
  } 

  @Override 
  public void clear() { 
  }

@Override
public <T> T get(Object arg0, Class<T> arg1) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public ValueWrapper putIfAbsent(Object arg0, Object arg1) {
	// TODO Auto-generated method stub
	return null;
} 
}