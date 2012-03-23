/*
 *  Copyright 2011 Eric F. Savage, code@efsavage.com
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
package com.ajah.css.util;

import java.util.Comparator;

import com.ajah.css.CssRule;
import com.ajah.util.compare.CompareUtils;

/**
 * Compares rules based on their specificity. If specificity is being computed
 * correctly, the document should be able to be reordered/optimized without
 * affecting any behaviors.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class RuleComparator implements Comparator<CssRule> {

	/**
	 * Singleton instance.
	 */
	public static final RuleComparator INSTANCE = new RuleComparator();

	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(final CssRule first, final CssRule second) {
		int retVal = CompareUtils.compare(first.getSelectors().get(0).getType().getSpecificity(), second.getSelectors().get(0).getType().getSpecificity());
		if (retVal == 0) {
			retVal = CompareUtils.compare(first.getSelectors().get(0).getPosition(), second.getSelectors().get(0).getPosition());
		}
		return retVal;
	}

}
