/*
 *  Copyright 2015 Eric F. Savage, code@efsavage.com
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
package com.ajah.user.memo;

import java.util.Date;

import javax.persistence.Transient;

import lombok.Data;

import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.user.info.UserInfo;
import com.ajah.util.Identifiable;

@Data
public class UserMemo implements Identifiable<UserMemoId> {

	private UserMemoId id;
	private UserId userId;
	private UserId authorUserId;
	private String name;
	private String memo;
	private UserMemoStatus status;
	private UserMemoType type;
	private Date created;

	@Transient
	private User user;

	@Transient
	private UserInfo userInfo;

	@Transient
	private User authorUser;

	@Transient
	private UserInfo authorUserInfo;

	@Transient
	private String authorAvatarUrl;

}
