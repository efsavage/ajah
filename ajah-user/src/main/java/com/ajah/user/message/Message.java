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
import com.ajah.util.Identifiable;

/**
 * A Message corresponds to a person who is using the system. A person should
 * not need to have separate messages within the application.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface Message extends Identifiable<MessageId> {

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
	@Override
	void setId(final MessageId messageId);

	/**
	 * Sets the message status.
	 * 
	 * @param status
	 *            The message's status, should not be null.
	 */
	void setStatus(final MessageStatus status);

	/**
	 * Sets the message's type.
	 * 
	 * @param type
	 *            The message's type, should not be null.
	 */
	void setType(final MessageType type);

	/**
	 * Sets the creation date of the message.
	 * 
	 * @param date
	 *            The creation date of the message.
	 */
	void setCreated(final Date date);

	/**
	 * Sets the message sender's ID.
	 * 
	 * @param userId
	 *            The message sender's ID.
	 */
	void setSender(final UserId userId);

	/**
	 * Sets the list of IDs of direct message recipients.
	 * 
	 * @param userIds
	 *            The list of IDs of direct message recipients.
	 */
	void setTo(final List<UserId> userIds);

	/**
	 * Sets the list of IDs of copied message recipients.
	 * 
	 * @param userIds
	 *            The list of IDs of copied message recipients.
	 */
	void setCc(final List<UserId> userIds);

	/**
	 * Sets the list of IDs of blind-copied message recipients.
	 * 
	 * @param userIds
	 *            The list of IDs of blind-copied message recipients.
	 */
	void setBcc(final List<UserId> userIds);

	/**
	 * Sets the message's subject.
	 * 
	 * @param string
	 *            The message's subject.
	 */
	void setSubject(final String string);

	/**
	 * Sets the message's body.
	 * 
	 * @param string
	 *            The message's body.
	 */
	void setBody(final String string);

	/**
	 * Gets the message's creation date.
	 * 
	 * @return The message's creation date.
	 */
	Date getCreated();

	/**
	 * Gets the senders ID.
	 * 
	 * @return The senders ID.
	 */
	UserId getSender();

	/**
	 * Gets the list of IDs of direct message recipients.
	 * 
	 * @return The list of IDs of direct message recipients.
	 */
	List<UserId> getTo();

	/**
	 * The list of IDs of copied message recipients.
	 * 
	 * @return The list of IDs of copied message recipients.
	 */
	List<UserId> getCc();

	/**
	 * Gets the list of IDs of blind-copied message recipients.
	 * 
	 * @return The list of IDs of blind-copied message recipients.
	 */
	List<UserId> getBcc();

	/**
	 * Sets the message's subject.
	 * 
	 * @return The message's subject.
	 */
	String getSubject();

	/**
	 * Sets the message's body.
	 * 
	 * @return The message's body.
	 */
	String getBody();

}
