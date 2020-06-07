package treeGraphs.stdDescendantsTG;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class PersonLocationContainterTest {

	
	private static TestFamily testFamily;
	
	@BeforeClass
	public static void prepare() {
		testFamily = new TestFamily();
		testFamily.prepare();
	}
	
	@Test
	public void testFlatSize() {
		assertEquals(4, testFamily.family.flatSize());
	}

	@Test
	public void testDeepSize() {
		assertEquals(14, testFamily.family.deepSize());
	}

}
