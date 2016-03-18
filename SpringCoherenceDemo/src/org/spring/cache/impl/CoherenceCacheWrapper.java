/*
 * Copyright 2002-2015 the original author or authors.
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

import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.util.Assert;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

/**
 * Simple {@link org.springframework.cache.Cache} implementation based on the
 * Oracle Coherence {@code com.tangosol.coherence} package.
 *
 * <p>
 * Useful for testing or simple caching scenarios, typically in combination with
 * {@link org.springframework.cache.support.SimpleCacheManager} or dynamically
 * through {@link CoherenceCacheManager}.
 *
 * @author LSHU
 * @since 3.1
 */
public class CoherenceCacheWrapper extends AbstractValueAdaptingCache {

	private final String name;

	private final NamedCache store;

	/**
	 * Create a new CoherenceCacheWrapper with the specified name.
	 * 
	 * @param name
	 *            the name of the cache
	 */
	public CoherenceCacheWrapper(String name) {
		this(name, CacheFactory.getCache(name), true);
	}

	/**
	 * Create a new CoherenceCacheWrapper with the specified name.
	 * 
	 * @param name
	 *            the name of the cache
	 * @param allowNullValues
	 *            whether to accept and convert {@code null} values for this
	 *            cache
	 */
	public CoherenceCacheWrapper(String name, boolean allowNullValues) {
		this(name, CacheFactory.getCache(name), allowNullValues);
	}

	/**
	 * Create a new CoherenceCacheWrapper with the specified name and the given
	 * internal {@link ConcurrentMap} to use.
	 * 
	 * @param name
	 *            the name of the cache
	 * @param store
	 *            the CoherenceCacheWrapper to use as an internal store
	 * @param allowNullValues
	 *            whether to allow {@code null} values (adapting them to an
	 *            internal null holder value)
	 */
	public CoherenceCacheWrapper(String name, NamedCache store, boolean allowNullValues) {
		super(allowNullValues);
		Assert.notNull(name, "Name must not be null");
		Assert.notNull(store, "Store must not be null");
		this.name = name;
		this.store = store;
	}

	@Override
	public final String getName() {
		return this.name;
	}

	@Override
	public final NamedCache getNativeCache() {
		return this.store;
	}

	@Override
	protected Object lookup(Object key) {
		return this.store.get(key);
	}

	@Override
	public void put(Object key, Object value) {
		this.store.put(key, toStoreValue(value));
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		Object existing = this.store.put(key, toStoreValue(value));
		return toValueWrapper(existing);
	}

	@Override
	public void evict(Object key) {
		this.store.remove(key);
	}

	@Override
	public void clear() {
		this.store.clear();
	}

}
