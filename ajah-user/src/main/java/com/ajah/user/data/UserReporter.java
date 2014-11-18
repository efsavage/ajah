package com.ajah.user.data;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.lang.MapMap;

@Service
public class UserReporter {

	@Autowired
	private UserInfoDao userInfoDao;

	public MapMap<LocalDate, String, Integer> getSourceCounts() {
		return userInfoDao.getSourceCounts();
	}

}
