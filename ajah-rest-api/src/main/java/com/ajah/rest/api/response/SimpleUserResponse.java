/*
 *  Copyright 2016 Eric F. Savage, code@efsavage.com
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
package com.ajah.rest.api.response;

import com.ajah.rest.api.model.relay.user.UserRelay;

/**
 * Extends {@link SimpleApiResponse} with a user property, to be used for
 * responses where the user may/must be logged in.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>,
 *         <a href="mailto:code@efsavage.com">code@efsavage.com</a>,
 *         <a href="https://github.com/efsavage">github.com/efsavage</a>.
 *
 */
public class SimpleUserResponse extends SimpleApiResponse {

	public UserRelay user;

}
