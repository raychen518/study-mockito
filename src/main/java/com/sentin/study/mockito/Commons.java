package com.sentin.study.mockito;

public final class Commons {

	private static final String DELIMITER_LINE = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

	private static final int NUMERIC_STRING_DEFAULT_PADDING_LENGTH = 2;

	private Commons() {
	}

	/**
	 * Print a delimiter line with a lower blank line.
	 */
	public static void printDelimiterLine() {
		printDelimiterLine(null, false);
	}

	/**
	 * Print a delimiter line with a lower blank line, and the delimiter line is
	 * marked with a number which automatically increases based on the specified
	 * number.
	 * 
	 * @param number
	 *            The number which serves as the base of the mark numbers of the
	 *            delimiter line.
	 */
	public static void printDelimiterLine(Integer number) {
		printDelimiterLine(number, false);
	}

	/**
	 * Print a delimiter line with a upper blank line (if the specified Needing
	 * Upper Blank Line flag is true) and a lower blank line, and the delimiter
	 * line is marked with a number which automatically increases based on the
	 * specified number.
	 * 
	 * @param number
	 *            The number which serves as the base of the mark numbers of the
	 *            delimiter line.
	 * @param needingUpperBlankLine
	 *            Whether the delimiter line needs a upper blank line.
	 */
	public static void printDelimiterLine(Integer number, Boolean needingUpperBlankLine) {
		if (needingUpperBlankLine) {
			System.out.println();
		}

		String delimiterLinePrefix = (number == null) ? "" : "[" + padNumericString(number) + "] ";

		System.out.println(delimiterLinePrefix + DELIMITER_LINE);
		System.out.println();
	}

	/**
	 * Pad a string of the specified number to the specified length, using the
	 * character '0'.
	 * 
	 * @param number
	 *            The number used to generate the numeric string.
	 * @param length
	 *            The length to which the numeric string should be padded.
	 * @return The padded numeric string.
	 */
	public static String padNumericString(int number, int length) {
		String formatString = "%0" + length + "d";
		return String.format(formatString, number);
	}

	/**
	 * Pad a string of the specified number to the default length, using the
	 * character '0'.
	 * 
	 * @param number
	 *            The number used to generate the numeric string.
	 * @return The padded numeric string.
	 */
	public static String padNumericString(int number) {
		return padNumericString(number, NUMERIC_STRING_DEFAULT_PADDING_LENGTH);
	}

	public static String getObjectInfo(Object object) {
		StringBuilder result = new StringBuilder();

		if (object != null) {
			result.append(object.getClass().getSimpleName()).append("@").append(Integer.toHexString(object.hashCode()));
		}

		return result.toString();
	}

	public static String getHashCode(Object object) {
		StringBuilder result = new StringBuilder();

		if (object != null) {
			result.append(" (").append(Integer.toHexString(object.hashCode())).append(")");
		}

		return result.toString();
	}

	public static void main(String[] args) {

		System.out.println(String.format("%04d", 1));
		System.out.println(String.format("%02d", 1));
		System.out.println(String.format("%02d", 9));
		System.out.println(String.format("%02d", 10));
		System.out.println(String.format("%02d", 11));
		System.out.println(String.format("%02d", 18));
		System.out.println(String.format("%02d", 20));
		System.out.println(String.format("%02d", 99));
		System.out.println(String.format("%02d", 100));

		System.out.println("--------------------------------------------------------------");

		System.out.println(padNumericString(1, 4));
		System.out.println(padNumericString(1, 2));
		System.out.println(padNumericString(9, 2));
		System.out.println(padNumericString(10, 2));
		System.out.println(padNumericString(11, 2));
		System.out.println(padNumericString(18, 2));
		System.out.println(padNumericString(20, 2));
		System.out.println(padNumericString(99, 2));
		System.out.println(padNumericString(100, 2));

		System.out.println("--------------------------------------------------------------");

		System.out.println(padNumericString(1));
		System.out.println(padNumericString(9));
		System.out.println(padNumericString(10));
		System.out.println(padNumericString(11));
		System.out.println(padNumericString(18));
		System.out.println(padNumericString(20));
		System.out.println(padNumericString(99));
		System.out.println(padNumericString(100));

	}

}
