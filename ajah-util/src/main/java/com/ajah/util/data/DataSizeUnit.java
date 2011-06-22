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

/**
 * Enumeration of standard data measurements. This version only supports up to
 * exabit/exabytes as the units above that are larger than 64-bit numbers. See
 * {@Link LargeDataSizeUnit} for those units.
 * 
 * @author <a href("http://efsavage.com">Eric F. Savage</a>, <a
 *         href("mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public enum DataSizeUnit {

	/**
	 * Bit (smallest unit)
	 */
	BIT(1, "bit", "b"),
	/**
	 * Kilobit (one thousand bits)
	 */
	KILOBIT(1000 * BIT.bits, "kilobit", "kb"),
	/**
	 * Megabit (one million bits)
	 */
	MEGABIT(1000 * KILOBIT.bits, "megabit", "Mb"),
	/**
	 * Gigabit (one billion bits)
	 */
	GIGABIT(1000 * MEGABIT.bits, "gigabit", "Gb"),
	/**
	 * Terabit (one trillion bits)
	 */
	TERABIT(1000 * GIGABIT.bits, "terabit", "Tb"),
	/**
	 * Petabit (one quadrillion bits)
	 */
	PETABIT(1000 * TERABIT.bits, "petabit", "Pb"),
	/**
	 * Exabit (one quintillion bits)
	 */
	EXABIT(1000 * PETABIT.bits, "exabit", "Eb"),
	/**
	 * Kibibit (2<sup>10</sup> or 1024 bits)
	 */
	KIBIBIT(1024 * BIT.bits, "kibibit", "kib"),
	/**
	 * Mebibit (2<sup>20</sup> or 1,048,576 bits)
	 */
	MEBIBIT(1024 * KIBIBIT.bits, "mebibit", "Mib"),
	/**
	 * Gibibit (2<sup>30</sup> or 1,048,576 bits)
	 */
	GIBIBIT(1024 * MEBIBIT.bits, "gibibit", "Gib"),
	/**
	 * Tebibit (2<sup>40</sup> or 1,048,576 bits)
	 */
	TEBIBIT(1024 * GIBIBIT.bits, "tebibit", "Tib"),
	/**
	 * Pebibit (2<sup>50</sup> or 1,048,576 bits)
	 */
	PEBIBIT(1024 * TEBIBIT.bits, "pebibit", "Pib"),
	/**
	 * Exbibit (2<sup>60</sup> or 1,048,576 bits)
	 */
	EXBIBIT(1024 * PEBIBIT.bits, "exbibit", "Eib"),
	/**
	 * Byte (eight bits)
	 */
	BYTE(8 * BIT.bits, "byte", "B"),
	/**
	 * Kilobyte (one thousand bytes)
	 */
	KILOBYTE(1000 * BYTE.bits, "kilobyte", "kB"),
	/**
	 * Megabyte (one million bytes)
	 */
	MEGABYTE(1000 * KILOBYTE.bits, "megabyte", "MB"),
	/**
	 * Gigabyte (one billion bytes)
	 */
	GIGABYTE(1000 * MEGABYTE.bits, "gigabyte", "GB"),
	/**
	 * Terabyte (one trillion bytes)
	 */
	TERABYTE(1000 * GIGABYTE.bits, "terabyte", "TB"),
	/**
	 * Petabyte (one quadrillion bytes)
	 */
	PETABYTE(1000 * TERABYTE.bits, "petabyte", "PB"),
	/**
	 * Exabyte (one quintillion bytes)
	 */
	EXABYTE(1000 * PETABYTE.bits, "exabyte", "EB"),
	/**
	 * Kibibyte (2<sup>10</sup> or 1024 bytes)
	 */
	KIBIBYTE(1024 * BYTE.bits, "kibibyte", "kiB"),
	/**
	 * Mebibyte (2<sup>20</sup> or 1,048,576 bytes)
	 */
	MEBIBYTE(1024 * KIBIBYTE.bits, "mebibyte", "MiB"),
	/**
	 * Gibibyte (2<sup>30</sup> or 1,048,576 bytes)
	 */
	GIBIBYTE(1024 * MEBIBYTE.bits, "gibibyte", "GiB"),
	/**
	 * Tebibyte (2<sup>40</sup> or 1,048,576 bytes)
	 */
	TEBIBYTE(1024 * GIBIBYTE.bits, "tebibyte", "TiB"),
	/**
	 * Pebibyte (2<sup>50</sup> or 1,048,576 bytes)
	 */
	PEBIBYTE(1024 * TEBIBYTE.bits, "pebibyte", "PiB"),
	/**
	 * Exbibyte (2<sup>60</sup> or 1,048,576 bytes)
	 */
	EXBIBYTE(1024 * PEBIBYTE.bits, "exbibyte", "EiB");

	private final long bits;

	private final long bytes;

	private final String name;

	private DataSizeUnit(long bits, String name, String abbreviation) {
		this.bits = bits;
		this.bytes = bits / 8;
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
	 * Return the number of bits for this unit.
	 * 
	 * @return The number of bits for this unit.
	 * @throws IllegalArgumentException
	 */
	public long getBits() {
		return this.bits;
	}

	/**
	 * Return the number of bytes for this unit.
	 * 
	 * @return The number of bytes for this unit.
	 * @throws IllegalArgumentException
	 */
	public long getBytes() {
		return this.bytes;
	}

	/**
	 * Returns the number of bytes the quantity of this unit represents.
	 * 
	 * Example: DataSizeUnit.MEBIBIT.getAsBytes(3) would return 3145728.
	 * 
	 * Note: This may wrap around if large quantities of large units are used.
	 * Use {@link LargeDataSizeUnit} where appropriate.
	 * 
	 * @param quantity
	 *            of this unit to return.
	 * @return The number of bytes the quantity of this unit represents.
	 */
	public long getAsBytes(long quantity) {
		return this.bytes * quantity;
	}

	/**
	 * Formats the number of bytes to an automatically selected unit, with two
	 * decimal places and the unit's abbreviation. Examples:
	 * 
	 * 1024 formats to "1.00 kiB", 135498798 formats to 129.22 MiB".
	 * 
	 * @param bytes
	 *            The number of bytes to format.
	 * @return The formatted string for the number of bytes.
	 */
	public static String format(long bytes) {
		DataSizeUnit[] autoFormatOrder = new DataSizeUnit[] { EXBIBYTE, PEBIBYTE, TEBIBYTE, GIBIBYTE, MEBIBYTE, KIBIBYTE };
		for (DataSizeUnit unit : autoFormatOrder) {
			if (bytes > unit.bytes) {
				return String.format("%.2f %s", Double.valueOf((double) (bytes) / unit.bytes), unit.getAbbreviation());
			}
		}
		return bytes + " " + BYTE.getAbbreviation();
	}

	/**
	 * Returns a double of the current unit quantity that represents the number
	 * of bytes supplied.
	 * 
	 * @param actualBytes
	 *            The number of bytes to convert to this unit.
	 * @return The quantity of this unit equivalent to the number of bytes
	 *         supplied.
	 */
	public double fromBytes(long actualBytes) {
		return (double) actualBytes / this.bytes;
	}
}