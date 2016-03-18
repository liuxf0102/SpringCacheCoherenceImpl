package org.spring.cache.defaultcache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public class AccountService {
	@Cacheable(value = "accountCache", condition = "#userName.length() <= 4") // ʹ����һ���������
																				// accountCache
	public Account getAccountByName(String userName) {
		System.out.println("getAccountByName..." + userName);
		return getFromDB(userName);
	}

	@Cacheable(value = "accountCache", key = "#userName.concat(#password)")
	public Account getAccount(String userName, String password, boolean sendLog) {
		return getFromDB(userName,password);

	}

	private Account getFromDB(String acctName) {
		System.out.println("getFromDB..." + acctName);
		return new Account(acctName);
	}
	private Account getFromDB(String acctName,String password) {
		System.out.println("getFromDB..." + acctName);
		return new Account(acctName,password);
	}

	@CacheEvict(value = "accountCache", key = "#account.getName()")
	public void updateAccount(Account account) {
		System.out.println("updateAccount..." + account.getName());
		updateDB(account);
		// evicAccount(account);
	}

	@CacheEvict(value = "accountCache", allEntries = true)
	public void reload() {
		System.out.println("reload...");
	}

	private void updateDB(Account account) {
		System.out.println("updateDB..." + account.getName());
	}
}