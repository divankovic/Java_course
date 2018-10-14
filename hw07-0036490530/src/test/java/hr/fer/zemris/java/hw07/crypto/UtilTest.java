package hr.fer.zemris.java.hw07.crypto;

import org.junit.Test;

import hr.fer.zemris.java.hw07.crypto.Util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class UtilTest {

	@Test
	public void hextobyteEmptyTest() {
		assertArrayEquals(new byte[0], Util.hextobyte(""));
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalSizehextobyteTest() {
		Util.hextobyte("aab");
	}

	@Test
	public void illegalCharacterhextobyteTest() {

	}

	@Test
	public void hexttobyteTest() {

		assertArrayEquals(new byte[] { 1, -82, 34 }, Util.hextobyte("01aE22"));
		assertArrayEquals(new byte[] { -69, -82, 33 }, Util.hextobyte("bBaE21"));
		assertArrayEquals(new byte[] { 15, 0, 46 }, Util.hextobyte("0F002E"));
		assertArrayEquals(new byte[] { -1, 2, 14 }, Util.hextobyte("FF020E"));

	}
	
	@Test
	public void bytetohexEmptyTest() {
		assertEquals("", Util.bytetohex(new byte[0]));
	}
	
	@Test
	public void bytetohexTest() {

		assertEquals("01ae22", Util.bytetohex(new byte[] { 1, -82, 34 }));
		assertEquals("bbae21", Util.bytetohex(new byte[] { -69, -82, 33 }));
		assertEquals("0f002e", Util.bytetohex(new byte[] { 15, 0, 46 }));
		assertEquals("ff020e", Util.bytetohex(new byte[] { -1, 2, 14 }));
		
	}

}
