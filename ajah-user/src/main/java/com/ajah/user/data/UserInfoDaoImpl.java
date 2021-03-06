/*
 *  Copyright 2011-2014 Eric F. Savage, code@efsavage.com
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
import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ajah.lang.MapMap;
import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.UserId;
import com.ajah.user.info.UserInfo;
import com.ajah.user.info.UserInfoImpl;
import com.ajah.util.data.Month;

/**
 * Data operations on the "user_info" table.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class UserInfoDaoImpl extends AbstractAjahDao<UserId, UserInfo, UserInfoImpl> implements UserInfoDao {

	@Override
	public MapMap<LocalDate, String, Integer> getSourceCounts() {
		final String sql = "SELECT FROM_UNIXTIME(created_date/1000,'%Y-%m-%d') as date,source,count(*) as total FROM user_info GROUP BY FROM_UNIXTIME(created_date/1000,'%Y-%m-%d'),source";
		sqlLog.finest(sql);
		return super.jdbcTemplate.query(sql, new ResultSetExtractor<MapMap<LocalDate, String, Integer>>() {

			@Override
			public MapMap<LocalDate, String, Integer> extractData(final ResultSet rs) throws SQLException, DataAccessException {
				final MapMap<LocalDate, String, Integer> mapMap = new MapMap<>();
				while (rs.next()) {
					final String source = rs.getString("source") == null ? "mg" : rs.getString("source");
					mapMap.put(LocalDate.parse(rs.getString("date")), source, Integer.valueOf(rs.getInt("total")));
				}
				return mapMap;
			}

		});
	}

	@Override
	public List<UserInfo> listBySource(String source, int page, int count) throws DataOperationException {
		return super.list(new Criteria().eq("source", source).offset(page * count).rows(count).orderBy("created_date", Order.ASC));
	}

	@Override
	public List<UserInfo> list(String firstName, String lastName, Integer birthYear, Month birthMonth, Integer birthDay) throws DataOperationException {
		return super.list(new Criteria().eq("first_name", firstName).eq("last_name", lastName).eq("birth_year", birthYear.longValue()).eq("birth_month", birthMonth).eq("birth_day",
				birthDay.longValue())
				.orderBy("created_date", Order.ASC));
	}
}
