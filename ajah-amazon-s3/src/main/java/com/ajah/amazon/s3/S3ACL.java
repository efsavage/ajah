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
package com.ajah.amazon.s3;

import org.jets3t.service.acl.AccessControlList;

/**
 * A wrapper for {@link AccessControlList}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public enum S3ACL {

	/**
	 * @see AccessControlList#REST_CANNED_AUTHENTICATED_READ
	 */
	AUTHENTICATED_READ(AccessControlList.REST_CANNED_AUTHENTICATED_READ),
	/**
	 * @see AccessControlList#REST_CANNED_PRIVATE
	 */
	PRIVATE(AccessControlList.REST_CANNED_PRIVATE),
	/**
	 * @see AccessControlList#REST_CANNED_PUBLIC_READ
	 */
	PUBLIC_READ(AccessControlList.REST_CANNED_PUBLIC_READ),
	/**
	 * @see AccessControlList#REST_CANNED_PUBLIC_READ_WRITE
	 */
	PUBLIC_READ_WRITE(AccessControlList.REST_CANNED_PUBLIC_READ_WRITE);

	private final AccessControlList jets3t;

	private S3ACL(final AccessControlList jets3t) {
		this.jets3t = jets3t;
	}

	/**
	 * Returns the Jets3t equivalent permission set.
	 * 
	 * @return the Jets3t equivalent permission set.
	 */
	public AccessControlList getJets3t() {
		return this.jets3t;
	}

}
