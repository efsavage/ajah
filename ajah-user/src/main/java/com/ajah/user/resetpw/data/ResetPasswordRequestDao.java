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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ajah.user.UserId;
import com.ajah.user.data.UserDao;
import com.ajah.user.resetpw.ResetPasswordRequest;
import com.ajah.user.resetpw.ResetPasswordRequestId;
import com.ajah.user.resetpw.ResetPasswordRequestStatusImpl;
import com.ajah.util.AjahUtils;

/**
 * Handles data operations for {@link ResetPasswordRequest} entities.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Repository
public class ResetPasswordRequestDao {

	private static final Logger log = Logger.getLogger(UserDao.class.getName());

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

	/**
	 * INSERTs a {@link ResetPasswordRequest}.
	 * 
	 * @param resetPasswordRequest
	 *            The {@link ResetPasswordRequest} to save, required.
	 */
	public void insert(ResetPasswordRequest resetPasswordRequest) {
		AjahUtils.requireParam(resetPasswordRequest, "resetPasswordRequest");
		this.jdbcTemplate.update(
				"INSERT INTO pw_reset (pw_reset_id, user_id, created, code, status) VALUES (?,?,?,?,?)",
				new Object[] { resetPasswordRequest.getId().getId(), resetPasswordRequest.getUserId().getId(),
						Long.valueOf(resetPasswordRequest.getCreated().getTime() / 1000), Long.valueOf(resetPasswordRequest.getCode()),
						resetPasswordRequest.getStatus().getId() });
	}

	/**
	 * Finds a {@link ResetPasswordRequest} by it's "code" property.
	 * 
	 * @param code
	 *            The code to query on, required to be greater than zero.
	 * @return The {@link ResetPasswordRequest}, if found.
	 */
	public ResetPasswordRequest findByCode(long code) {
		AjahUtils.requireParam(code, "code", 1);
		try {
			return this.jdbcTemplate.queryForObject("SELECT " + ResetPasswordRequestRowMapper.RPR_FIELDS + " FROM pw_reset WHERE code = ?",
					new Object[] { Long.valueOf(code) }, new ResetPasswordRequestRowMapper());
		} catch (EmptyResultDataAccessException e) {
			log.fine(e.getMessage());
			return null;
		}
	}

	static final class ResetPasswordRequestRowMapper implements RowMapper<ResetPasswordRequest> {

		public static final String RPR_FIELDS = "pw_reset_id, user_id, created, code, redeemed, status";

		/**
		 * {@inheritDoc}
		 */
		@Override
		public ResetPasswordRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
			ResetPasswordRequest rpr = new ResetPasswordRequest();
			rpr.setId(new ResetPasswordRequestId(rs.getString("pw_reset_id")));
			rpr.setUserId(new UserId(rs.getString("user_id")));
			rpr.setCreated(new Date(rs.getLong("created") * 1000));
			rpr.setCode(rs.getLong("code"));
			rpr.setRedeemed(new Date(rs.getLong("redeemed") * 1000));
			rpr.setStatus(ResetPasswordRequestStatusImpl.get(rs.getString("status")));
			return rpr;
		}

	}

	/**
	 * UPDATEs a {@link ResetPasswordRequest}.
	 * 
	 * @param resetPasswordRequest
	 *            The {@link ResetPasswordRequest} to save, required.
	 */
	public void update(ResetPasswordRequest resetPasswordRequest) {
		AjahUtils.requireParam(resetPasswordRequest, "resetPasswordRequest");
		this.jdbcTemplate
				.update("UPDATE pw_reset SET user_id = ?, created = ?, code = ?, status = ? WHERE pw_reset_id = ?",
						new Object[] { resetPasswordRequest.getUserId().getId(), Long.valueOf(resetPasswordRequest.getCreated().getTime() / 1000),
								Long.valueOf(resetPasswordRequest.getCode()), resetPasswordRequest.getStatus().getId(),
								resetPasswordRequest.getId().getId() });
	}

}