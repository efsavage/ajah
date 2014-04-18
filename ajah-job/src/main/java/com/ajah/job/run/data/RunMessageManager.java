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
package com.ajah.job.run.data;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajah.job.Job;
import com.ajah.job.run.RunId;
import com.ajah.job.run.RunMessage;
import com.ajah.job.run.RunMessageId;
import com.ajah.job.run.RunMessageStatus;
import com.ajah.job.run.RunMessageType;
import com.ajah.spring.jdbc.DataOperationResult;
import com.ajah.spring.jdbc.err.DataOperationException;
import com.ajah.util.StringUtils;
import com.vigilanced.client.VigilancedClient;

/**
 * Manages data operations for {@link RunMessage}.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
@Service
@Slf4j
public class RunMessageManager {

	@Autowired
	private RunMessageDao runMessageDao;

	@Autowired(required = false)
	private VigilancedClient vigilancedClient;

	/**
	 * Saves an {@link RunMessage}. Assigns a new ID ({@link UUID}) and sets the
	 * creation date if necessary. If either of these elements are set, will
	 * perform an insert. Otherwise will perform an update.
	 * 
	 * @param runMessage
	 *            The runMessage to save.
	 * @return The result of the save operation, which will include the new
	 *         runMessage at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<RunMessage> save(RunMessage runMessage) throws DataOperationException {
		boolean create = false;
		if (runMessage.getId() == null) {
			runMessage.setId(new RunMessageId(UUID.randomUUID().toString()));
			create = true;
		}
		if (runMessage.getCreated() == null) {
			runMessage.setCreated(new Date());
			create = true;
		}
		if (create) {
			DataOperationResult<RunMessage> result = this.runMessageDao.insert(runMessage);
			log.debug("Created RunMessage " + runMessage.getId());
			return result;
		}
		DataOperationResult<RunMessage> result = this.runMessageDao.update(runMessage);
		log.debug("Updated RunMessage " + runMessage.getId());
		return result;
	}

	/**
	 * Loads an {@link RunMessage} by it's ID.
	 * 
	 * @param runMessageId
	 *            The ID to load, required.
	 * @return The matching runMessage, if found. Will not return null.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws RunMessageNotFoundException
	 *             If the ID specified did not match any runMessages.
	 */
	public RunMessage load(RunMessageId runMessageId) throws DataOperationException, RunMessageNotFoundException {
		RunMessage runMessage = this.runMessageDao.load(runMessageId);
		if (runMessage == null) {
			throw new RunMessageNotFoundException(runMessageId);
		}
		return runMessage;
	}

	/**
	 * Returns a list of {@link RunMessage}s that match the specified criteria.
	 * 
	 * @param type
	 *            The type of runMessage, optional.
	 * @param status
	 *            The status of the runMessage, optional.
	 * @param page
	 *            The page of results to fetch.
	 * @param count
	 *            The number of results per page.
	 * @return A list of {@link RunMessage}s, which may be empty.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public List<RunMessage> list(RunMessageType type, RunMessageStatus status, long page, long count) throws DataOperationException {
		return this.runMessageDao.list(type, status, page, count);
	}

	/**
	 * Creates a new {@link RunMessage} with the given properties.
	 * 
	 * @param runId
	 *            The name of the runMessage, required.
	 * @param message
	 *            The type of runMessage, required.
	 * @param external
	 *            Notify external services of this message?
	 * @param debug
	 *            The status of the runMessage, required.
	 * @return The result of the creation, which will include the new runMessage
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public DataOperationResult<RunMessage> create(Job job, RunId runId, String message, Throwable throwable, RunMessageType type, boolean external) throws DataOperationException {
		RunMessage runMessage = new RunMessage();
		runMessage.setRunId(runId);
		runMessage.setJobId(job.getId());
		runMessage.setMessage(message);

		if (throwable != null) {
			throwable.printStackTrace();
			StringWriter errors = new StringWriter();
			throwable.printStackTrace(new PrintWriter(errors));
			runMessage.setStackTrace(errors.toString());
			if (StringUtils.isBlank(message)) {
				runMessage.setMessage(throwable.getClass().getSimpleName());
			}
		}

		runMessage.setType(type);
		runMessage.setStatus(RunMessageStatus.ACTIVE);
		DataOperationResult<RunMessage> result = save(runMessage);
		if (external) {
			if (this.vigilancedClient != null) {
				this.vigilancedClient.addMessage(job.getMonitorKey(), message, null, null);
			} else {
				log.debug("No Vigilanced client is configured");
			}
		}
		return result;
	}

	/**
	 * Marks the entity as {@link RunMessageStatus#DELETED}.
	 * 
	 * @param runMessageId
	 *            The ID of the runMessage to delete.
	 * @return The result of the deletion, will not include the new runMessage
	 *         at {@link DataOperationResult#getEntity()}.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 * @throws RunMessageNotFoundException
	 *             If the ID specified did not match any runMessages.
	 */
	public DataOperationResult<RunMessage> delete(RunMessageId runMessageId) throws DataOperationException, RunMessageNotFoundException {
		RunMessage runMessage = load(runMessageId);
		runMessage.setStatus(RunMessageStatus.DELETED);
		DataOperationResult<RunMessage> result = save(runMessage);
		return result;
	}

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
	 *            The runMessage type to limit to, optional.
	 * @param status
	 *            The status to limit to, optional.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public long count(final RunMessageType type, final RunMessageStatus status) throws DataOperationException {
		return this.runMessageDao.count(type, status);
	}

	/**
	 * Counts the records available that match the search criteria.
	 * 
	 * @param search
	 *            The search query.
	 * @return The number of matching records.
	 * @throws DataOperationException
	 *             If the query could not be executed.
	 */
	public int searchCount(String search) throws DataOperationException {
		return this.runMessageDao.searchCount(search);
	}

}
