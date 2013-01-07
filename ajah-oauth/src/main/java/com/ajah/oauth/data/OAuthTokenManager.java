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
package com.ajah.oauth.data;

import java.util.Date;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.oauth.OAuthAccessToken;
import com.ajah.oauth.OAuthAccessTokenId;
import com.ajah.oauth.OAuthProvider;
import com.ajah.oauth.OAuthToken;
import com.ajah.oauth.OAuthTokenId;
import com.ajah.oauth.service.AjahOAuthService;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.User;
import com.ajah.user.UserId;

/**
 * Manages operations and persistence of {@link OAuthToken}s and
 * {@link OAuthAccessToken}s.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Service
@Slf4j
public class OAuthTokenManager {

	@Autowired
	OAuthTokenDao tokenDao;

	@Autowired
	OAuthAccessTokenDao accessTokenDao;

	/**
	 * Loads an access token by its id value.
	 * 
	 * @param provider
	 *            The OAuth provider.
	 * @param userId
	 *            The user seeking to authenticate.
	 * @param oAuthAccessTokenId
	 *            The token ID to load.
	 * @return The matching token, if found, will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws OAuthAccessTokenNotFoundException
	 *             If the requested token could not be found.
	 */
	public OAuthAccessToken load(final OAuthProvider provider, final UserId userId, final OAuthAccessTokenId oAuthAccessTokenId) throws DataOperationException, OAuthAccessTokenNotFoundException {
		final OAuthAccessToken token = this.accessTokenDao.load(provider, userId, oAuthAccessTokenId);
		if (token == null) {
			throw new OAuthAccessTokenNotFoundException(oAuthAccessTokenId);
		}
		return token;
	}

	/**
	 * Loads a token by its token value.
	 * 
	 * @param provider
	 *            The token's provider.
	 * @param userId
	 * @param oAuthTokenId
	 *            The token ID to load, required.
	 * @return The matching {@link OAuthToken}, will not be null.
	 * @throws OAuthTokenNotFoundException
	 *             If the token could not be found.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */

	public OAuthToken loadToken(final OAuthProvider provider, final UserId userId, final OAuthTokenId oAuthTokenId) throws DataOperationException, OAuthTokenNotFoundException {
		final OAuthToken token = this.tokenDao.load(provider, userId, oAuthTokenId);
		if (token == null) {
			throw new OAuthTokenNotFoundException(oAuthTokenId);
		}
		return token;
	}

	/**
	 * Loads a token by its token value.
	 * 
	 * @param provider
	 *            The token's provider.
	 * @param userId
	 * @param oauthToken
	 *            The token value to load, required.
	 * @return The matching {@link OAuthToken}, will not be null.
	 * @throws OAuthTokenNotFoundException
	 *             If the token could not be found.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public OAuthToken loadToken(final OAuthProvider provider, final UserId userId, final String oauthToken) throws OAuthTokenNotFoundException, DataOperationException {
		final OAuthToken token = this.tokenDao.load(provider, userId, oauthToken);
		if (token == null) {
			throw new OAuthTokenNotFoundException(provider.getName() + ": " + oauthToken);
		}
		return token;
	}

	/**
	 * Inserts or update an {@link OAuthAccessToken}, as necessary.
	 * 
	 * @param oAuthAccessToken
	 *            The token to save.
	 * @return The number of rows affected.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int save(final OAuthAccessToken oAuthAccessToken) throws DataOperationException {
		if (oAuthAccessToken.getId() == null) {
			oAuthAccessToken.setId(new OAuthAccessTokenId(UUID.randomUUID().toString()));
			oAuthAccessToken.setCreated(new Date());
			oAuthAccessToken.setModified(oAuthAccessToken.getCreated());
			return this.accessTokenDao.insert(oAuthAccessToken);
		}
		oAuthAccessToken.setModified(new Date());
		return this.accessTokenDao.update(oAuthAccessToken);
	}

	/**
	 * Inserts or update an {@link OAuthToken}, as necessary.
	 * 
	 * @param oAuthToken
	 *            The token to save.
	 * @return The number of rows affected.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int save(final OAuthToken oAuthToken) throws DataOperationException {
		if (oAuthToken.getId() == null) {
			oAuthToken.setId(new OAuthTokenId(UUID.randomUUID().toString()));
			oAuthToken.setCreated(new Date());
			oAuthToken.setModified(oAuthToken.getCreated());
			return this.tokenDao.insert(oAuthToken);
		}
		oAuthToken.setModified(new Date());
		return this.tokenDao.update(oAuthToken);
	}

	/**
	 * Verifies a token and creates/saves an access token.
	 * 
	 * @param user
	 *            The user to verify.
	 * @param oauthToken
	 *            The token to verify.
	 * @param oauthVerifier
	 *            The verification code.
	 * @param service
	 *            The OAuth service to use.
	 * @return The success URL of the token.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws OAuthTokenNotFoundException
	 *             If the token could not be located.
	 */
	public String verify(final User user, final String oauthToken, final String oauthVerifier, final AjahOAuthService service) throws DataOperationException, OAuthTokenNotFoundException {
		final OAuthToken token = loadToken(OAuthProvider.TWITTER, user.getId(), oauthToken);
		final OAuthAccessToken accessToken = service.verify(token, oauthVerifier);
		save(accessToken);
		token.setOAuthAccessTokenId(accessToken.getId());
		save(token);
		log.debug("Twitter OAuth verified for user " + user.getUsername());
		return token.getSuccessUrl().replaceAll("\\{tokenId\\}", token.getId().toString());
	}

}
