/*
 *  Copyright 2011 Eric F. Savage, code@efsavage.com
 *
 *   Licensed under the Apache License, Version 2.0 the "License");
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
package com.ajah.util.data;

import java.math.BigInteger;

/**
 * Enumeration of standard data measurements. This version includes the largest
 * units which exceed the 64-bit space of a long, so they use {@link BigInteger}
 * s.
 * 
 * @author <a href("http://efsavage.com">Eric F. Savage</a>, <a
 *         href("mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public enum LargeDataSizeUnit {

	/**
	 * Bit (smallest unit)
	 */
	BIT(BigInteger.valueOf(1), "bit", "b"),
	/**
	 * Kilobit (one thousand bits)
	 */
	KILOBIT(BigInteger.valueOf(1000).multiply(BIT.bits), "kilobit", "kb"),
	/**
	 * Megabit (one million bits)
	 */
	MEGABIT(BigInteger.valueOf(1000).multiply(KILOBIT.bits), "megabit", "Mb"),
	/**
	 * Gigabit (one billion bits)
	 */
	GIGABIT(BigInteger.valueOf(1000).multiply(MEGABIT.bits), "gigabit", "Gb"),
	/**
	 * Terabit (one trillion bits)
	 */
	TERABIT(BigInteger.valueOf(1000).multiply(GIGABIT.bits), "terabit", "Tb"),
	/**
	 * Petabit (one quadrillion bits)
	 */
	PETABIT(BigInteger.valueOf(1000).multiply(TERABIT.bits), "petabit", "Pb"),
	/**
	 * Exabit (one quintillion bits)
	 */
	EXABIT(BigInteger.valueOf(1000).multiply(PETABIT.bits), "exabit", "Eb"),
	/**
	 * Zettabit (one sextillion bits)
	 */
	ZETTABIT(BigInteger.valueOf(1000).multiply(EXABIT.bits), "zettabit", "Zb"),
	/**
	 * Yottabit (one septillion bits)
	 */
	YOTTABIT(BigInteger.valueOf(1000).multiply(ZETTABIT.bits), "yottabit", "Yb"),
	/**
	 * Kibibit (2<sup>10</sup> or 1024 bits)
	 */
	KIBIBIT(BigInteger.valueOf(1024).multiply(BIT.bits), "kibibit", "kib"),
	/**
	 * Mebibit (2<sup>20</sup> or 1,048,576 bits)
	 */
	MEBIBIT(BigInteger.valueOf(1024).multiply(KIBIBIT.bits), "mebibit", "Mib"),
	/**
	 * Gibibit (2<sup>30</sup> or 1,048,576 bits)
	 */
	GIBIBIT(BigInteger.valueOf(1024).multiply(MEBIBIT.bits), "gibibit", "Gib"),
	/**
	 * Tebibit (2<sup>40</sup> or 1,048,576 bits)
	 */
	TEBIBIT(BigInteger.valueOf(1024).multiply(GIBIBIT.bits), "tebibit", "Tib"),
	/**
	 * Pebibit (2<sup>50</sup> or 1,048,576 bits)
	 */
	PEBIBIT(BigInteger.valueOf(1024).multiply(TEBIBIT.bits), "pebibit", "Pib"),
	/**
	 * Exbibit (2<sup>60</sup> or 1,048,576 bits)
	 */
	EXBIBIT(BigInteger.valueOf(1024).multiply(PEBIBIT.bits), "exbibit", "Eib"),
	/**
	 * Zebibit (2<sup>70</sup> or 1,048,576 bits)
	 */
	ZEBIBIT(BigInteger.valueOf(1024).multiply(EXBIBIT.bits), "zebibit", "Zib"),
	/**
	 * Yobibit (2<sup>80</sup> or 1,048,576 bits)
	 */
	YOBIBIT(BigInteger.valueOf(1024).multiply(ZEBIBIT.bits), "yobibit", "Yib"),
	/**
	 * Byte (eight bits)
	 */
	BYTE(BigInteger.valueOf(8).multiply(BIT.bits), "byte", "B"),
	/**
	 * Kilobyte (one thousand bytes)
	 */
	KILOBYTE(BigInteger.valueOf(1000).multiply(BYTE.bits), "kilobyte", "kB"),
	/**
	 * Megabyte (one million bytes)
	 */
	MEGABYTE(BigInteger.valueOf(1000).multiply(KILOBYTE.bits), "megabyte", "MB"),
	/**
	 * Gigabyte (one billion bytes)
	 */
	GIGABYTE(BigInteger.valueOf(1000).multiply(MEGABYTE.bits), "gigabyte", "GB"),
	/**
	 * Terabyte (one trillion bytes)
	 */
	TERABYTE(BigInteger.valueOf(1000).multiply(GIGABYTE.bits), "terabyte", "TB"),
	/**
	 * Petabyte (one quadrillion bytes)
	 */
	PETABYTE(BigInteger.valueOf(1000).multiply(TERABYTE.bits), "petabyte", "PB"),
	/**
	 * Exabyte (one quintillion bytes)
	 */
	EXABYTE(BigInteger.valueOf(1000).multiply(PETABYTE.bits), "exabyte", "EB"),
	/**
	 * Zettabyte (one sextillion bytes)
	 */
	ZETTABYTE(BigInteger.valueOf(1000).multiply(EXABYTE.bits), "zettabyte", "ZB"),
	/**
	 * Yottabyte (one septillion bytes)
	 */
	YOTTABYTE(BigInteger.valueOf(1000).multiply(ZETTABYTE.bits), "yottabyte", "YB"),
	/**
	 * Kibibyte (2<sup>10</sup> or 1024 bytes)
	 */
	KIBIBYTE(BigInteger.valueOf(1024).multiply(BYTE.bits), "kibibyte", "kiB"),
	/**
	 * Mebibyte (2<sup>20</sup> or 1,048,576 bytes)
	 */
	MEBIBYTE(BigInteger.valueOf(1024).multiply(KIBIBYTE.bits), "mebibyte", "MiB"),
	/**
	 * Gibibyte (2<sup>30</sup> or 1,048,576 bytes)
	 */
	GIBIBYTE(BigInteger.valueOf(1024).multiply(MEBIBYTE.bits), "gibibyte", "GiB"),
	/**
	 * Tebibyte (2<sup>40</sup> or 1,048,576 bytes)
	 */
	TEBIBYTE(BigInteger.valueOf(1024).multiply(GIBIBYTE.bits), "tebibyte", "TiB"),
	/**
	 * Pebibyte (2<sup>50</sup> or 1,048,576 bytes)
	 */
	PEBIBYTE(BigInteger.valueOf(1024).multiply(TEBIBYTE.bits), "pebibyte", "PiB"),
	/**
	 * Exbibyte (2<sup>60</sup> or 1,048,576 bytes)
	 */
	EXBIBYTE(BigInteger.valueOf(1024).multiply(PEBIBYTE.bits), "exbibyte", "EiB"),
	/**
	 * Zebibyte (2<sup>70</sup> or 1,048,576 bytes)
	 */
	ZEBIBYTE(BigInteger.valueOf(1024).multiply(EXBIBYTE.bits), "zebibyte", "ZiB"),
	/**
	 * Yobibyte (2<sup>80</sup> or 1,048,576 bytes)
	 */
	YOBIBYTE(BigInteger.valueOf(1024).multiply(ZEBIBYTE.bits), "yobibyte", "YiB");

	private final BigInteger bits;

	private final long bitsLong;

	private final BigInteger bytes;

	private final long bytesLong;

	private final String name;

	private LargeDataSizeUnit(final BigInteger bits, final String name, final String abbreviation) {
		this.bits = bits;
		this.bytes = bits.divide(BigInteger.valueOf(8));
		if (this.bits.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) <= 0) {
			this.bitsLong = this.bits.longValue();
		} else {
			this.bitsLong = -1;
		}
		if (this.bytes.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) <= 0) {
			this.bytesLong = this.bytes.longValue();
		} else {
			this.bytesLong = -1;
		}
		this.name = name;
		this.abbreviation = abbreviation;
	}

	/**
	 * Full name of this unit.
	 * 
	 * @return Full name of this unit.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Abbreviation of this unit. Note that case matters (b=bit, B=byte).
	 * 
	 * @return Abbreviation of this unit.
	 */
	public String getAbbreviation() {
		return this.abbreviation;
	}

	private final String abbreviation;

	/**
	 * Return the number of bits for this unit. Note that due to long being a
	 * 64-bit number, this will throw an UnsupportedOperationException for very
	 * large units.
	 * 
	 * @return The number of bits for this unit.
	 * @throws UnsupportedOperationException
	 */
	public long getBits() {
		if (this.bitsLong < 0) {
			throw new UnsupportedOperationException();
		}
		return this.bitsLong;
	}

	/**
	 * Return the number of bytes for this unit. Note that due to long being a
	 * 64-bit number, this will throw an illegal argument exception for very
	 * large units.
	 * 
	 * @return The number of bytes for this unit.
	 * @throws IllegalArgumentException
	 */
	public long getBytes() {
		if (this.bytesLong < 0) {
			throw new UnsupportedOperationException();
		}
		return this.bytesLong;
	}

}
