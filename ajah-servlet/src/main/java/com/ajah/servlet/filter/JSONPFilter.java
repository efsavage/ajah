package com.ajah.servlet.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.java.Log;

import com.ajah.util.StringUtils;

/**
 * Wraps a JSON response with a JSONP callback.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Log
public class JSONPFilter extends BaseFilter {

	/**
	 * Wraps a JSON response with a JSONP callback, if possible and appropriate.
	 * 
	 * @see com.ajah.servlet.filter.BaseFilter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		final String callback = request.getParameter("callback");
		if (StringUtils.isBlank(callback)) {
			log.finest("Not adding callback, no \"callback\" parameter specified");
			// If there's no callback, do nothing.
			chain.doFilter(request, response);
			return;
		}
		final OutputStream out = response.getOutputStream();
		final GenericResponseWrapper wrapper = new GenericResponseWrapper((HttpServletResponse) response);
		chain.doFilter(request, wrapper);
		if ("application/json".equals(wrapper.getContentType()) || "jsonp".equals(request.getParameter("format"))) {
			// We've got a JSON response
			if (log.isLoggable(Level.FINEST)) {
				log.finest("Adding callback \"" + callback + "\" to  " + wrapper.getData().length + " bytes");
			}
			out.write(callback.getBytes());
			out.write('(');
			out.write(wrapper.getData());
			out.write(");".getBytes());
		} else {
			if (log.isLoggable(Level.FINEST)) {
				log.finest("Not adding callback, response is not JSON (" + wrapper.getContentType() + ")");
			}
			// It's not JSON, don't mess with it
			out.write(wrapper.getData());
		}
		out.close();
	}

}
