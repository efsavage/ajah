/*  Copyright 2011 Eric F. Savage, code@efsavage.com
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
package com.ajah.user.data;

import com.ajah.spring.jdbc.AjahDao;
import com.ajah.user.User;
import com.ajah.user.UserId;
import com.ajah.util.crypto.Password;

/**
 * Data operations on the "user" table.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * 
 */
public interface UserDao extends AjahDao<UserId, User> {

	public void insert(User user, Password password);

	public void update(UserId userId, Password password);

	public User findByFields(String[] strings, String[] strings2);

	public User findById(UserId userId);

	public User findByField(String string, String username);

}
