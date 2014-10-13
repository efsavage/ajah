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
package com.ajah.user.invitation.data;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.invitation.Invitation;
import com.ajah.user.invitation.InvitationId;
import com.ajah.user.invitation.InvitationStatus;
import com.ajah.user.invitation.InvitationType;
import com.ajah.util.StringUtils;

/**
 * MySQL-based implementation of {@link InvitationDao}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class InvitationDaoImpl extends AbstractAjahDao<InvitationId, Invitation, Invitation> implements InvitationDao {

	@Override
	public List<Invitation> list(InvitationType type, InvitationStatus status, long page, long count) throws DataOperationException {
		Criteria criteria = new Criteria();
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.list(criteria.offset(page * count).rows(count).orderBy("created_date", Order.ASC));
	}

	/**
	 * @see com.ajah.user.invitation.data.InvitationDao#count(com.ajah.user.invitation.InvitationType,
	 *      com.ajah.user.invitation.InvitationStatus)
	 */
	@Override
	public long count(InvitationType type, InvitationStatus status) throws DataOperationException {
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
	 * @see com.ajah.user.invitation.data.InvitationDao#searchCount(String
	 *      search)
	 */
	@Override
	public int searchCount(String search) throws DataOperationException {
		Criteria criteria = new Criteria();
		if (!StringUtils.isBlank(search)) {
			criteria.like("address", "%" + search.replaceAll("\\*", "%") + "%");
		}
		return super.count(criteria);
	}

	/**
	 * @see com.ajah.user.invitation.data.InvitationDao#findByReference(String)
	 */
	@Override
	public Invitation findByReference(String reference) throws DataOperationException {
		return super.find("reference", reference);
	}

}
