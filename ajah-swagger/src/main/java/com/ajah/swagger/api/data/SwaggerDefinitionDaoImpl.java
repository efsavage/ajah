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
import com.ajah.swagger.api.SwaggerDefinition;
import com.ajah.swagger.api.SwaggerDefinitionId;
import com.ajah.swagger.api.SwaggerDefinitionStatus;
import com.ajah.swagger.api.SwaggerDefinitionType;
import com.ajah.util.StringUtils;

/**
 * MySQL-based implementation of {@link SwaggerDefinitionDao}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Repository
public class SwaggerDefinitionDaoImpl extends AbstractAjahDao<SwaggerDefinitionId, SwaggerDefinition, SwaggerDefinition> implements SwaggerDefinitionDao {

	@Override
	public List<SwaggerDefinition> list(SwaggerApiId swaggerApiId, String search, SwaggerDefinitionType type, SwaggerDefinitionStatus status, String sort, Order order, int page, int count)
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
	 * @see com.ajah.swagger.api.data.SwaggerDefinitionDao#count(com.ajah.swagger.api.SwaggerDefinitionType,
	 *      com.ajah.swagger.api.SwaggerDefinitionStatus)
	 */
	@Override
	public int count(SwaggerApiId swaggerApiId, SwaggerDefinitionType type, SwaggerDefinitionStatus status) throws DataOperationException {
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
	 * @see com.ajah.swagger.api.data.SwaggerDefinitionDao#searchCount(String,
	 *      SwaggerDefinitionType, SwaggerDefinitionStatus)
	 */
	@Override
	public int searchCount(SwaggerApiId swaggerApiId, String search, SwaggerDefinitionType type, SwaggerDefinitionStatus status) throws DataOperationException {
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
