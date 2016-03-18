/*
 * Copyright 2002-2012 the original author or authors.
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import com.tangosol.net.NamedCache;

/**
 * {@link FactoryBean} for easy configuration of a {@link MyMapCache}
 * when used within a Spring container. Can be configured through bean properties;
 * uses the assigned Spring bean name as the default cache name.
 *
 * <p>Useful for testing or simple caching scenarios, typically in combination
 * with {@link org.springframework.cache.support.SimpleCacheManager} or
 * dynamically through {@link MyMapCacheManager}.
 *
 * @author Costin Leau
 * @author Juergen Hoeller
 * @since 3.1
 */
public class CoherenceCacheFactoryBean
		implements FactoryBean<CoherenceCacheWrapper>, BeanNameAware, InitializingBean {

	private String name = "";

	private NamedCache store;

	private boolean allowNullValues = true;

	private CoherenceCacheWrapper cache;

	private  Logger logger = LogManager.getLogger(this.getClass());


	/**
	 * Specify the name of the cache.
	 * <p>Default is "" (empty String).
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Specify the ConcurrentMap to use as an internal store
	 * (possibly pre-populated).
	 * <p>Default is a standard {@link java.util.concurrent.ConcurrentHashMap}.
	 */
	public void setStore(NamedCache store) {
		this.store = store;
	}

	/**
	 * Set whether to allow {@code null} values
	 * (adapting them to an internal null holder value).
	 * <p>Default is "true".
	 */
	public void setAllowNullValues(boolean allowNullValues) {
		this.allowNullValues = allowNullValues;
	}

	@Override
	public void setBeanName(String beanName) {
		if (!StringUtils.hasLength(this.name)) {
			setName(beanName);
		}
	}

	@Override
	public void afterPropertiesSet() {
		this.cache = (this.store != null ? new CoherenceCacheWrapper(this.name, this.store, this.allowNullValues) :
				new CoherenceCacheWrapper(this.name, this.allowNullValues));
	}


	@Override
	public CoherenceCacheWrapper getObject() {
		
		logger.debug("impl");
		return this.cache;
	}

	@Override
	public Class<?> getObjectType() {
		return CoherenceCacheWrapper.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
