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
package com.ajah.user;

import java.util.Date;

import lombok.Data;

import com.ajah.util.Identifiable;

@Data
public class UserSetting implements Identifiable<UserSettingId> {

	private UserSettingId id;
	private UserId userId;
	private String name;
	private String value;
	private UserSettingStatus status;
	private UserSettingType type;
	private Date created;

}
