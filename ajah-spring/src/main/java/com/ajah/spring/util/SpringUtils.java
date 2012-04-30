/*
 *  Copyright 2011 Eric F. Savage, code@efsavage.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.ajah.spring.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;

/**
 * High-level utilities for Spring.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class SpringUtils {

	/**
	 * Creates a {@link SimpleCacheManager} and a set of named
	 * {@link ConcurrentMapCacheFactoryBean}s.
	 * 
	 * @param cacheNames
	 *            The names of the caches, must include at least one.
	 * @return A new cache manager with new caches.
	 */
	public SimpleCacheManager getSimpleCache(String... cacheNames) {
		List<Cache> caches = new ArrayList<>();
		for (String cacheName : cacheNames) {
			ConcurrentMapCacheFactoryBean cache = new ConcurrentMapCacheFactoryBean();
			cache.setName(cacheName);
			caches.add(cache.getObject());
		}
		SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
		simpleCacheManager.setCaches(caches);
		return simpleCacheManager;
	}
}
