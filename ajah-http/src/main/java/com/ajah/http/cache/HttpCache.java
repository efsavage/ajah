package com.ajah.http.cache;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.ajah.http.err.NotFoundException;
import com.ajah.http.err.UnexpectedResponseCode;

public interface HttpCache {

	public byte[] getBytes(final URI uri) throws IOException, NotFoundException, UnexpectedResponseCode;

	public String get(final URI uri) throws IOException, UnexpectedResponseCode, NotFoundException;

	public byte[] getBytes(final String uri) throws IOException, NotFoundException, UnexpectedResponseCode, URISyntaxException;
}
