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
package com.ajah.user.message.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.AbstractAjahRowMapper;
import com.ajah.spring.jdbc.AjahDao;
import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.user.UserId;
import com.ajah.user.message.UserMessage;
import com.ajah.user.message.UserMessageId;
import com.ajah.util.AjahUtils;
import com.ajah.util.CollectionUtils;
import com.ajah.util.StringUtils;
import com.ajah.util.date.DateUtils;

/**
 * Data operations on the "message" table.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class UserMessageDao extends AbstractAjahDao<UserMessageId, UserMessage, UserMessage> {

	static final class UserMessageRowMapper extends AbstractAjahRowMapper<UserMessageId, UserMessage> {

		/**
		 * Converts a comma-separated list of User IDs into a type-safe list.
		 * 
		 * @param string
		 *            The list of IDs, separated by commas.
		 * @return A list of user ids, may be empty but will not be null.
		 */
		private static List<UserId> getUserIds(final String string) {
			final List<UserId> userIds = new ArrayList<>();
			if (StringUtils.isBlank(string)) {
				return userIds;
			}
			final String[] stringIds = string.split(",");
			for (final String stringId : stringIds) {
				userIds.add(new UserId(stringId));
			}
			return userIds;
		}

		protected UserMessageRowMapper(final AjahDao<UserMessageId, UserMessage> dao) {
			super(dao);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public UserMessage mapRow(final ResultSet rs, final int rowNum) throws SQLException {
			final UserMessage message = super.mapRow(rs, rowNum);
			message.setTo(getUserIds(rs.getString("to")));
			message.setCc(getUserIds(rs.getString("cc")));
			message.setBcc(getUserIds(rs.getString("bcc")));
			return message;
		}
	}

	/**
	 * Converts a list of user IDs to a comma-delimted string.
	 * 
	 * @param userIds
	 *            The list of user IDs, may be null or empty.
	 * @return Comma-separated list of User IDs. If a null or empty list is
	 *         passed in, will return null.
	 */
	private static String fromUserIds(final List<UserId> userIds) {
		if (CollectionUtils.isEmpty(userIds)) {
			return null;
		}
		if (userIds.size() == 1) {
			return userIds.get(0).getId();
		}
		final StringBuilder stringIds = new StringBuilder();
		for (int i = 0; i < userIds.size(); i++) {
			if (i > 0) {
				stringIds.append(",");
			}
			stringIds.append(userIds.get(i).getId());
		}
		return stringIds.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected RowMapper<UserMessage> getRowMapper() {
		return new UserMessageRowMapper(this);
	}

	/**
	 * INSERTs a {@link UserMessage} entity.
	 * 
	 * @param message
	 *            UserMessage entity to insert, required.
	 */
	@Override
	public DataOperationResult<UserMessage> insert(final UserMessage message) {
		AjahUtils.requireParam(message, "message");
		AjahUtils.requireParam(this.jdbcTemplate, "this.jdbcTemplate");
		return new DataOperationResult<>(message, this.jdbcTemplate.update("INSERT INTO " + getTableName() + " (" + getSelectFields() + ") VALUES (?,?,?,?,?,?,?,?,?,?)",
				new Object[] { message.getId().getId(), DateUtils.safeToLong(message.getCreated()), message.getSender().getId(), fromUserIds(message.getTo()), fromUserIds(message.getCc()),
						fromUserIds(message.getBcc()), message.getSubject(), message.getBody(), message.getType().getId(), message.getStatus().getId() }));
	}
}
