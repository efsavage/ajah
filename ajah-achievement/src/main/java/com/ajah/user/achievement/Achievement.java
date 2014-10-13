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

import javax.persistence.Transient;

import lombok.Data;

import com.ajah.user.User;
import com.ajah.user.achievement.data.AchievementUserManager;
import com.ajah.util.Identifiable;
import com.ajah.util.text.BigDecimalFormat;

/**
 * A achievement is a badge that can be awarded to a {@link User} for completing
 * a task or hitting a milestone. The general flow will be that when an
 * application event occurs, it will call
 * {@link AchievementUserManager#checkAcheivements(User,String)} which will
 * analyze and update the available Achievements.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Data
public class Achievement implements Identifiable<AchievementId> {

	private AchievementId id;
	private String name;
	/**
	 * The base string to use for icons, images, CSS classes, etc.
	 */
	private String code;
	private String description;
	BigDecimal targetValue;
	BigDecimalFormat valueFormat;
	private AchievementStatus status;
	private AchievementType type;
	private Date created;
	private String analyzer;

	@Transient
	private AchievementAnalyzer achievementAnalyzer;

}
