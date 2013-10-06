package sk.sochuliak.barabasi.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.NumberFormat;

import org.junit.Test;

public class CommonUtilsTest {

	@Test
	public void testIsNumber() {
		assertFalse(CommonUtils.isNumber("hello world"));
		assertTrue(CommonUtils.isNumber("99"));
		assertTrue(CommonUtils.isNumber("-99"));
	}
	
	@Test
	public void testIsPositiveNumber() {
		assertFalse(CommonUtils.isPositiveNumber("hello world"));
		assertTrue(CommonUtils.isPositiveNumber("99"));
		assertFalse(CommonUtils.isPositiveNumber("-99"));
	}
	
	@Test
	public void testSumOfDoubleArray() {
		double[] array = new double[]{0.42, 0.13, 5.28, 1.15, 3.14};
		double expectedSum = 10.12;
		double actualSum = CommonUtils.sumOfDoubleArray(array);
		
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		
		String expectedSumAsString = nf.format(expectedSum);
		String actualSumAsString = nf.format(actualSum);
		
		assertEquals(expectedSumAsString, actualSumAsString);
	}
}
