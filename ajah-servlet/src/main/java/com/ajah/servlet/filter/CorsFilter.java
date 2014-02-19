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
import javax.servlet.http.HttpServletResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.ajah.servlet.util.ResponseHeader;
import com.ajah.util.StringUtils;
import com.ajah.util.config.Config;

/**
 * Sets a Access-Control-Allow-Origin based on the "ajah.header.cors" property.
 * If set, will also set a "Access-Control-Allow-Headers" as defined by property
 * "ajah.header.cors-headers", defaults to
 * "Origin, X-Requested-With, Content-Type, Accept".
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CorsFilter extends BaseFilter {

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		final String value = Config.i.get("ajah.header.cors", "null");
		((HttpServletResponse) response).addHeader(ResponseHeader.ACCESS_CONTROL_ALLOW_ORIGIN.getHeader(), value);

		if (!StringUtils.isBlank(value)) {
			final String headers = Config.i.get("ajah.header.cors-headers", "Origin, X-Requested-With, Content-Type, Accept");
			((HttpServletResponse) response).addHeader(ResponseHeader.ACCESS_CONTROL_ALLOW_HEADERS.getHeader(), headers);
		}

		super.doFilter(request, response, chain);
	}
}
