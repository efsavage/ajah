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
package com.ajah.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.java.Log;

/**
 * Simple skeleton class that adds some logging and implements extra methods.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Log
@SuppressWarnings("static-method")
public abstract class AjahFilter implements Filter {

	/**
	 * Does nothing except log.
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		log.finer(getClass().getName() + " destroyed");
	}

	/**
	 * Does nothing except log and
	 * {@link FilterChain#doFilter(ServletRequest, ServletResponse)}.
	 * 
	 * @param request
	 *            The servlet request. Cannot be null.
	 * @param response
	 *            The servlet response. Cannot be null.
	 * @param chain
	 *            The filter chain. Cannot be null.
	 * @throws ServletException
	 *             If thrown by
	 *             {@link FilterChain#doFilter(ServletRequest, ServletResponse)}
	 * @throws IOException
	 *             If thrown by
	 *             {@link FilterChain#doFilter(ServletRequest, ServletResponse)}
	 * 
	 * @see javax.servlet.Filter#init(FilterConfig)
	 */
	public void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);
	}

	/**
	 * Does nothing except log and
	 * {@link FilterChain#doFilter(ServletRequest, ServletResponse)}.
	 * 
	 * @see javax.servlet.Filter#init(FilterConfig)
	 */
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
	}

	/**
	 * Does nothing except log.
	 * 
	 * @see javax.servlet.Filter#init(FilterConfig)
	 */
	@Override
	public void init(final FilterConfig arg0) {
		log.finer(getClass().getName() + " initialized");
	}

}
