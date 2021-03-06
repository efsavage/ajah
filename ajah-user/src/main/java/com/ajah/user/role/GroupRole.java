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
package com.ajah.user.role;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import com.ajah.user.group.GroupId;
import com.ajah.util.Identifiable;

import lombok.Data;

@Data
@Table(name = "group__role")
public class GroupRole implements Identifiable<GroupRoleId> {

	private GroupRoleId id;
	private GroupId groupId;
	private RoleId roleId;
	private GroupRoleStatus status;
	private GroupRoleType type;
	private Date created;

	@Transient
	private Role role;
	
}
