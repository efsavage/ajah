/*
 *  Copyright 2013 Eric F. Savage, code@efsavage.com
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
package com.ajah.oauth.spring.controller;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ajah.oauth.OAuthProvider;
import com.ajah.oauth.data.OAuthTokenManager;
import com.ajah.oauth.data.OAuthTokenNotFoundException;
import com.ajah.oauth.service.FacebookOAuthService;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * Controllers for dealing with OAuth operations for
 * {@link OAuthProvider#FACEBOOK}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Controller
@Slf4j
public class FacebookOAuthController extends AbstractOAuthController {

	@Autowired
	OAuthTokenManager oAuthTokenManager;

	@Autowired
	FacebookOAuthService facebookService;

	/**
	 * Verifies a token, this should commonly be the callback (the token
	 * contains that ultimate success url).
	 * 
	 * @param oauthToken
	 *            The token, required.
	 * @param oauthVerifier
	 *            The verification code, required.
	 * @param request
	 *            The Request.
	 * @return Redirect to success url if there was no error.
	 * @throws DataOperationException
	 *             If a query could not be executed.
	 * @throws OAuthTokenNotFoundException
	 *             If the token could not be found.
	 */
	@RequestMapping(value = "/oauth/facebook", method = RequestMethod.GET)
	public String facebook(@RequestParam("token") final String oauthToken, @RequestParam("code") final String oauthVerifier, final HttpServletRequest request) throws DataOperationException,
			OAuthTokenNotFoundException {
		final String successUrl = this.oAuthTokenManager.verify(getUser(request), OAuthProvider.FACEBOOK, oauthToken, oauthVerifier, this.facebookService);
		log.debug("Verification successful, redirecting to " + successUrl);
		return "redirect:" + successUrl;
	}

}
