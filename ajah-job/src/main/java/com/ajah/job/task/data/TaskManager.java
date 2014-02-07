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
package com.ajah.job.task.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.job.task.Task;
import com.ajah.job.task.TaskId;
import com.ajah.job.task.TaskStatus;
import com.ajah.job.task.TaskType;
import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;

/**
 * Manages data operations for {@link Task}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Log
public class TaskManager {

	@Autowired
	private TaskDao taskDao;

	/**
	 * Returns a count of all records.
	 * 
	 * @return Count of all records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count() throws DataOperationException {
		return count(null, null);
	}

	/**
	 * Counts the records available that match the criteria.
	 * 
	 * @param type
	 *            The task type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final TaskType type, final TaskStatus status) throws DataOperationException {
		return this.taskDao.count(type, status);
	}

	/**
	 * Creates a new {@link Task} with the given properties.
	 * 
	 * @param name
	 *            The name of the task, required.
	 * @param type
	 *            The type of task, required.
	 * @param status
	 *            The status of the task, required.
	 * @return The result of the creation, which will include the new task at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<Task> create(final String name, final TaskType type, final TaskStatus status) throws DataOperationException {
		final Task task = new Task();
		task.setName(name);
		task.setType(type);
		task.setStatus(status);
		final DataOperationResult<Task> result = save(task);
		return result;
	}

	/**
	 * Marks the entity as {@link TaskStatus#DELETED}.
	 * 
	 * @param taskId
	 *            The ID of the task to delete.
	 * @return The result of the deletion, will not include the new task at
	 *         {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws TaskNotFoundException
	 *             If the ID specified did not match any tasks.
	 */
	public DataOperationResult<Task> delete(final TaskId taskId) throws DataOperationException, TaskNotFoundException {
		final Task task = load(taskId);
		task.setStatus(TaskStatus.DELETED);
		final DataOperationResult<Task> result = save(task);
		return result;
	}

	/**
	 * Returns a list of {@link Task}s that match the specified criteria.
	 * 
	 * @param type
	 *            The type of task, optional.
	 * @param status
	 *            The status of the task, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link Task}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<Task> list(final TaskType type, final TaskStatus status, final long page, final long count) throws DataOperationException {
		return this.taskDao.list(type, status, page, count);
	}

	/**
	 * Loads an {@link Task} by it's ID.
	 * 
	 * @param taskId
	 *            The ID to load, required.
	 * @return The matching task, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws TaskNotFoundException
	 *             If the ID specified did not match any tasks.
	 */
	public Task load(final TaskId taskId) throws DataOperationException, TaskNotFoundException {
		final Task task = this.taskDao.load(taskId);
		if (task == null) {
			throw new TaskNotFoundException(taskId);
		}
		return task;
	}

	/**
	 * Saves an {@link Task}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param task
	 *            The task to save.
	 * @return The result of the save operation, which will include the new task
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<Task> save(final Task task) throws DataOperationException {
		boolean create = false;
		if (task.getId() == null) {
			task.setId(new TaskId(UUID.randomUUID().toString()));
			create = true;
		}
		if (task.getCreated() == null) {
			task.setCreated(new Date());
			create = true;
		}
		if (create) {
			final DataOperationResult<Task> result = this.taskDao.insert(task);
			log.fine("Created Task " + task.getName() + " [" + task.getId() + "]");
			return result;
		}
		final DataOperationResult<Task> result = this.taskDao.update(task);
		log.fine("Updated Task " + task.getName() + " [" + task.getId() + "]");
		return result;
	}

}
