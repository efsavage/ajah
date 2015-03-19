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

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.util.Identifiable;

/**
 * Represents the current state of an individual {@link User}'s progress on an
 * {@link Achievement}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Data
@Table(name = "achievement__user")
public class AchievementUser implements Identifiable<AchievementUserId> {

	private AchievementUserId id;
	private AchievementId achievementId;
	private UserId userId;
	private BigDecimal currentValue;
	private BigDecimal targetValue;
	private BigDecimal minValue;
	private BigDecimal maxValue;
	private AchievementUserStatus status;
	private AchievementUserType type;
	private Date completed;
	private Date modified;
	private Date created;
	private Date lastAnalyzed;

	@Transient
	Achievement achievement;

	@Transient
	User user;

}
