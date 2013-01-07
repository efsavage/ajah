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

import com.ajah.oauth.OAuthAccessToken;
import com.ajah.oauth.OAuthToken;
import com.ajah.oauth.data.OAuthTokenManager;

/**
 * Interface for all services that the generic data operation functions can deal
 * with.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public interface AjahOAuthService {

	/**
	 * Verify a token and create a new {@link OAuthAccessToken}. This new entity
	 * should not be persisted, that is handled elsewhere, typically by
	 * {@link OAuthTokenManager}.
	 * 
	 * @param token
	 *            The token to verify.
	 * @param oauthVerifier
	 *            The verification code.
	 * @return A new OAuthAccessToken that is ready to persist.
	 */
	OAuthAccessToken verify(final OAuthToken token, final String oauthVerifier);

}
