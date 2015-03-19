package com.ajah.user.achievement;

import com.ajah.user.UserId;

public interface AchievementDispatchListener {

	void execute(UserId userId, Achievement achievement, AchievementUser achievementUser);

}
