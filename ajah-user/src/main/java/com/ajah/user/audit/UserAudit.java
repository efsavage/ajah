/*
 *  Copyright 2014-2015 Eric F. Savage, code@efsavage.com
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
package com.ajah.user.audit;

import java.util.Date;

import javax.persistence.Transient;

import lombok.Data;

import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.util.Identifiable;

@Data
public class UserAudit implements Identifiable<UserAuditId> {

	private UserAuditId id;
	/**
	 * The user the changed occurred for.
	 */
	private UserId userId;
	/**
	 * If this changed was performed by administrative staff, the user that made
	 * the change.
	 */
	private UserId staffUserId;
	private UserAuditField field;
	private String oldValue;
	private String newValue;
	private UserAuditType type;
	private Date created;
	private String userComment;
	private String staffComment;
	private String ip;
	private String headers;

	@Transient
	private User user;

	@Transient
	private User staffUser;

	/**
	 * A human readable version of the data, for where the actual data is a code
	 * or something unreadable or should be redacted.
	 */
	@Transient
	private String oldValueHuman;

	/**
	 * A human readable version of the data, for where the actual data is a code
	 * or something unreadable or should be redacted.
	 */
	@Transient
	private String newValueHuman;

}
