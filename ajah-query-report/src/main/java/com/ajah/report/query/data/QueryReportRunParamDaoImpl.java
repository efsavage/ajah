/*
 *  Copyright 2015 Eric F. Savage, code@efsavage.com
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
package com.ajah.report.query.data;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ajah.report.query.QueryReportRunParam;
import com.ajah.report.query.QueryReportRunParamId;
import com.ajah.report.query.QueryReportRunParamStatus;
import com.ajah.report.query.QueryReportRunParamType;
import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.util.StringUtils;

/**
 * MySQL-based implementation of {@link QueryReportRunParamDao}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class QueryReportRunParamDaoImpl extends AbstractAjahDao<QueryReportRunParamId, QueryReportRunParam, QueryReportRunParam> implements QueryReportRunParamDao {

	@Override
	public List<QueryReportRunParam> list(String search, QueryReportRunParamType type, QueryReportRunParamStatus status, String sort, Order order, int page, int count) throws DataOperationException {
		Criteria criteria = new Criteria();
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
	 * @see com.ajah.report.query.data.QueryReportRunParamDao#count(com.ajah.report.query.QueryReportRunParamType,
	 *      com.ajah.report.query.QueryReportRunParamStatus)
	 */
	@Override
	public int count(QueryReportRunParamType type, QueryReportRunParamStatus status) throws DataOperationException {
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
	 * @see com.ajah.report.query.data.QueryReportRunParamDao#searchCount(String,
	 *      QueryReportRunParamType, QueryReportRunParamStatus)
	 */
	@Override
	public int searchCount(String search, QueryReportRunParamType type, QueryReportRunParamStatus status) throws DataOperationException {
		Criteria criteria = new Criteria();
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

}
