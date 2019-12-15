/*
 *  Copyright 2012 Eric F. Savage, code@efsavage.com
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
package com.ajah.servlet.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.java.Log;

import com.ajah.http.Browser;
import com.ajah.servlet.util.RequestUtils;
import com.ajah.util.config.Config;

/**
 * Checks to see if the browser is {@link Browser#CHROME} and if not, forwards a
 * configurable error url. Default url is "chrome-only", can be overriden via
 * config property "ajah.chrome-filter.failure".
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Log
public class ChromeFilter extends BaseFilter {

	private String failureUrl = Config.i.get("ajah.chrome-filter.failure", "chrome-only");

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		final Browser browser = RequestUtils.getUserAgent((HttpServletRequest) request).getBrowser();
		if (browser != Browser.CHROME) {
			log.finest("Browser " + browser.getName() + " blocked");
			request.getRequestDispatcher(this.failureUrl).forward(request, response);
			return;
		}
		log.finest("Browser " + browser.getName() + " allowed");
		super.doFilter(request, response, chain);
	}
}
