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
package com.ajah.user.alert;

import java.util.Date;

import lombok.Data;

import com.ajah.user.UserId;
import com.ajah.util.Identifiable;

@Data
public class UserAlert implements Identifiable<UserAlertId> {

	private UserAlertId id;
	private UserId userId;
	private String subject;
	private String body;
	/**
	 * The date after which the message should not be shown even if it has not
	 * been dismissed/resolved.
	 */
	private Date expiration;
	private Date responded;
	private String response;
	private UserAlertStatus status;
	private UserAlertType type;
	private UserAlertResponseType responseType;
	private Date created;
	/**
	 * Keep this message even after it has been dismissed/resolved/expired?
	 */
	private boolean preserve;

}
