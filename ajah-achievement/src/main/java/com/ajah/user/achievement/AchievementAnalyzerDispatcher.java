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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import lombok.extern.java.Log;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.ajah.lang.ListMap;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.user.UserId;
import com.ajah.user.achievement.data.AchievementManager;
import com.ajah.user.achievement.data.AchievementTagManager;
import com.ajah.user.achievement.data.AchievementUserManager;
import com.ajah.util.AjahUtils;
import com.ajah.util.StringUtils;

/**
 * Determines and executes the propert {@link AchievementAnalyzer}s for a given
 * user and tags.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
@Service
@Log
public class AchievementAnalyzerDispatcher implements ApplicationContextAware {

	@Autowired
	AchievementManager achievementManager;

	@Autowired
	AchievementTagManager achievementTagManager;

	@Autowired
	AchievementUserManager achievementUserManager;

	ListMap<String, Achievement> tagCache = null;

	private ApplicationContext applicationContext;

	private synchronized void init() throws DataOperationException {
		ListMap<String, Achievement> myTagCache = new ListMap<>();
		List<Achievement> achievements = null;
		while (achievements == null || achievements.size() == 100) {
			achievements = this.achievementManager.list(null, AchievementStatus.ACTIVE, 0, 100);
			for (Achievement achievement : achievements) {
				log.fine(achievement.getName() + ": " + achievement.getAnalyzer());
				if (StringUtils.isBlank(achievement.getAnalyzer())) {
					log.warning("No analyzer for achievement " + achievement.getName());
					continue;
				}
				try {
					AchievementAnalyzer analyzer = (AchievementAnalyzer) this.applicationContext.getBean(Class.forName(achievement.getAnalyzer()));
					achievement.setAchievementAnalyzer(analyzer);
					List<AchievementTag> tags = this.achievementTagManager.list(achievement.getId());
					for (AchievementTag tag : tags) {
						myTagCache.putValue(tag.getName(), achievement);
					}
				} catch (BeansException | ClassNotFoundException e) {
					log.log(Level.SEVERE, e.getMessage(), e);
				}
			}
		}
		this.tagCache = myTagCache;
	}

	/**
	 * Determines which {@link AchievementAnalyzer}s to run and executes them.
	 * If an analyzer matches more than one tag, it only executes once.
	 * 
	 * @param userId
	 *            The user to check for, required.
	 * @param tags
	 *            The tags to match achievements on, required.
	 * @return true if the analyzers were successfully dispatched, otherwise
	 *         false
	 * @throws DataOperationException
	 *             If a query could not be executed.
	 */
	public boolean dispatch(UserId userId, String[] tags) throws DataOperationException {
		AjahUtils.requireParam(userId, "userId");
		AjahUtils.requireParam(tags, "tags");
		if (this.tagCache == null) {
			init();
		}
		Set<Achievement> achievements = new HashSet<>();
		for (String tag : tags) {
			List<Achievement> list = this.tagCache.getList(tag);
			if (list != null) {
				achievements.addAll(list);
			}
		}
		if (achievements.size() == 0) {
			log.warning("No achievements matched " + StringUtils.join(",", tags));
			return true;
		}
		for (Achievement achievement : achievements) {
			AchievementUser achievementUser = this.achievementUserManager.find(userId, achievement.getId());
			if (achievementUser != null && achievementUser.getStatus() == AchievementUserStatus.COMPLETED) {
				log.fine("Already completed");
			} else {
				achievement.getAchievementAnalyzer().analyze(userId, achievement, achievementUser);
			}
		}
		return true;
	}

	/**
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
