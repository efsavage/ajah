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

import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.user.achievement.data.AchievementUserManager;
import com.ajah.user.data.UserManager;

/**
 * Interface that all classes specified by {@link Achievement#getAnalyzer()}
 * should implement. These classes will be executed when
 * {@link AchievementUserManager#checkAcheivements(User, String)} is fired.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public interface AchievementAnalyzer {

	/**
	 * Analyzes whatever is necessary to see if an {@link Achievement} needs to
	 * be updated for a given user. Better for events that are likely or certain
	 * to be completed.
	 * 
	 * @param user
	 *            The user to analyze, required.
	 * @param achievement
	 *            The achievement to analyze, required.
	 * @param achievementUser
	 *            The existing achievementUser, required.
	 * @return true if the AchievementUser was updated, otherwise false.
	 */
	public AchievementCompletion analyze(final User user, final Achievement achievement, final AchievementUser achievementUser);

	/**
	 * Analyzes whatever is necessary to see if an {@link Achievement} needs to
	 * be updated for a given user. Does not require a loaded user, as that may
	 * only be necessary if the achievement is actually completed. Better for
	 * rare events.
	 * 
	 * @param userId
	 *            The ID of the user to analyze, required.
	 * @param userManager
	 *            The manager by which to load the user if necessary.
	 * @param achievement
	 *            The achievement to analyze, required.
	 * @param achievementUser
	 *            The existing achievementUser, required.
	 * @return true if the AchievementUser was updated, otherwise false.
	 */
	public AchievementCompletion analyze(final UserId userId, final UserManager userManager, final Achievement achievement, final AchievementUser achievementUser);

}
