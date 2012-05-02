/*
 *  Copyright 2011 Eric F. Savage, code@efsavage.com
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
package com.ajah.user.message;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.ajah.user.UserId;

/**
 * Basic implementation of UserMessage interface.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Data
public class UserMessageImpl implements UserMessage {

	protected UserMessageId id;
	protected UserMessageStatus status;
	protected UserMessageType type;
	protected String subject;
	protected String body;
	protected Date created;
	protected UserId sender;
	protected List<UserId> to;
	protected List<UserId> cc;
	protected List<UserId> bcc;

	/**
	 * Constructor with ID.
	 * 
	 * @param id
	 *            Unique ID of the message.
	 */
	public UserMessageImpl(final UserMessageId id) {
		this.id = id;
	}

}
