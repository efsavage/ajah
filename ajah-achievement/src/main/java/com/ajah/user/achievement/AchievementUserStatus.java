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

import com.ajah.util.IdentifiableEnum;

/**
 * Valid states of {@link AchievementUser} entities.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public enum AchievementUserStatus implements IdentifiableEnum<String> {

	/**
	 * Incomplete.
	 */
	INCOMPLETE("0", "incomplete", "Incomplete", "Incomplete.", true, false, false),
	/**
	 * In progress.
	 */
	IN_PROGRESS("1", "progress", "In Progress", "In progress.", true, true, false),
	/**
	 * Completed.
	 */
	COMPLETED("2", "complete", "Completed", "Completed.", false, false, true);

	/**
	 * Finds a AchievementUserStatus that matches the id on id, name, or name().
	 * 
	 * @param string
	 *            Value to match against id, name, or name()
	 * @return Matching AchievementUserStatus, or null.
	 */
	public static AchievementUserStatus get(final String string) {
		for (final AchievementUserStatus type : values()) {
			if (type.getId().equals(string) || type.getCode().equals(string) || type.name().equals(string) || type.getName().equals(string)) {
				return type;
			}
		}
		return null;
	}

	private final String id;
	private final String code;
	private final String name;
	private final String description;
	private final boolean started;
	private final boolean inProgress;
	private final boolean completed;

	private AchievementUserStatus(final String id, final String code, final String name, final String description, final boolean started, final boolean inProgress, final boolean completed) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
		this.started = started;
		this.inProgress = inProgress;
		this.completed = completed;
	}

	/**
	 * The short, display-friendly code of the type. If no code is applicable,
	 * it should be an alias for the ID.
	 * 
	 * @return The short, display-friendly code of the type. Cannot be null.
	 */
	@Override
	public String getCode() {
		return this.code;
	}

	/**
	 * The display-friendly description of the type.
	 * 
	 * @return The display-friendly description of the type. May be null.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * The internal ID of the type.
	 * 
	 * @return The internal ID of the type. Cannot be null.
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * The display-friendly name of the type. If no name is applicable, it
	 * should be an alias for the ID or code.
	 * 
	 * @return The display-friendly name of the type. Cannot be null.
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Does this status mean that the entity has been completed?
	 * 
	 * @return true if completed, otherwise false
	 */
	public boolean isCompleted() {
		return this.completed;
	}

	/**
	 * Does this status mean that the entity is in an inProgress state?
	 * 
	 * @return true if in inProgress state, otherwise false
	 */
	public boolean isInProgress() {
		return this.inProgress;
	}

	/**
	 * Does this status mean that the entity is started?
	 * 
	 * @return true if started, otherwise false
	 */
	public boolean isStarted() {
		return this.started;
	}

	@Override
	public void setId(final String id) {
		throw new UnsupportedOperationException();
	}
}
