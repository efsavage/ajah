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
package test.ajah.util.data;

import org.junit.Assert;
import org.junit.Test;

import com.ajah.util.data.Weight;
import com.ajah.util.data.WeightUnit;

/**
 * Tests {@link Weight} and {@link WeightUnit}.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class WeightTest {

	/**
	 * Tests {@link Weight#to(WeightUnit)}
	 */
	@Test
	public void kilogramToOunce() {
		Assert.assertEquals(35.27396194958048, new Weight(1.0, WeightUnit.KILOGRAM).to(WeightUnit.OUNCE).getQuanity().doubleValue(), .00000000001);
	}

	/**
	 * Tests {@link Weight#to(WeightUnit)}
	 */
	@Test
	public void ounceToGram() {
		Assert.assertEquals(34.98331153624993, new Weight(1.234, WeightUnit.OUNCE).to(WeightUnit.GRAM).getQuanity().doubleValue(), .00000000001);
	}

	/**
	 * Tests {@link Weight#to(WeightUnit)}
	 */
	@Test
	public void ounceToKilogram() {
		Assert.assertEquals(0.000045075741768749917, new Weight(.00159, WeightUnit.OUNCE).to(WeightUnit.KILOGRAM).getQuanity().doubleValue(), .00000000001);
	}

}
