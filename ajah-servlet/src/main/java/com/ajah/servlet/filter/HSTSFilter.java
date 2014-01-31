/*
 *  Copyright 2014 Eric F. Savage, code@efsavage.com
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
import javax.servlet.http.HttpServletResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.ajah.servlet.util.ResponseHeader;
import com.ajah.util.config.Config;

/**
 * Sets a Strict-Transport-Security header based on the
 * "ajah.header.hsts-max-age" property, which is specified in seconds and
 * defaults to 31536000 (1 year). Also supports the
 * "ajah.header.include-subdomains" boolean property which defaults to false.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HSTSFilter extends BaseFilter {

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		long maxAge = Config.i.getLong("ajah.header.hsts-max-age", 31536000);
		boolean includeSubDomains = Config.i.getBoolean("ajah.header.include-subdomains", false);
		((HttpServletResponse) response).addHeader(ResponseHeader.STRICT_TRANSPORT_SECURITY.getHeader(), "max-age=" + maxAge + (includeSubDomains ? "; includeSubDomains" : ""));
		super.doFilter(request, response, chain);
	}
}
