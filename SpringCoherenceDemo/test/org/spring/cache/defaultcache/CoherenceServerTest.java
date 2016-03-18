package org.spring.cache.defaultcache;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CoherenceServerTest {
	public class AccountServiceTest {

		private ApplicationContext context;

		@Test
		public void testStartCoherenceService() throws Exception {
			context = new ClassPathXmlApplicationContext("spring-cache-coherence.xml");
			
			Thread.sleep(36000);

		}
	}
}
