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

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.util.StringUtils;
import com.ajah.report.query.QueryReportRunStep;
import com.ajah.report.query.QueryReportRunStepId;
import com.ajah.report.query.QueryReportRunStepStatus;
import com.ajah.report.query.QueryReportRunStepType;

/**
 * MySQL-based implementation of {@link QueryReportRunStepDao}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class QueryReportRunStepDaoImpl extends AbstractAjahDao<QueryReportRunStepId, QueryReportRunStep, QueryReportRunStep> implements QueryReportRunStepDao {

	@Override
	public List<QueryReportRunStep> list(String search, QueryReportRunStepType type, QueryReportRunStepStatus status, String sort, Order order, int page, int count) throws DataOperationException {
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
	 * @see com.ajah.report.query.data.QueryReportRunStepDao#count(com.ajah.report.query.QueryReportRunStepType,
	 *      com.ajah.report.query.QueryReportRunStepStatus)
	 */
	@Override
	public int count(QueryReportRunStepType type, QueryReportRunStepStatus status) throws DataOperationException {
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
	 * @see com.ajah.report.query.data.QueryReportRunStepDao#searchCount(String,
	 *      QueryReportRunStepType, QueryReportRunStepStatus)
	 */
	@Override
	public int searchCount(String search, QueryReportRunStepType type, QueryReportRunStepStatus status) throws DataOperationException {
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
