/*
 *  Copyright 2016 Eric F. Savage, code@efsavage.com
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
package com.ajah.util.lang;

import lombok.Data;

/**
 * Simple bean to represent a left/right pair. Useful for things like composite
 * cache keys.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>,
 *         <a href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * @param <O>
 *            The type of the left.
 * @param <V>
 *            The type of the right.
 */
@Data
public class Pair<L, R> {

	private L left;

	private R right;

	/**
	 * Constructs this bean with the two fields. Either field may be null.
	 * 
	 * @param left
	 *            The left of the pair, may be null.
	 * @param right
	 *            The right of the pair, may be null.
	 */
	public Pair(final L left, final R right) {
		this.left = left;
		this.right = right;
	}

	/**
	 * Returns the left of the pair.
	 * 
	 * @return The left of the pair.
	 */
	public L getLeft() {
		return this.left;
	}

	/**
	 * Returns the right of the pair.
	 * 
	 * @return The right of the pair.
	 */
	public R getRight() {
		return this.right;
	}

	/**
	 * Sets the left of the pair..
	 * 
	 * @param left
	 *            The left of the pair.
	 */
	public void setLeft(final L left) {
		this.left = left;
	}

	/**
	 * Sets the right of the pair..
	 * 
	 * @param right
	 *            The right of the pair.
	 */
	public void setRight(final R right) {
		this.right = right;
	}

}
