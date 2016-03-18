/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.spring.cache.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.AbstractCacheManager;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.CacheService;
import com.tangosol.net.Cluster;

/**
 * {@link CacheManager} implementation that lazily builds {@link MyMapCache}
 * instances for each {@link #getCache} request. Also supports a 'static' mode where
 * the set of cache names is pre-defined through {@link #setCacheNames}, with no
 * dynamic creation of further cache regions at runtime.
 *
 * <p>Note: This is by no means a sophisticated CacheManager; it comes with no
 * cache configuration options. However, it may be useful for testing or simple
 * caching scenarios. For advanced local caching needs, consider
 * {@link org.springframework.cache.guava.GuavaCacheManager} or
 * {@link org.springframework.cache.ehcache.EhCacheCacheManager}.
 *
 * @author Juergen Hoeller
 * @since 3.1
 * @see MyMapCache
 */
public class CoherenceCacheManager extends AbstractCacheManager {

	@SuppressWarnings("unused")
	private boolean dynamic =  true;
	private Collection<? extends Cache> caches;
	private  Logger logger = LogManager.getLogger(this.getClass());


	/**
	 * Construct a CoherenceCacheManager instances as they are being requested.
	 */
	public CoherenceCacheManager() {
	}

	/**
	 * Construct a static ConcurrentMapCacheManager,
	 * managing caches for the specified cache names only.
	 */
	public CoherenceCacheManager(String... cacheNames) {
		setCacheNames(Arrays.asList(cacheNames));
	}


	/**
	 * Specify the set of cache names for this CacheManager's 'static' mode.
	 * <p>The number of caches and their names will be fixed after a call to this method,
	 * with no creation of further cache regions at runtime.
	 * <p>Calling this with a {@code null} collection argument resets the
	 * mode to 'dynamic', allowing for further creation of caches again.
	 */
	public void setCacheNames(Collection<String> cacheNames) {
		if (cacheNames != null) {
			for (String name : cacheNames) {
				createCoherenceCacheWrapper(name);
			}
			this.dynamic = false;
		}
		else {
			this.dynamic = true;
		}
	}

	/**
	 * Specify whether to accept and convert {@code null} values for all caches
	 * in this cache manager.
	 * <p>Default is "true", despite ConcurrentHashMap itself not supporting {@code null}
	 * values. An internal holder object will be used to store user-level {@code null}s.
	 * <p>Note: A change of the null-value setting will reset all existing caches,
	 * if any, to reconfigure them with the new null-value requirement.
	 */
	public void setAllowNullValues(boolean allowNullValues) {
//		Assert.isTrue(allowNullValues, "Can't set false");
	}

	/**
	 * Return whether this cache manager accepts and converts {@code null} values
	 * for all of its caches.
	 */
	public boolean isAllowNullValues() {
		return true;
	}


	
	@Override
	public Collection<String> getCacheNames() {
        List<String> listCaches = new ArrayList<String>();
        Cluster cluster = CacheFactory.getCluster();
        
        @SuppressWarnings("unchecked")
		Enumeration<String> serviceNames = cluster.getServiceNames();
 
        while (serviceNames.hasMoreElements()) {
            String serviceName = serviceNames.nextElement();
            if (cluster.getService(serviceName) instanceof CacheService) {
                CacheService serviceCache = (CacheService) cluster.getService(serviceName);
                @SuppressWarnings("unchecked")
                Enumeration<String> cacheNames = serviceCache.getCacheNames();
                while (cacheNames.hasMoreElements()) {
                    String cacheName = (String) cacheNames.nextElement();
                    listCaches.add(cacheName);
                }
            }
        }

		return listCaches;
	}

	@Override
	public Cache getCache(String name) {
		return createCoherenceCacheWrapper(name);
	}

	/**
	 * Create a new ConcurrentMapCache instance for the specified cache name.
	 * @param name the name of the cache
	 * @return the ConcurrentMapCache (or a decorator thereof)
	 */
	protected Cache createCoherenceCacheWrapper(String name) {
		return new CoherenceCacheWrapper(name);
	}

	/**
	 * Specify the collection of Cache instances to use for this CacheManager.
	 */
	public void setCaches(Collection<? extends Cache> caches) {
		this.caches = caches;
	}

	@Override
	protected Collection<? extends Cache> loadCaches() {
		logger.debug("Here");
		
		return this.caches;
	}

}
