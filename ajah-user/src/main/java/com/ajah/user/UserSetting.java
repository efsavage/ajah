/*
 *  Copyright 2013-2016 Eric F. Savage, code@efsavage.com
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
package com.ajah.user;

import java.util.Date;

import com.ajah.util.Identifiable;
import com.ajah.util.StringUtils;

import lombok.Data;

@Data
public class UserSetting implements Identifiable<UserSettingId> {

	private UserSettingId id;
	private UserId userId;
	private String name;
	private String value;
	private UserSettingStatus status;
	private UserSettingType type;
	private Date created;

	/**
	 * Returns a boolean value based on {@link #getValue()}.
	 * 
	 * @param defaultValue
	 * @return The value as a boolean, or the defaultValue.
	 */
	public boolean getBoolean(final boolean defaultValue) {
		if ("true".equals(this.value)) {
			return true;
		}
		return defaultValue;
	}

	/**
	 * Returns whether the value is empty (null or empty string) or not.
	 * 
	 * @return true if the value is null or an empty string, otherwise false.
	 */
	public boolean isEmpty() {
		return StringUtils.isBlank(this.value);
	}
}
