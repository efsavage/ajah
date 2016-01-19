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
import com.ajah.swagger.api.SwaggerOperationId;
import com.ajah.swagger.api.SwaggerResponse;
import com.ajah.swagger.api.SwaggerResponseId;
import com.ajah.swagger.api.SwaggerResponseStatus;
import com.ajah.swagger.api.SwaggerResponseType;
import com.ajah.util.StringUtils;

/**
 * MySQL-based implementation of {@link SwaggerResponseDao}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class SwaggerResponseDaoImpl extends AbstractAjahDao<SwaggerResponseId, SwaggerResponse, SwaggerResponse>implements SwaggerResponseDao {

	@Override
	public List<SwaggerResponse> list(SwaggerOperationId swaggerOperationId, String search, SwaggerResponseType type, SwaggerResponseStatus status, String sort, Order order, int page, int count)
			throws DataOperationException {
		Criteria criteria = new Criteria().eq(swaggerOperationId);
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
	 * @see com.ajah.swagger.api.data.SwaggerResponseDao#count(SwaggerOperationId,
	 *      SwaggerResponseType, SwaggerResponseStatus)
	 */
	@Override
	public int count(SwaggerOperationId swaggerOperationId, SwaggerResponseType type, SwaggerResponseStatus status) throws DataOperationException {
		Criteria criteria = new Criteria().eq(swaggerOperationId);
		if (type != null) {
			criteria.eq("type", type);
		}
		if (status != null) {
			criteria.eq("status", status);
		}
		return super.count(criteria);
	}

	/**
	 * @see com.ajah.swagger.api.data.SwaggerResponseDao#searchCount(SwaggerOperationId,
	 *      String, SwaggerResponseType, SwaggerResponseStatus)
	 */
	@Override
	public int searchCount(SwaggerOperationId swaggerOperationId, String search, SwaggerResponseType type, SwaggerResponseStatus status) throws DataOperationException {
		Criteria criteria = new Criteria().eq(swaggerOperationId);
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
