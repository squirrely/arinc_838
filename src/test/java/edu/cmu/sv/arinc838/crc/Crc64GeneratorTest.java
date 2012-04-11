package edu.cmu.sv.arinc838.crc;

import static org.testng.Assert.assertEquals;

import java.math.BigInteger;
import java.util.Map;

import org.testng.annotations.Test;

public class Crc64GeneratorTest {
	@Test
	public void calculateCrcTest() throws Exception {
		Map<BigInteger, byte[]> expectedCrcs = CrcGeneratorTestCommon
				.getExpectedCrcs("crc64");
		for (BigInteger expectedCrc : expectedCrcs.keySet()) {
			long crc = Crc64Generator.calculateCrc(expectedCrcs
					.get(expectedCrc));
			assertEquals(crc, expectedCrc.longValue());
		}
	}
}