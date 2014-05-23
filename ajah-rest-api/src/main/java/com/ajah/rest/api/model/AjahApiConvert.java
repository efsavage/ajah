/*
 *  Copyright 2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.rest.api.model;

import com.ajah.rest.api.model.relay.IdentifiableEnumRelay;
import com.ajah.util.IdentifiableEnum;

/**
 * Converts any enum implementing {@link IdentifiableEnum} to an
 * {@link IdentifiableEnumRelay}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class AjahApiConvert {

	/**
	 * Converts any enum implementing {@link IdentifiableEnum} to an
	 * {@link IdentifiableEnumRelay}.
	 * 
	 * @param identifiableEnum
	 *            The enum to convert. If null will return null.
	 * @return The converted enum, or null.
	 */
	public static <K extends Comparable<K>> IdentifiableEnumRelay<K> convert(final IdentifiableEnum<K> identifiableEnum) {
		if (identifiableEnum == null) {
			return null;
		}
		final IdentifiableEnumRelay<K> relay = new IdentifiableEnumRelay<K>();
		relay.id = identifiableEnum.getId();
		relay.code = identifiableEnum.getCode();
		relay.name = identifiableEnum.getName();
		return relay;
	}

}
