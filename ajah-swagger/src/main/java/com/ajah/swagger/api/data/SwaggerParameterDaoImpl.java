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
import com.ajah.swagger.api.SwaggerParameter;
import com.ajah.swagger.api.SwaggerParameterId;
import com.ajah.swagger.api.SwaggerParameterStatus;
import com.ajah.swagger.api.SwaggerParameterType;
import com.ajah.util.StringUtils;

/**
 * MySQL-based implementation of {@link SwaggerParameterDao}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class SwaggerParameterDaoImpl extends AbstractAjahDao<SwaggerParameterId, SwaggerParameter, SwaggerParameter>implements SwaggerParameterDao {

	@Override
	public List<SwaggerParameter> list(SwaggerOperationId swaggerOperationId, String search, SwaggerParameterType type, SwaggerParameterStatus status, String sort, Order order, int page, int count)
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
	 * @see com.ajah.swagger.api.data.SwaggerParameterDao#count(SwaggerOperationId,
	 *      SwaggerParameterType, SwaggerParameterStatus)
	 */
	@Override
	public int count(SwaggerOperationId swaggerOperationId, SwaggerParameterType type, SwaggerParameterStatus status) throws DataOperationException {
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
	 * @see com.ajah.swagger.api.data.SwaggerParameterDao#searchCount(SwaggerOperationId,
	 *      String, SwaggerParameterType, SwaggerParameterStatus)
	 */
	@Override
	public int searchCount(SwaggerOperationId swaggerOperationId, String search, SwaggerParameterType type, SwaggerParameterStatus status) throws DataOperationException {
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
