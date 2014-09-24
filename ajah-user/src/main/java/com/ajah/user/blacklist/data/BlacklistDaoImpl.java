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

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.blacklist.Blacklist;
import com.ajah.user.blacklist.BlacklistId;
import com.ajah.user.blacklist.BlacklistStatus;
import com.ajah.user.blacklist.BlacklistType;
import com.ajah.util.StringUtils;

/**
 * MySQL-based implementation of {@link BlacklistDao}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class BlacklistDaoImpl extends AbstractAjahDao<BlacklistId, Blacklist, Blacklist> implements BlacklistDao {

	/**
	 * @see com.ajah.user.blacklist.data.BlacklistDao#list(String, String,
	 *      BlacklistType, BlacklistStatus, long, long)
	 */
	@Override
	public List<Blacklist> list(String part1, String part2, BlacklistType type, BlacklistStatus status, long page, long count) throws DataOperationException {
		Criteria criteria = new Criteria();
		if (part1 != null) {
			criteria.reverseLike("part_1", part1);
		}
		if (part2 != null) {
			criteria.reverseLike("part_2", part2);
		}
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.list(criteria.offset(page * count).rows(count).orderBy("created_date", Order.DESC));
	}

	/**
	 * @see com.ajah.user.blacklist.data.BlacklistDao#count(BlacklistType,
	 *      BlacklistStatus)
	 */
	@Override
	public long count(BlacklistType type, BlacklistStatus status) throws DataOperationException {
		Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.count(criteria);
	}

	/**
	 * @see com.ajah.user.blacklist.data.BlacklistDao#searchCount(String search)
	 */
	@Override
	public int searchCount(String search) throws DataOperationException {
		Criteria criteria = new Criteria();
		if (!StringUtils.isBlank(search)) {
			criteria.like("name", "%" + search.replaceAll("\\*", "%") + "%");
		}
		return super.count(criteria);
	}

}
