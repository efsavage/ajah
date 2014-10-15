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
package com.ajah.user.invitation;

import java.util.Date;

import lombok.Data;

import com.ajah.user.UserId;
import com.ajah.util.Identifiable;

/**
 * An invitation sent by a user to another person.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
public class Invitation implements Identifiable<InvitationId> {

	private InvitationId id;
	private UserId userId;
	private UserId acceptedUserId;
	private String address;
	private InvitationStatus status;
	private InvitationType type;
	private InvitationChannel channel;
	private Date created;
	private Date sent;
	private Date responded;
	private int sendCount;
	private String reference;
	private String targetType;
	private String targetId;
	private String message;
	private String lang;

}
