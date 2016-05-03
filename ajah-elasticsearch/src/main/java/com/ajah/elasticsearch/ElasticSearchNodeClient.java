/*
 *  Copyright 2013 Eric F. Savage, code@efsavage.com
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
package com.ajah.elasticsearch;

import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

import com.ajah.util.Identifiable;
import com.ajah.util.StringUtils;

import lombok.extern.java.Log;

/**
 * A base client that allows for easily indexing and searching documents. Does
 * not support cross-index, multi-type searches.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <K>
 *            The primary key field.
 * @param <T>
 *            The type of the object stored/returned.
 * @param <C>
 *            The concrete class of the object stored/returned.
 */
@Log
public abstract class ElasticSearchNodeClient<K extends Comparable<K>, T extends Identifiable<K>, C extends T> extends AbstractElasticSearchNativeClient<K, T, C> {

	private Node node;

	/**
	 * Public constructor with data set to false and no additional
	 * configurations.
	 */
	public ElasticSearchNodeClient() {
		this(false);
	}

	/**
	 * Public constructor with configurable data parameter and all other values
	 * set to defaults.
	 * 
	 * @param data
	 *            Should this client use a data node?
	 */
	public ElasticSearchNodeClient(final boolean data) {
		this(data, null, null, null);
	}

	/**
	 * @param data
	 *            Should this client use a data node?
	 * @param typeName
	 *            The name of the type. If null it will attempt to infer the
	 *            type from the class parameters.
	 * @param indexName
	 *            The name of the index. If null it will use the type.
	 * @param clusterName
	 *            The name of the cluster to use. If null it will use the type.
	 */
	public ElasticSearchNodeClient(final boolean data, final String typeName, final String indexName, final String clusterName) {
		// Derive the cluster, index, type name if needed
		if (StringUtils.isBlank(typeName)) {
			final String simpleName = getTargetClass().getSimpleName();
			this.type = StringUtils.splitCamelCase(simpleName).replaceAll("\\W+", "_").toLowerCase();
		} else {
			this.type = typeName;
		}
		log.finest("Type is: " + this.type);
		if (StringUtils.isBlank(indexName)) {
			this.index = this.type;
		} else {
			this.index = indexName;
		}
		log.fine("Index is: " + this.index);
		if (clusterName == null) {
			this.clusterName = this.type;
		} else {
			this.clusterName = clusterName;
		}
		log.fine("Cluster name is: " + this.clusterName);

		this.node = NodeBuilder.nodeBuilder().clusterName(this.clusterName).data(data).node();
		this.client = this.node.client();
		log.fine("Waiting for green status");
		this.client.admin().cluster().prepareHealth().setWaitForGreenStatus().execute().actionGet();
		log.fine("Green status achieved");
	}

	/**
	 * Closes the node.
	 * 
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() {
		this.node.close();
		super.close();
	}

}
