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

import com.ajah.user.UserId;

/**
 * A Message corresponds to a person who is using the system. A person should
 * not need to have separate messages within the application.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface Message {

	/**
	 * Unique ID of the message.
	 * 
	 * @return Unique ID of the message, may be null.
	 */
	MessageId getId();

	/**
	 * The status of the message.
	 * 
	 * @return The status of the message. May be null if not saved/complete.
	 */
	MessageStatus getStatus();

	/**
	 * The type of the message.
	 * 
	 * @return The type of the message. May be null if not saved/complete.
	 */
	MessageType getType();

	/**
	 * Sets the message ID.
	 * 
	 * @param messageId
	 *            The Message ID, should not be null.
	 */
	void setId(MessageId messageId);

	/**
	 * Sets the message status.
	 * 
	 * @param status
	 *            The message's status, should not be null.
	 */
	void setStatus(MessageStatus status);

	/**
	 * Sets the message's type.
	 * 
	 * @param type
	 *            The message's type, should not be null.
	 */
	void setType(MessageType type);

	/**
	 * @param date
	 */
	void setCreated(Date date);

	/**
	 * @param userId
	 */
	void setSender(UserId userId);

	/**
	 * @param userIds
	 */
	void setTo(List<UserId> userIds);

	/**
	 * @param userIds
	 */
	void setCc(List<UserId> userIds);

	/**
	 * @param userIds
	 */
	void setBcc(List<UserId> userIds);

	/**
	 * @param string
	 */
	void setSubject(String string);

	/**
	 * @param string
	 */
	void setBody(String string);

	/**
	 * @return
	 */
	Date getCreated();

	/**
	 * @return
	 */
	UserId getSender();

	/**
	 * @return
	 */
	List<UserId> getTo();

	/**
	 * @return
	 */
	List<UserId> getCc();

	/**
	 * @return
	 */
	List<UserId> getBcc();

	/**
	 * @return
	 */
	String getSubject();

	/**
	 * @return
	 */
	String getBody();

}