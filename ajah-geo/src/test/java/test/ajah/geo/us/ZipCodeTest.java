package test.ajah.geo.us;

import org.junit.Assert;
import org.junit.Test;

import com.ajah.geo.us.IllegalZipCodeFormatException;
import com.ajah.geo.us.ZipCode;

/**
 * Unit tests for ZipCode class.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 */
public class ZipCodeTest {

	/**
	 * Test format that should work.
	 */
	@Test
	public void goodFormat1() {
		ZipCode test = new ZipCode();
		test.setZip("00000");
		test.setZip4("0000");
		Assert.assertEquals("00000-0000", test.toString());
	}

	/**
	 * Test another format that should work.
	 */
	@Test
	public void goodFormat2() {
		ZipCode test = new ZipCode();
		test.setZip("11111");
		test.setZip4("1111");
		Assert.assertEquals("11111-1111", test.toString());
	}

	/**
	 * Test default
	 */
	@Test
	public void defaultFormat() {
		ZipCode test = new ZipCode();
		Assert.assertNull(test.toString());
	}

	/**
	 * Test null
	 */
	@Test
	public void nullFormat() {
		ZipCode test = new ZipCode();
		test.setZip(null);
		Assert.assertNull(test.toString());
	}

	/**
	 * Test null with zip4
	 */
	@Test
	public void nullFormatZip4() {
		ZipCode test = new ZipCode();
		test.setZip(null);
		test.setZip4("1111");
		Assert.assertNull(test.toString());
	}

	/**
	 * Test too short
	 */
	@Test(expected=IllegalZipCodeFormatException.class)
	public void tooShort() {
		ZipCode test = new ZipCode();
		test.setZip(null);
		test.setZip4("3");
		Assert.assertNull(test.toString());
	}

	/**
	 * Test too long
	 */
	@Test(expected=IllegalZipCodeFormatException.class)
	public void tooLong() {
		ZipCode test = new ZipCode();
		test.setZip(null);
		test.setZip4("1234567");
		Assert.assertNull(test.toString());
	}

	/**
	 * Test alpha chars
	 */
	@Test(expected=IllegalZipCodeFormatException.class)
	public void alphaChars() {
		ZipCode test = new ZipCode();
		test.setZip(null);
		test.setZip4("abs");
		Assert.assertNull(test.toString());
	}

	/**
	 * Test alphanumeric chars
	 */
	@Test(expected=IllegalZipCodeFormatException.class)
	public void alphaNumericChars() {
		ZipCode test = new ZipCode();
		test.setZip(null);
		test.setZip4("12345abs");
		Assert.assertNull(test.toString());
	}

}