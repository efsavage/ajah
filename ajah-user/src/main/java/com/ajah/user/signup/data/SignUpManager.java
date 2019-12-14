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
package com.ajah.user.signup.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.crypto.Password;
import com.ajah.geo.State;
import com.ajah.geo.iso.ISOCountry;
import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.User;
import com.ajah.user.UserType;
import com.ajah.user.data.UserManager;
import com.ajah.user.info.UserInfo;
import com.ajah.user.info.UserSourceId;
import com.ajah.user.signup.DuplicateUsernameException;
import com.ajah.user.signup.SignUp;
import com.ajah.user.signup.SignUpId;
import com.ajah.user.signup.SignUpStatus;
import com.ajah.user.signup.SignUpType;
import com.ajah.util.StringUtils;
import com.ajah.util.data.Month;
import com.ajah.util.data.format.EmailAddress;

import lombok.extern.java.Log;

/**
 * Manages data operations for {@link SignUp}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class SignUpManager {

	@Autowired
	private SignUpDao signUpDao;

	@Autowired
	private UserManager userManager;

	/**
	 * Returns a count of all records.
	 * 
	 * @return Count of all records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count() throws DataOperationException {
		return count(null, null);
	}

	/**
	 * Counts the records available that match the criteria.
	 * 
	 * @param type
	 *            The signUp type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final SignUpType type, final SignUpStatus status) throws DataOperationException {
		return this.signUpDao.count(type, status);
	}

	/**
	 * Marks the entity as {@link SignUpStatus#DELETED}.
	 * 
	 * @param signUpId
	 *            The ID of the signUp to delete.
	 * @return The result of the deletion, will not include the new signUp at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws SignUpNotFoundException
	 *             If the ID specified did not match any signUps.
	 */
	public DataOperationResult<SignUp> delete(final SignUpId signUpId) throws DataOperationException, SignUpNotFoundException {
		final SignUp signUp = load(signUpId);
		signUp.setStatus(SignUpStatus.DELETED);
		final DataOperationResult<SignUp> result = save(signUp);
		return result;
	}

	/**
	 * Returns a list of {@link SignUp}s that match the specified criteria.
	 * 
	 * @param type
	 *            The type of signUp, optional.
	 * @param status
	 *            The status of the signUp, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link SignUp}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<SignUp> list(final SignUpType type, final SignUpStatus status, final long page, final long count) throws DataOperationException {
		return this.signUpDao.list(type, status, page, count);
	}

	/**
	 * Loads an {@link SignUp} by it's ID.
	 * 
	 * @param signUpId
	 *            The ID to load, required.
	 * @return The matching signUp, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws SignUpNotFoundException
	 *             If the ID specified did not match any signUps.
	 */
	public SignUp load(final SignUpId signUpId) throws DataOperationException, SignUpNotFoundException {
		final SignUp signUp = this.signUpDao.load(signUpId);
		if (signUp == null) {
			throw new SignUpNotFoundException(signUpId);
		}
		return signUp;
	}

	/**
	 * Saves an {@link SignUp}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param signUp
	 *            The signUp to save.
	 * @return The result of the save operation, which will include the new
	 *         signUp at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<SignUp> save(final SignUp signUp) throws DataOperationException {
		boolean create = false;
		if (signUp.getId() == null) {
			signUp.setId(new SignUpId(UUID.randomUUID().toString()));
			create = true;
		}
		if (signUp.getCreated() == null) {
			signUp.setCreated(new Date());
			create = true;
		}
		if (create) {
			final DataOperationResult<SignUp> result = this.signUpDao.insert(signUp);
			log.fine("Created SignUp " + signUp.getUsername() + " [" + signUp.getId() + "]");
			return result;
		}
		final DataOperationResult<SignUp> result = this.signUpDao.update(signUp);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated SignUp " + signUp.getUsername() + " [" + signUp.getId() + "]");
		}
		return result;
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(final String search) throws DataOperationException {
		return this.signUpDao.searchCount(search);
	}

	/**
	 * Returns a signUp record for a user.
	 * 
	 * @param emailAddress
	 *            Username or email of the user logging in.
	 * @param password
	 *            Password of the user logging in, unencrypted.
	 * @param ip
	 *            IP of requesting user
	 * @param source
	 *            Source of signUp attempt
	 * @param type
	 *            Type of signUp attempt
	 * @return SignUp record, will never return null.
	 * @throws DataOperationException
	 *             If the queries could not be completed.
	 * @throws DuplicateUsernameException
	 *             If a user with that username already exists.
	 */
	public SignUp signUp(final EmailAddress emailAddress, final Password password, final String ip, final UserSourceId source, final UserType type) throws DataOperationException,
			DuplicateUsernameException {
		log.fine("SignUp attempt for: " + emailAddress);
		final SignUp signUp = new SignUp();
		signUp.setIp(ip);
		signUp.setCreated(new Date());
		signUp.setSource(source);
		signUp.setStatus(SignUpStatus.SUCCESS);
		// TODO signup should be saved
		if (this.userManager.usernameExists(emailAddress.toString())) {
			log.fine(emailAddress.toString() + " is in use");
			throw new DuplicateUsernameException(emailAddress.toString());
		}

		final User user = this.userManager.createUser(emailAddress.toString(), emailAddress, password, ip, source, type);
		log.info(user.getUsername() + " created!");
		signUp.setUser(user);
		return signUp;
	}

	public SignUp signUp(final String username, final Password password, final EmailAddress emailAddress, final String firstName, final String lastName, final Integer birthDay,
			final Month birthMonth, final Integer birthYear, final String address1, final String address2, final String address3, final State state, final ISOCountry country, final String ip,
			final String userAgent, final UserSourceId source, final String promoCode, final String referralSource, final String referralSourceOther, final UserType type)
			throws DataOperationException, DuplicateUsernameException {
		log.fine("SignUp attempt for: " + emailAddress);
		final SignUp signUp = new SignUp();
		signUp.setUsername(username.trim().replaceAll(" +", " "));

		signUp.setIp(ip);
		signUp.setUserAgent(userAgent);

		signUp.setType(SignUpType.STANDARD);
		signUp.setCreated(new Date());
		signUp.setSource(source);
		signUp.setStatus(SignUpStatus.SUCCESS);
		signUp.setPromoCode(promoCode);
		signUp.setReferralSource(StringUtils.safeLeft(referralSource, 100));
		signUp.setReferralSourceOther(StringUtils.safeLeft(referralSourceOther, 200));

		signUp.setBirthDay(birthDay);
		signUp.setBirthMonth(birthMonth);
		signUp.setBirthYear(birthYear);

		signUp.setAddress1(address1);
		signUp.setAddress2(address2);
		signUp.setAddress3(address3);
		signUp.setState(state == null ? null : state.getAbbr());
		signUp.setCountry(country);

		if (this.userManager.usernameExists(emailAddress.toString())) {
			log.fine(emailAddress.toString() + " is in use");
			throw new DuplicateUsernameException(emailAddress.toString());
		}

		final User user = this.userManager.createUser(username.trim().replaceAll(" +", " "), emailAddress, password, ip, source, type);
		final UserInfo userInfo = this.userManager.getUserInfo(user.getId());
		userInfo.setFirstName(firstName);
		userInfo.setLastName(lastName);
		userInfo.setBirthDay(birthDay);
		userInfo.setBirthMonth(birthMonth);
		userInfo.setBirthYear(birthYear);
		userInfo.setCurrentCountry(country);
		userInfo.setSource(source);
		this.userManager.save(userInfo);
		log.info(user.getUsername() + " created!");
		signUp.setUserId(user.getId());
		signUp.setUser(user);
		signUp.setUserInfo(userInfo);
		save(signUp);
		return signUp;
	}
}
