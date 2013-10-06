package sk.sochuliak.barabasi.utils;

public class CommonUtils {

	/**
	 * Checks if parameters is a number
	 * 
	 * @param number number
	 * @return true if parameters is a number, false otherwise 
	 */
	public static boolean isNumber(String number) {
		try {
			Integer.parseInt(number);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Checks if parameters is a positive number
	 * 
	 * @param number number
	 * @return true if parameters is a positive number, false otherwise
	 */
	public static boolean isPositiveNumber(String number) {
		return CommonUtils.isNumber(number) && Integer.parseInt(number) > 0;
	}
	
	/**
	 * Returns sum of double values in array
	 * 
	 * @param array Array of double values
	 * @return Sum
	 */
	public static double sumOfDoubleArray(double[] array) {
		double sum = 0d;
		for (double value : array) {
			sum += value;
		}
		return sum;
	}
}
