/*
 *  Copyright 2015-2016 Eric F. Savage, code@efsavage.com
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
package com.ajah.user.role.data;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.group.GroupId;
import com.ajah.user.role.GroupRole;
import com.ajah.user.role.GroupRoleId;
import com.ajah.user.role.GroupRoleStatus;
import com.ajah.user.role.GroupRoleType;
import com.ajah.util.StringUtils;

/**
 * MySQL-based implementation of {@link GroupRoleDao}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>,
 *         <a href="https://github.com/efsavage">github.com/efsavage</a>.
 *         <a href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Repository
public class GroupRoleDaoImpl extends AbstractAjahDao<GroupRoleId, GroupRole, GroupRole> implements GroupRoleDao {

	@Override
	public List<GroupRole> list(GroupId groupId, String search, GroupRoleType type, GroupRoleStatus status, String sort, Order order, int page, int count) throws DataOperationException {
		Criteria criteria = new Criteria();
		if (groupId != null) {
			criteria.eq(groupId);
		}
		if (!StringUtils.isBlank(search)) {
			final String pattern = "%" + search.replaceAll("\\*", "%") + "%";
			criteria.like("name", pattern);
		}
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		if (!StringUtils.isBlank(sort)) {
			criteria.orderBy(sort, order == null ? Order.ASC : order);
		}
		return super.list(criteria.offset(page * count).rows(count));
	}

	/**
	 * @see com.ajah.user.role.data.GroupRoleDao#count(com.ajah.user.role.GroupRoleType,
	 *      com.ajah.user.role.GroupRoleStatus)
	 */
	@Override
	public int count(GroupId groupId, GroupRoleType type, GroupRoleStatus status) throws DataOperationException {
		Criteria criteria = new Criteria();
		if (groupId != null) {
			criteria.eq(groupId);
		}
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.count(criteria);
	}

	/**
	 * @see com.ajah.user.role.data.GroupRoleDao#searchCount(String,
	 *      GroupRoleType, GroupRoleStatus)
	 */
	@Override
	public int searchCount(GroupId groupId, String search, GroupRoleType type, GroupRoleStatus status) throws DataOperationException {
		Criteria criteria = new Criteria();
		if (groupId != null) {
			criteria.eq(groupId);
		}
		if (!StringUtils.isBlank(search)) {
			criteria.like("name", "%" + search.replaceAll("\\*", "%") + "%");
		}
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.count(criteria);
	}

	@Override
	public List<GroupRole> list(GroupId groupId, GroupRoleStatus status) throws DataOperationException {
		Criteria criteria = new Criteria();

		criteria.eq(groupId);

		if (status != null) {
			criteria.eq("status", status);
		}

		return super.list(criteria);
	}

}
