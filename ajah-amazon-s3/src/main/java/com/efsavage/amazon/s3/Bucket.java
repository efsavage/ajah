/*
 *  Copyright 2012 Eric F. Savage, code@efsavage.com
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
package com.efsavage.amazon.s3;

import lombok.Data;

import com.ajah.util.AjahUtils;
import com.ajah.util.FromStringable;
import com.ajah.util.ToStringable;

/**
 * An S3 Bucket.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
public class Bucket implements ToStringable, FromStringable {

	private final String name;

	/**
	 * Public constructor.
	 * 
	 * @param name
	 *            The name of the bucket, required.
	 */
	public Bucket(String name) {
		AjahUtils.requireParam(name, "name");
		this.name = name;
	}

	/**
	 * Returns the name of the bucket.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.name;
	}

}