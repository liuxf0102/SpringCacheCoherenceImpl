package org.spring.cache.defaultcache;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AccountServiceTest {

	private ApplicationContext context;

	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("spring-cache-coherence.xml");

	}

	@Test
	public void testGetAccountByName() {
		AccountService s = (AccountService) context.getBean("accountServiceBean");

		s.getAccountByName("some");
		s.getAccountByName("some");
		s.getAccountByName("some");

		s.getAccountByName("somebody");
		s.getAccountByName("somebody");
		s.getAccountByName("somebody");

	}

	@Test
	public void testGetAccount() {
		AccountService s = (AccountService) context.getBean("accountServiceBean");

		s.getAccount("somebody", "123456", true);// Ӧ�ò�ѯ��ݿ�
		s.getAccount("somebody", "123456", true);// Ӧ���߻���
		s.getAccount("somebody", "123456", false);// Ӧ���߻���
		s.getAccount("somebody", "654321", true);// Ӧ�ò�ѯ��ݿ�
		s.getAccount("somebody", "654321", true);// Ӧ���߻���

	}

}
