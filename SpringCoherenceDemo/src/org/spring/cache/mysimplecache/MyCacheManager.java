package org.spring.cache.mysimplecache;

import java.util.Collection;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager; 

public class MyCacheManager extends AbstractCacheManager {



	private Collection<? extends Cache> caches;


	/**
	 * Specify the collection of Cache instances to use for this CacheManager.
	 */
	public void setCaches(Collection<? extends Cache> caches) {
		this.caches = caches;
	}

	@Override
	protected Collection<? extends Cache> loadCaches() {
		System.out.println("Here");
		
		return this.caches;
	}

}
