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
package com.ajah.user.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.user.UserId;
import com.ajah.user.email.EmailId;
import com.ajah.user.info.UserInfo;
import com.ajah.user.info.UserInfoImpl;
import com.ajah.util.AjahUtils;
import com.ajah.util.data.Month;

/**
 * Data operations on the "user" table.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class UserInfoDao extends AbstractAjahDao<UserId, UserInfo> {

	private static final Logger log = Logger.getLogger(UserDao.class.getName());

	private static final String SELECT_FIELDS = "user_id, first_name, middle_name, last_name, birth_day, birth_month, birth_year, primary_email_id";

	private static final String TABLE_NAME = "user_info";

	private JdbcTemplate jdbcTemplate;

	/**
	 * Sets up a new JDBC template with the supplied data source.
	 * 
	 * @param dataSource
	 *            DataSource to use for a new JDBC template.
	 */
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	static final class UserInfoRowMapper implements RowMapper<UserInfo> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserInfo userInfo = new UserInfoImpl(new UserId(rs.getString("user_id")));
			userInfo.setFirstName(rs.getString("first_name"));
			userInfo.setMiddleName(rs.getString("middle_name"));
			userInfo.setLastName(rs.getString("last_name"));
			userInfo.setBirthDay(getInteger(rs, "birth_day"));
			userInfo.setBirthMonth(Month.get(rs.getInt("birth_month")));
			userInfo.setBirthYear(getInteger(rs, "birth_year"));
			userInfo.setPrimaryEmailId(rs.getString("primary_email_id") == null ? null : new EmailId(rs.getString("primary_email_id")));
			return userInfo;
		}
	}

	/**
	 * INSERTs a {@link UserInfo} entity.
	 * 
	 * @param userInfo
	 *            UserInfo entity to insert, required.
	 */
	public void insert(UserInfo userInfo) {
		AjahUtils.requireParam(userInfo, "userInfo");
		AjahUtils.requireParam(this.jdbcTemplate, "this.jdbcTemplate");
		this.jdbcTemplate
				.update("INSERT INTO user_info (user_id, first_name, middle_name, last_name, birth_day, birth_month, birth_year, primary_email_id) VALUES (?,?,?,?,?,?,?,?)",
						new Object[] { userInfo.getUserId().getId(), userInfo.getFirstName(), userInfo.getMiddleName(), userInfo.getLastName(),
								userInfo.getBirthDay(), userInfo.getBirthMonth() == null ? null : Integer.valueOf(userInfo.getBirthMonth().getId()),
								userInfo.getBirthYear(), userInfo.getPrimaryEmailId().getId() });
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getSelectFields() {
		return SELECT_FIELDS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected RowMapper<UserInfo> getRowMapper() {
		return new UserInfoRowMapper();
	}

}