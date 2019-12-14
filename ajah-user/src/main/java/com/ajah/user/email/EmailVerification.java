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
package com.ajah.user.email;

import java.util.Date;

import com.ajah.util.Identifiable;

import lombok.Data;

/**
 * Generated for an {@link Email} and (the code is) sent to that email for
 * verification.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
public class EmailVerification implements Identifiable<EmailVerificationId> {

	private EmailVerificationId id;
	private EmailId emailId;
	private String code;
	private EmailVerificationStatus status;
	private EmailVerificationType type;
	private Date created;

}
