package com.ajah.elasticsearch.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import lombok.extern.java.Log;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;

import com.ajah.elasticsearch.ElasticSearchException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Log
public class RestClient<C> implements AutoCloseable {

	private ObjectMapper mapper = new ObjectMapper();

	public RestClient(String hostname, int port) {
	}

	public void close() throws IOException {
	}

	public SearchRequestBuilder prepareSearch(String index) {
		throw new NotImplementedException("Indexing not implemented yet");
	}

	public RestSearchResponse<C> search(String hostname, int port, String index, String type, String body, Class<?> clazz, CloseableHttpClient http) throws ElasticSearchException {
		try {
			HttpGetWithEntity get = new HttpGetWithEntity();
			get.setURI(new URI("http://" + hostname + ":" + port + "/" + index + "/" + type));
			get.setEntity(new StringEntity(body));
			log.fine(get.getURI().toString());
			try (CloseableHttpResponse response = http.execute(get)) {
				HttpEntity entity = response.getEntity();
				// EntityUtils.consume(entity);
				// for (final SearchHit hit : response.getHits()) {
				// final C result =
				// this.mapper.readValue(hit.getSourceAsString(),
				// getTargetClass());
				// results.add(result);
				// }
				log.fine(EntityUtils.toString(entity));
				return null;
				// return (RestGetResponse<C>)
				// mapper.readValue(entity.getContent(),
				// clazz);
			} catch (IOException e) {
				throw new ElasticSearchException(e);
			}
		} catch (URISyntaxException e) {
			throw new ElasticSearchException(e);
		} catch (UnsupportedEncodingException e) {
			throw new ElasticSearchException(e);
		}
	}
}
