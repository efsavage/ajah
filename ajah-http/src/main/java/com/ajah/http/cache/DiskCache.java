package com.ajah.http.cache;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import lombok.extern.java.Log;

import com.ajah.crypto.SHA;
import com.ajah.http.Http;
import com.ajah.http.err.NotFoundException;
import com.ajah.http.err.UnexpectedResponseCode;
import com.ajah.util.config.Config;
import com.ajah.util.io.file.FileHashUtils;
import com.ajah.util.io.file.FileUtils;

@Log
public class DiskCache implements HttpCache {

	@Override
	public String get(final URI uri) throws IOException, UnexpectedResponseCode, NotFoundException {
		return new String(getBytes(uri));
	}

	@Override
	public byte[] getBytes(final String uri) throws IOException, NotFoundException, UnexpectedResponseCode, URISyntaxException {
		return getBytes(new URI(uri));
	}

	@Override
	public byte[] getBytes(final URI uri) throws IOException, NotFoundException, UnexpectedResponseCode {
		final String path = FileHashUtils.getHashedFileName(SHA.sha1Hex(uri.toString()), 3, 2);
		final File cacheDir = new File(Config.i.get("ajah.http.cache.dir"));
		final File f = new File(cacheDir, path);

		byte[] data = null;
		if (!f.exists()) {
			log.info("Cache miss; getting " + uri);
			data = Http.getBytes(uri);
			FileUtils.write(f, data);
		} else {
			data = FileUtils.readFileAsBytes(f);
		}

		return data;
	}
}
