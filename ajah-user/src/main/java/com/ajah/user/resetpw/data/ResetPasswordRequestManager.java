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

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajah.user.User;
import com.ajah.user.resetpw.ResetPasswordRequest;
import com.ajah.user.resetpw.ResetPasswordRequestId;
import com.ajah.user.resetpw.ResetPasswordRequestNotFoundException;
import com.ajah.user.resetpw.ResetPasswordRequestStatus;
import com.ajah.user.resetpw.ResetPasswordRequestStatusImpl;
import com.ajah.util.AjahUtils;
import com.ajah.util.RandomUtils;

/**
 * Manages the persistence and transport of reset password requests.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ResetPasswordRequestManager {

	@Autowired
	ResetPasswordRequestDao resetPasswordRequestDao;

	/**
	 * Finds a {@link ResetPasswordRequest} by it's code.
	 * 
	 * @param code
	 *            The code to query on, required to be greater than zero.
	 * @return The {@link ResetPasswordRequest}, if found.
	 * @throws ResetPasswordRequestNotFoundException
	 *             If no {@link ResetPasswordRequest} is found.
	 */
	public ResetPasswordRequest getResetPasswordRequestByCode(final long code) throws ResetPasswordRequestNotFoundException {
		AjahUtils.requireParam(code, "code", 1);
		final ResetPasswordRequest rpr = this.resetPasswordRequestDao.findByField("code", Long.valueOf(code));
		if (rpr == null) {
			throw new ResetPasswordRequestNotFoundException(code);
		}
		return rpr;
	}

	/**
	 * Redeems a request, setting its status and redeemed date, then saving the
	 * record.
	 * 
	 * @see ResetPasswordRequestStatus#isRedeemable()
	 * @param rpr
	 *            The {@link ResetPasswordRequest} to redeem.
	 * @throws IllegalArgumentException
	 *             If the {@link ResetPasswordRequest} is not in a redeemable
	 *             state.
	 */
	public void redeem(final ResetPasswordRequest rpr) {
		if (!rpr.getStatus().isRedeemable()) {
			throw new IllegalArgumentException("Request is not redeemable");
		}
		rpr.setRedeemed(new Date());
		rpr.setStatus(ResetPasswordRequestStatusImpl.REDEEMED);
		this.resetPasswordRequestDao.update(rpr);
	}

	/**
	 * Sends a reset password request to the particular user.
	 * 
	 * @param user
	 * @return The ResetPasswordRequest if creation/transport was successful.
	 *         Will not return null.
	 */
	public ResetPasswordRequest sendResetPassword(final User user) {
		final long code = RandomUtils.getRandomNumber(1000000000000000L, 9999999999999999L);
		final ResetPasswordRequest rpr = new ResetPasswordRequest();
		rpr.setId(new ResetPasswordRequestId(UUID.randomUUID().toString()));
		rpr.setUserId(user.getId());
		rpr.setCode(code);
		rpr.setCreated(new Date());
		rpr.setStatus(ResetPasswordRequestStatusImpl.NEW);
		this.resetPasswordRequestDao.insert(rpr);
		return rpr;
	}

}
