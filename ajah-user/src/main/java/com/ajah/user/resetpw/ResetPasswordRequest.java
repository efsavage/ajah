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
package com.ajah.user.resetpw;

import java.util.Date;

import lombok.Data;

import com.ajah.user.UserId;
import com.ajah.util.Identifiable;

/**
 * Represents a password reset request record.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Data
public class ResetPasswordRequest implements Identifiable<ResetPasswordRequestId> {

	private ResetPasswordRequestId id;
	private UserId userId;
	private ResetPasswordRequestStatus status;
	private Date created;
	private Date redeemed;
	private long code;

}
