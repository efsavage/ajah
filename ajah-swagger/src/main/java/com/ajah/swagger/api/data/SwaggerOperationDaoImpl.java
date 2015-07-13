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
package com.ajah.swagger.api.data;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ajah.spring.jdbc.AbstractAjahDao;
import com.ajah.spring.jdbc.criteria.Criteria;
import com.ajah.spring.jdbc.criteria.Order;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.swagger.api.SwaggerApiId;
import com.ajah.swagger.api.SwaggerOperation;
import com.ajah.swagger.api.SwaggerOperationId;
import com.ajah.swagger.api.SwaggerOperationStatus;
import com.ajah.swagger.api.SwaggerOperationType;
import com.ajah.util.StringUtils;

/**
 * MySQL-based implementation of {@link SwaggerOperationDao}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class SwaggerOperationDaoImpl extends AbstractAjahDao<SwaggerOperationId, SwaggerOperation, SwaggerOperation> implements SwaggerOperationDao {

	@Override
	public List<SwaggerOperation> list(SwaggerApiId swaggerApiId, String search, SwaggerOperationType type, SwaggerOperationStatus status, String sort, Order order, int page, int count)
			throws DataOperationException {
		Criteria criteria = new Criteria().eq(swaggerApiId);
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
	 * @see com.ajah.swagger.api.data.SwaggerOperationDao#count(com.ajah.swagger.api.SwaggerOperationType,
	 *      com.ajah.swagger.api.SwaggerOperationStatus)
	 */
	@Override
	public int count(SwaggerApiId swaggerApiId, SwaggerOperationType type, SwaggerOperationStatus status) throws DataOperationException {
		Criteria criteria = new Criteria().eq(swaggerApiId);
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.count(criteria);
	}

	/**
	 * @see com.ajah.swagger.api.data.SwaggerOperationDao#searchCount(String,
	 *      SwaggerOperationType, SwaggerOperationStatus)
	 */
	@Override
	public int searchCount(SwaggerApiId swaggerApiId, String search, SwaggerOperationType type, SwaggerOperationStatus status) throws DataOperationException {
		Criteria criteria = new Criteria().eq(swaggerApiId);
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
