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
package com.ajah.user.resetpw.data;

import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.user.resetpw.ResetPasswordRequest;
import com.ajah.user.resetpw.ResetPasswordRequestId;
import com.ajah.util.AjahUtils;

/**
 * Handles data operations for {@link ResetPasswordRequest} entities.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Repository
public class ResetPasswordRequestDao extends AbstractAjahDao<ResetPasswordRequestId, ResetPasswordRequest> {

	// private static final Logger log = Logger.getLogger(UserDao.class.getName());

	/**
	 * INSERTs a {@link ResetPasswordRequest}.
	 * 
	 * @param resetPasswordRequest
	 *            The {@link ResetPasswordRequest} to save, required.
	 */
	public int insert(ResetPasswordRequest resetPasswordRequest) {
		AjahUtils.requireParam(resetPasswordRequest, "resetPasswordRequest");
		return this.jdbcTemplate.update(
				"INSERT INTO pw_reset (pw_reset_id, user_id, created, code, status) VALUES (?,?,?,?,?)",
				new Object[] { resetPasswordRequest.getId().getId(), resetPasswordRequest.getUserId().getId(),
						Long.valueOf(resetPasswordRequest.getCreated().getTime() / 1000), Long.valueOf(resetPasswordRequest.getCode()),
						resetPasswordRequest.getStatus().getId() });
	}

	/**
	 * UPDATEs a {@link ResetPasswordRequest}.
	 * 
	 * @param resetPasswordRequest
	 *            The {@link ResetPasswordRequest} to save, required.
	 */
	public int update(ResetPasswordRequest resetPasswordRequest) {
		AjahUtils.requireParam(resetPasswordRequest, "resetPasswordRequest");
		return this.jdbcTemplate
				.update("UPDATE pw_reset SET user_id = ?, created = ?, code = ?, status = ? WHERE pw_reset_id = ?",
						new Object[] { resetPasswordRequest.getUserId().getId(), Long.valueOf(resetPasswordRequest.getCreated().getTime() / 1000),
								Long.valueOf(resetPasswordRequest.getCode()), resetPasswordRequest.getStatus().getId(),
								resetPasswordRequest.getId().getId() });
	}

}