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
package com.ajah.oauth;

import java.util.Date;

import lombok.Data;

import com.ajah.user.UserId;
import com.ajah.util.Identifiable;

/**
 * An OAuth access token.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Data
public class OAuthAccessToken implements Identifiable<OAuthAccessTokenId> {

	private OAuthAccessTokenId id;
	private UserId userId;
	private OAuthProvider provider;
	private String token;
	private String secret;
	private Date created;
	private Date modified;

}
