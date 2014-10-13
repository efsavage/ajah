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
package com.ajah.user.achievement;

import java.util.Date;

import lombok.Data;

import com.ajah.util.Identifiable;

/**
 * A tag for an achievement, to aid in determining which to analyze upon certain
 * events happening. For example, if inviting a friend is an achievement, it
 * might be tagged "invite", and the invitation process will then process all
 * achievements tagged as "invite".
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
public class AchievementTag implements Identifiable<AchievementTagId> {

	private AchievementTagId id;
	private String name;
	private AchievementTagStatus status;
	private AchievementTagType type;
	private Date created;

}
