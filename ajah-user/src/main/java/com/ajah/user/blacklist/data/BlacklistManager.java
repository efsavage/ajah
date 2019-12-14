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
package com.ajah.user.blacklist.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.blacklist.Blacklist;
import com.ajah.user.blacklist.BlacklistId;
import com.ajah.user.blacklist.BlacklistStatus;
import com.ajah.user.blacklist.BlacklistType;
import com.ajah.util.data.format.EmailAddress;

import lombok.extern.java.Log;

/**
 * Manages data operations for {@link Blacklist}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class BlacklistManager {

	@Autowired
	private BlacklistDao blacklistDao;

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
	 *            The blacklist type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final BlacklistType type, final BlacklistStatus status) throws DataOperationException {
		return this.blacklistDao.count(type, status);
	}

	/**
	 * Creates a new {@link Blacklist} with the given properties.
	 * 
	 * @param part1
	 *            The first part of the blacklist, see {@link BlacklistType} for
	 *            details on usage.
	 * @param part2
	 *            The second part of the blacklist, see {@link BlacklistType}
	 *            for details on usage.
	 * @param type
	 *            The type of blacklist, required.
	 * @param status
	 *            The status of the blacklist, required.
	 * @return The result of the creation, which will include the new blacklist
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<Blacklist> create(final String part1, final String part2, final BlacklistType type, final BlacklistStatus status) throws DataOperationException {
		final Blacklist blacklist = new Blacklist();
		blacklist.setPart1(part1);
		blacklist.setPart2(part2);
		blacklist.setType(type);
		blacklist.setStatus(status);
		final DataOperationResult<Blacklist> result = save(blacklist);
		return result;
	}

	/**
	 * Marks the entity as {@link BlacklistStatus#DELETED}.
	 * 
	 * @param blacklistId
	 *            The ID of the blacklist to delete.
	 * @return The result of the deletion, will not include the new blacklist at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws BlacklistNotFoundException
	 *             If the ID specified did not match any blacklists.
	 */
	public DataOperationResult<Blacklist> delete(final BlacklistId blacklistId) throws DataOperationException, BlacklistNotFoundException {
		final Blacklist blacklist = load(blacklistId);
		blacklist.setStatus(BlacklistStatus.DELETED);
		final DataOperationResult<Blacklist> result = save(blacklist);
		return result;
	}

	/**
	 * Returns a list of {@link Blacklist}s that match the specified criteria.
	 * 
	 * @param type
	 *            The type of blacklist, optional.
	 * @param status
	 *            The status of the blacklist, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link Blacklist}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<Blacklist> list(final BlacklistType type, final BlacklistStatus status, final long page, final long count) throws DataOperationException {
		return this.blacklistDao.list(null, null, type, status, page, count);
	}

	/**
	 * Returns a list of {@link BlacklistStatus#ACTIVE}
	 * {@link BlacklistType#EMAIL} {@link Blacklist}s that match the specified
	 * criteria.
	 * 
	 * @param emailAddress
	 *            The address to check.
	 * @return A list of {@link Blacklist}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<Blacklist> list(final EmailAddress emailAddress) throws DataOperationException {
		return this.blacklistDao.list(emailAddress.getUsername(), emailAddress.getDomain(), BlacklistType.EMAIL, BlacklistStatus.ACTIVE, 0, 1000);
	}

	/**
	 * Returns a list of {@link BlacklistStatus#ACTIVE}
	 * {@link BlacklistType#REAL_NAME} {@link Blacklist}s that match the
	 * specified criteria.
	 * 
	 * @param firstName
	 *            The first name to check.
	 * @param lastName
	 *            The last name to check.
	 * @return A list of {@link Blacklist}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<Blacklist> listRealName(final String firstName, final String lastName) throws DataOperationException {
		return this.blacklistDao.list(firstName, lastName, BlacklistType.REAL_NAME, BlacklistStatus.ACTIVE, 0, 1000);
	}

	/**
	 * Returns a list of {@link BlacklistStatus#ACTIVE}
	 * {@link BlacklistType#USERNAME} {@link Blacklist}s that match the
	 * specified criteria.
	 * 
	 * @param username
	 *            The username to check.
	 * @return A list of {@link Blacklist}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<Blacklist> listUsername(final String username) throws DataOperationException {
		return this.blacklistDao.list(username, null, BlacklistType.USERNAME, BlacklistStatus.ACTIVE, 0, 1000);
	}

	/**
	 * Loads an {@link Blacklist} by it's ID.
	 * 
	 * @param blacklistId
	 *            The ID to load, required.
	 * @return The matching blacklist, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws BlacklistNotFoundException
	 *             If the ID specified did not match any blacklists.
	 */
	public Blacklist load(final BlacklistId blacklistId) throws DataOperationException, BlacklistNotFoundException {
		final Blacklist blacklist = this.blacklistDao.load(blacklistId);
		if (blacklist == null) {
			throw new BlacklistNotFoundException(blacklistId);
		}
		return blacklist;
	}

	/**
	 * Saves an {@link Blacklist}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param blacklist
	 *            The blacklist to save.
	 * @return The result of the save operation, which will include the new
	 *         blacklist at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<Blacklist> save(final Blacklist blacklist) throws DataOperationException {
		boolean create = false;
		if (blacklist.getId() == null) {
			blacklist.setId(new BlacklistId(UUID.randomUUID().toString()));
			create = true;
		}
		if (blacklist.getCreated() == null) {
			blacklist.setCreated(new Date());
			create = true;
		}
		if (create) {
			final DataOperationResult<Blacklist> result = this.blacklistDao.insert(blacklist);
			log.fine("Created Blacklist " + blacklist.getPart1() + ", " + blacklist.getPart2() + " [" + blacklist.getId() + "]");
			return result;
		}
		final DataOperationResult<Blacklist> result = this.blacklistDao.update(blacklist);
		if (result.getRowsAffected() > 0) {
			log.fine("Updated Blacklist " + blacklist.getPart1() + ", " + blacklist.getPart2() + " [" + blacklist.getId() + "]");
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
		return this.blacklistDao.searchCount(search);
	}

}
