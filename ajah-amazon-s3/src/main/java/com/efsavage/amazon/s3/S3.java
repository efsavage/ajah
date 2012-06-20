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

/**
 * Class to streamline S3 operations.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class S3 {

	private static S3Client defaultClient = S3Client.getDefaultClient();

	/**
	 * Puts an object using the default client.
	 * 
	 * @see S3Client#getDefaultClient()
	 * 
	 * @param bucket
	 *            The bucket to put the object into, required.
	 * @param name
	 *            The name to store the object as, required.
	 * @param data
	 *            The data of the object.
	 * @throws S3Exception
	 *             If an error occurs storing the object.
	 */
	public static void put(final Bucket bucket, final String name, final String data) throws S3Exception {
		defaultClient.put(bucket, name, data);
	}

}
