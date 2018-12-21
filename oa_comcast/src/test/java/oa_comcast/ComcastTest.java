package oa_comcast;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ComcastTest {
	private Comcast x1;
	
	@Before
    public void setUp() {
        x1 = new Comcast();
    }
	
	@Test
	public void matchHexFile() {
		final String[] arr = {"-f", "-x"};
		final String HEXFILE = "HexFile";
		Assert.assertEquals(x1.checkMode(arr), HEXFILE);
	}
	
	@Test
	public void matchStandardFile() {
		final String[] arr = {"-f"};
		final String STANDARDFILE = "StandardFile";
		Assert.assertEquals(x1.checkMode(arr), STANDARDFILE);
	}
	
	@Test
	public void matchStandardHex() {
		final String[] arr = {"-x"};
		final String STANDARDHEX = "StandardHex";
		Assert.assertEquals(x1.checkMode(arr), STANDARDHEX);
	}
	
	@Test
	public void matchStandardWithWrongParams() {
		final String[] arr = {"-xg", "sdfsdfw", "-ft"};
		final String STANDARD = "Standard";
		Assert.assertEquals(x1.checkMode(arr), STANDARD);
	}
	
	@Test
	public void matchStandardFileWithWrongParams() {
		final String[] arr = {"-xd", "342", "-f"};
		final String STANDARDFILE = "StandardFile";
		Assert.assertEquals(x1.checkMode(arr), STANDARDFILE);
	}
	
	@Test
	public void matchStandard() {
		final String[] arr = {};
		final String STANDARD = "Standard";
		Assert.assertEquals(x1.checkMode(arr), STANDARD);
	}
	
	@Test
	public void notMatchStandard() {
		final String[] arr = {};
		final String NOTSTANDARD = "StandardFile";
		Assert.assertNotEquals(x1.checkMode(arr), NOTSTANDARD);
	}
	
	@Test
	public void checkSum() throws Exception {
		String line = "abc123";
		Assert.assertEquals(x1.sum(5, line), 11);
	}
	
	@Test
	public void checkStandardFile() throws Exception {
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("abc123");
		lines.add("123abc");
		lines.add("a456b789c123");
		Assert.assertEquals(x1.standardFile(3, lines), 60);
	}
	
	@Test
	public void check10fHexToDec() throws Exception {
		String expect = "1099511627775";
		Assert.assertEquals(x1.HexToDec("ffffffffff"), expect);
	}
	
	@Test
	public void checkMixHexToDec() throws Exception {
		String expect = "1252776078422";
		Assert.assertEquals(x1.HexToDec("123af45ec56"), expect);
	}
	
	@Test
	public void checkMixHexAndSum() throws Exception {
		long expect = 100;
		Assert.assertEquals(x1.hexAndSum(9, "123axf45exc56"), expect);
	}
	
	@Test
	public void checkAToFHexAndSum() throws Exception {
		long expect = 50;
		Assert.assertEquals(x1.hexAndSum(8, "abcyzdefop"), expect);
	}
	
	
	@After
    public void tearDown() {
        x1 = null;
    }
}
