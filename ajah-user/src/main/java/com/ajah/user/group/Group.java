/*
 *  Copyright 2015-2016 Eric F. Savage, code@efsavage.com
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
package com.ajah.user.group;

import java.util.Date;

import com.ajah.util.Identifiable;

import lombok.Data;

/**
 * A Group of Users. A user can belong to any number of groups.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>,
 *         <a href="https://github.com/efsavage">github.com/efsavage</a>.
 *         <a href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Data
public class Group implements Identifiable<GroupId> {

	private GroupId id;
	private String name;
	private GroupStatus status;
	private GroupType type;
	private Date created;

}
