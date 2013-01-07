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

import com.ajah.oauth.OAuthAccessToken;
import com.ajah.oauth.OAuthToken;
import com.ajah.oauth.OAuthTokenId;

/**
 * Thrown when an {@link OAuthToken} was expected to be found, but was not.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class OAuthTokenNotFoundException extends Exception {

	/**
	 * Thrown when an {@link OAuthAccessToken} could not be found by it's
	 * internal ID.
	 * 
	 * @param oAuthTokenId
	 *            The internal ID that was sought.
	 */
	public OAuthTokenNotFoundException(final OAuthTokenId oAuthTokenId) {
		super("ID: " + oAuthTokenId);
	}

	/**
	 * Thrown when an {@link OAuthToken} could not be found by its token value.
	 * 
	 * @param oauthToken
	 *            The token value that was sought.
	 */
	public OAuthTokenNotFoundException(final String oauthToken) {
		super(oauthToken);
	}

}
