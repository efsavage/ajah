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
package com.ajah.user.invitation;

import com.ajah.user.User;

/**
 * A user may send an invitation to another person. The rules around the ability
 * to send invitations and what happens after are up to the application, but a
 * successful invitation in this context will always result in the creation of
 * an account.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public interface Invitation {

	/**
	 * Unique ID of this invitation.
	 * 
	 * @return Unique ID of this invitation. May be null if not saved/complete.
	 */
	InvitationId getId();

	/**
	 * Status of this invitation.
	 * 
	 * @return Status of this invitation. May be null if not saved/complete.
	 */
	InvitationStatus getStatus();

	/**
	 * Channel of this invitation.
	 * 
	 * @return Channel of this invitation. May be null if not saved/complete.
	 */
	InvitationChannel getChannel();

	/**
	 * User who sent this invitation.
	 * 
	 * @return User who sent this invitation. May be null if not saved/complete.
	 */
	User getSender();

	/**
	 * Recipient of this invitation. The data contained in this field will vary
	 * based on the channel (email, IM handle, etc).
	 * 
	 * @return Address of invitee within the invitation's channel.
	 */
	String getRecipient();

}