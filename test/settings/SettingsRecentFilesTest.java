package settings;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class SettingsRecentFilesTest {

	@Test
	public void test_twoTimesTheSameFile() {
		Settings setting = new Settings();
		File file = new File("tree.pgl");
		
		setting.getRecentFiles().add(file);
		setting.getRecentFiles().add(file);
		
		assertEquals(1, setting.getRecentFiles().size());
	}

	@Test
	public void test_addAtTheBeginning() {
		Settings setting = new Settings();
		File file1 = new File("tree1.pgl");
		File file2 = new File("tree2.pgl");
		
		setting.getRecentFiles().add(file1);
		setting.getRecentFiles().add(file2);
		
		assertTrue(setting.getRecentFiles().get(0).equals(file2));
	}
	
	@Test
	public void test_twoTimesTheSameFileAndOneAnother() {
		Settings setting = new Settings();
		File file1 = new File("tree1.pgl");
		File file2 = new File("tree2.pgl");
		
		setting.getRecentFiles().add(file1);
		setting.getRecentFiles().add(file2);
		setting.getRecentFiles().add(file1);
		
		assertEquals(2, setting.getRecentFiles().size());
	}
	
	@Test
	public void test_saveProperty_favorite() {
		Settings setting = new Settings();
		File file1 = new File("tree1.pgl");
		File file2 = new File("tree2.pgl");
		
		setting.getRecentFiles().add(file1);
		setting.getRecentFiles().get(0).setFavorite(true);
		setting.getRecentFiles().add(file2);
		setting.getRecentFiles().add(file1);
		
		assertTrue(setting.getRecentFiles().get(0).isFavorite());
	}
	
	@Test
	public void test_saveProperty_name() {
		Settings setting = new Settings();
		File file1 = new File("tree1.pgl");
		File file2 = new File("tree2.pgl");
		
		setting.getRecentFiles().add(file1);
		setting.getRecentFiles().get(0).setName("abc");
		setting.getRecentFiles().add(file2);
		setting.getRecentFiles().add(file1);
		
		assertEquals("abc", setting.getRecentFiles().get(0).getName());
	}

}
