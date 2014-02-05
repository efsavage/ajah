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
package com.ajah.job.task;

import java.util.Date;

import lombok.Data;

import org.springframework.context.ApplicationContext;

import com.ajah.util.Identifiable;

@Data
public class Task implements Identifiable<TaskId> {

	private TaskId id;
	private String name;
	private String clazz;
	private TaskStatus status;
	private TaskType type;
	private Date created;

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void execute(ApplicationContext applicationContext) {
		try {
			AjahTask ajahTask = (AjahTask) Class.forName(getClazz()).newInstance();
			ajahTask.setApplicationContext(applicationContext);
			ajahTask.run();
		} catch (InstantiationException e) {
			throw new TaskConfigurationException(e);
		} catch (IllegalAccessException e) {
			throw new TaskConfigurationException(e);
		} catch (ClassNotFoundException e) {
			throw new TaskConfigurationException(e);
		}
	}

}