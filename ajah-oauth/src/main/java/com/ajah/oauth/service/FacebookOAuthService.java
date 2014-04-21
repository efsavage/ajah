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
package com.ajah.oauth.service;

import java.util.Date;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.springframework.stereotype.Service;

import com.ajah.oauth.OAuthAccessToken;
import com.ajah.oauth.OAuthProvider;
import com.ajah.oauth.OAuthToken;
import com.ajah.util.config.Config;

/**
 * Operations for the {@link FacebookApi} service.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Service
@Slf4j
public class FacebookOAuthService implements AjahOAuthService {

	/**
	 * Generates a new {@link OAuthToken}. Note that this token is not saved.
	 * 
	 * @param callback
	 *            The callback for the service.
	 * 
	 * @return A token for the Facebook service.
	 */
	public static OAuthToken getToken() {
		// OAuth 2.0 doesn't require tokens, but we're going to use it anyways
		// since we store the success url there, and it will be helpful in
		// tracking down failures.
		final String tempToken = UUID.randomUUID().toString();
		final ServiceBuilder serviceBuilder = new ServiceBuilder().provider(FacebookApi.class).apiKey(Config.i.get("oauth.facebook.key")).apiSecret(Config.i.get("oauth.facebook.secret"));
		serviceBuilder.callback(Config.i.get("oauth.facebook.callback") + "?token=" + tempToken);
		final OAuthService service = serviceBuilder.build();
		final OAuthToken token = new OAuthToken();
		token.setProvider(OAuthProvider.FACEBOOK);
		token.setToken(tempToken);
		token.setSecret(tempToken);
		token.setAuthUrl(service.getAuthorizationUrl(null));
		token.setCreated(new Date());
		return token;
	}

	/**
	 * Generates a new {@link OAuthAccessToken}. Note that this token is not
	 * saved.
	 * 
	 * @param token
	 *            The token to verify.
	 * @param oauthVerifier
	 *            The verification code.
	 * @return The {@link OAuthAccessToken} to be used to sign future requests.
	 */
	@Override
	public OAuthAccessToken verify(final OAuthToken token, final String oauthVerifier) {
		final ServiceBuilder serviceBuilder = new ServiceBuilder().provider(FacebookApi.class).apiKey(Config.i.get("oauth.facebook.key")).apiSecret(Config.i.get("oauth.facebook.secret"));
		serviceBuilder.callback(Config.i.get("oauth.facebook.callback") + "?token=" + token.getToken());
		final OAuthService service = serviceBuilder.build();
		final Token requestToken = new Token(token.getToken(), Config.i.get("oauth.facebook.secret"));
		final Verifier verifier = new Verifier(oauthVerifier);
		final Token remoteAccessToken = service.getAccessToken(requestToken, verifier);
		log.info("Access Token: " + remoteAccessToken.getToken());
		log.info("Access Token Secret: " + remoteAccessToken.getSecret());
		final OAuthAccessToken accessToken = new OAuthAccessToken();
		accessToken.setToken(remoteAccessToken.getToken());
		accessToken.setSecret(remoteAccessToken.getSecret());
		accessToken.setUserId(token.getUserId());
		accessToken.setProvider(OAuthProvider.FACEBOOK);
		return accessToken;
	}

}
