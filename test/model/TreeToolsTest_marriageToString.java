package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Person.Sex;
import model.random.RandomPerson;

public class TreeToolsTest_marriageToString {

	private Tree tree;
	private TreeTools tools;
	private RandomPerson rPerson;
	
	@Before
	public void prepare() {
		tree = new Tree();
		tools = new TreeTools(tree);
		rPerson = new RandomPerson();
	}

	//-----marriageToString-----

	@Test
	public void marriageToString_onlySpouses() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		Marriage marriage = new Marriage(husband, wife);
		
		tree.addPerson("1", husband);
		tree.addPerson("2", wife);
		
		assertEquals("1;2;;", tools.marriageToString(marriage));
	}

	@Test
	public void marriageToString_onlyWife() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		Marriage marriage = new Marriage();
		marriage.setWife(wife);
		
		tree.addPerson("1", husband);
		tree.addPerson("2", wife);
		
		assertEquals(";2;;", tools.marriageToString(marriage));
	}

	@Test
	public void marriageToString_onlyPlace() {
		Marriage marriage = new Marriage();
		marriage.setPlace("Warsaw");
		
		assertEquals(";;;Warsaw", tools.marriageToString(marriage));
	}

	@Test
	public void marriageToString_withDate() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		Marriage marriage = new Marriage(husband, wife);
		marriage.setDate("1.2.2000");
		
		tree.addPerson("1", husband);
		tree.addPerson("2", wife);
		
		assertEquals("1;2;1.2.2000;", tools.marriageToString(marriage));
	}

	@Test
	public void marriageToString_withPlace() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		Marriage marriage = new Marriage(husband, wife);
		marriage.setPlace("Warsaw");
		
		tree.addPerson("1", husband);
		tree.addPerson("2", wife);
		
		assertEquals("1;2;;Warsaw", tools.marriageToString(marriage));
	}

	@Test
	public void marriageToString_withDateAndPlace() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		Marriage marriage = new Marriage(husband, wife);
		marriage.setDate("1.2.2000");
		marriage.setPlace("Warsaw");
		
		tree.addPerson("1", husband);
		tree.addPerson("2", wife);
		
		assertEquals("1;2;1.2.2000;Warsaw", tools.marriageToString(marriage));
	}

	@Test
	public void marriageToString_null() {
		assertNull(tools.marriageToString(null));
	}

	@Test
	public void marriageToString_empty() {
		assertEquals(";;;", tools.marriageToString(new Marriage()));
	}

	@Test
	public void marriageToString_onePersonBeyondTree() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		tree.addPerson("1", husband);
		Marriage marriage = new Marriage(husband, wife);
		
		assertNull(tools.marriageToString(marriage));
	}

	@Test
	public void marriageToString_personsBeyondTree() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		Marriage marriage = new Marriage(husband, wife);
		
		assertNull(tools.marriageToString(marriage));
	}
	
	//-----stringToMarriage-----

	@Test
	public void stringToMarriage() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		tree.addPerson("2", husband);
		tree.addPerson("3", wife);
		String originalString = "2;3;3.4.1990;Poznan";
		
		Marriage marriage = tools.stringToMarriage(originalString);
		
		assertEquals(husband, marriage.getHusband());
		assertEquals(wife, marriage.getWife());
		assertEquals("3.4.1990", marriage.getDate());
		assertEquals("Poznan", marriage.getPlace());
	}

	@Test
	public void stringToMarriage_null() {
		Marriage marriage = tools.stringToMarriage(null);
		assertNull(marriage);
	}

	@Test
	public void stringToMarriage_empty() {
		Marriage marriage = tools.stringToMarriage(";;;");
		assertNull(marriage.getHusband());
		assertNull(marriage.getWife());
		assertNull(marriage.getDate());
		assertNull(marriage.getPlace());
	}

	@Test
	public void stringToMarriage_withoutDate() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		tree.addPerson("2", husband);
		tree.addPerson("3", wife);
		String originalString = "2;3;;Poznan";
		
		Marriage marriage = tools.stringToMarriage(originalString);
		
		assertEquals(husband, marriage.getHusband());
		assertEquals(wife, marriage.getWife());
		assertNull(marriage.getDate());
		assertEquals("Poznan", marriage.getPlace());
	}

	@Test
	public void stringToMarriage_withoutPlace() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		tree.addPerson("2", husband);
		tree.addPerson("3", wife);
		String originalString = "2;3;3.4.1990;";
		
		Marriage marriage = tools.stringToMarriage(originalString);
		
		assertEquals(husband, marriage.getHusband());
		assertEquals(wife, marriage.getWife());
		assertEquals("3.4.1990", marriage.getDate());
		assertNull(marriage.getPlace());
	}

	@Test
	public void stringToMarriage_withoutDateAndPlace() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		tree.addPerson("2", husband);
		tree.addPerson("3", wife);
		String originalString = "2;3;;";
		
		Marriage marriage = tools.stringToMarriage(originalString);
		
		assertEquals(husband, marriage.getHusband());
		assertEquals(wife, marriage.getWife());
		assertNull(marriage.getDate());
		assertNull(marriage.getPlace());
	}

	@Test
	public void stringToMarriage_withoutSpouses() {
		String originalString = ";;3.4.1990;Poznan";
		
		Marriage marriage = tools.stringToMarriage(originalString);
		
		assertNull(marriage.getHusband());
		assertNull(marriage.getWife());
		assertEquals("3.4.1990", marriage.getDate());
		assertEquals("Poznan", marriage.getPlace());
	}

	@Test
	public void stringToMarriage_withoutWife() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		tree.addPerson("2", husband);
		tree.addPerson("3", wife);
		String originalString = "2;;3.4.1990;Poznan";
		
		Marriage marriage = tools.stringToMarriage(originalString);
		
		assertEquals(husband, marriage.getHusband());
		assertNull(marriage.getWife());
		assertEquals("3.4.1990", marriage.getDate());
		assertEquals("Poznan", marriage.getPlace());
	}

	@Test
	public void stringToMarriage_noPersonsInTheTree() {
		String originalString = "2;3;3.4.1990;Poznan";
		
		Marriage marriage = tools.stringToMarriage(originalString);
		
		assertNull(marriage);
	}

	@Test
	public void stringToMarriage_onePersonsInTheTree() {
		Person husband = rPerson.generate(Sex.MAN);
		tree.addPerson("2", husband);
		String originalString = "2;3;3.4.1990;Poznan";
		
		Marriage marriage = tools.stringToMarriage(originalString);
		
		assertNull(marriage);
	}

	@Test
	public void stringToMarriage_moreData() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		tree.addPerson("2", husband);
		tree.addPerson("3", wife);
		String originalString = "2;3;3.4.1990;Poznan;abcd";
		
		Marriage marriage = tools.stringToMarriage(originalString);
		
		assertNull(marriage);
	}
	
	//-----two-way

	@Test
	public void marriageToString_stringToMarriage() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		Marriage marriage = new Marriage(husband, wife);
		marriage.setDate("3.4.1990");
		marriage.setPlace("Poznan");
		
		tree.addPerson("2", husband);
		tree.addPerson("3", wife);
		
		String string = tools.marriageToString(marriage);
		Marriage m = tools.stringToMarriage(string);
		
		assertEquals(husband, m.getHusband());
		assertEquals(wife, m.getWife());
		assertEquals("3.4.1990", m.getDate());
		assertEquals("Poznan", m.getPlace());
	}

	@Test
	public void stringToMarriage_marriageToString() {
		Person husband = rPerson.generate(Sex.MAN);
		Person wife = rPerson.generate(Sex.WOMAN);
		tree.addPerson("2", husband);
		tree.addPerson("3", wife);
		
		String originalString = "2;3;3.4.1990;Poznan";
		
		Marriage marriage = tools.stringToMarriage(originalString);
		String computedString = tools.marriageToString(marriage);
		
		assertEquals(originalString, computedString);
	}
	
	//-----marriagesToString-----

	@Test
	public void marriagesToString() {
		Person husband1 = rPerson.generate(Sex.MAN);
		Person husband2 = rPerson.generate(Sex.MAN);
		Person husband3 = rPerson.generate(Sex.MAN);
		Person wife1 = rPerson.generate(Sex.WOMAN);
		Person wife2 = rPerson.generate(Sex.WOMAN);
		Person wife3 = rPerson.generate(Sex.WOMAN);
		tree.addPerson("5", husband1);
		tree.addPerson("6", wife1);
		tree.addPerson("7", husband2);
		tree.addPerson("8", wife2);
		tree.addPerson("9", husband3);
		tree.addPerson("1", wife3);
		
		Marriage marriage1 = new Marriage(husband1, wife1);
		Marriage marriage2 = new Marriage(husband2, wife2);
		Marriage marriage3 = new Marriage(husband3, wife3);
		
		List<Marriage> marriages = Arrays.asList(marriage1, marriage2, marriage3);
		
		String computedString = tools.marriagesToString(marriages);
		String expectedString = String.join(TreeTools.LINESEPARATOR,
				"5;6;;",
				"7;8;;",
				"9;1;;");
		
		assertEquals(expectedString, computedString);
	}

	@Test
	public void marriagesToString_null() {
		String computedString = tools.marriagesToString(null);
		assertNull(computedString);
	}

	@Test
	public void marriagesToString_empty() {
		String computedString = tools.marriagesToString(new ArrayList<>());
		assertEquals("", computedString);
	}

	@Test
	public void marriagesToString_onePersonBeyondTree() {
		Person husband1 = rPerson.generate(Sex.MAN);
		Person husband2 = rPerson.generate(Sex.MAN);
		Person husband3 = rPerson.generate(Sex.MAN);
		Person wife1 = rPerson.generate(Sex.WOMAN);
		Person wife2 = rPerson.generate(Sex.WOMAN);
		Person wife3 = rPerson.generate(Sex.WOMAN);
		tree.addPerson("5", husband1);
		tree.addPerson("6", wife1);
		tree.addPerson("7", husband2);
		tree.addPerson("8", wife2);
		tree.addPerson("9", husband3);
		
		Marriage marriage1 = new Marriage(husband1, wife1);
		Marriage marriage2 = new Marriage(husband2, wife2);
		Marriage marriage3 = new Marriage(husband3, wife3);
		
		List<Marriage> marriages = Arrays.asList(marriage1, marriage2, marriage3);
		
		String computedString = tools.marriagesToString(marriages);
		
		assertNull(computedString);
	}
	
	//-----stringToMarriages-----

	@Test
	public void stringToMarriages() {
		Person husband1 = rPerson.generate(Sex.MAN);
		Person husband2 = rPerson.generate(Sex.MAN);
		Person husband3 = rPerson.generate(Sex.MAN);
		Person wife1 = rPerson.generate(Sex.WOMAN);
		Person wife2 = rPerson.generate(Sex.WOMAN);
		Person wife3 = rPerson.generate(Sex.WOMAN);
		tree.addPerson("5", husband1);
		tree.addPerson("6", wife1);
		tree.addPerson("7", husband2);
		tree.addPerson("8", wife2);
		tree.addPerson("9", husband3);
		tree.addPerson("1", wife3);
		
		String string = String.join(TreeTools.LINESEPARATOR,
				"5;6;;",
				"7;8;;",
				"9;1;;");
		
		List<Marriage> marriages = tools.stringToMarriages(string);
		
		assertEquals(3, marriages.size());
		assertEquals(husband1, marriages.get(0).getHusband());
		assertEquals(husband2, marriages.get(1).getHusband());
		assertEquals(husband3, marriages.get(2).getHusband());
		assertEquals(wife1, marriages.get(0).getWife());
		assertEquals(wife2, marriages.get(1).getWife());
		assertEquals(wife3, marriages.get(2).getWife());
	}

	@Test
	public void stringToMarriages_withDateAndPlace() {
		Person husband1 = rPerson.generate(Sex.MAN);
		Person husband2 = rPerson.generate(Sex.MAN);
		Person husband3 = rPerson.generate(Sex.MAN);
		Person wife1 = rPerson.generate(Sex.WOMAN);
		Person wife2 = rPerson.generate(Sex.WOMAN);
		Person wife3 = rPerson.generate(Sex.WOMAN);
		tree.addPerson("5", husband1);
		tree.addPerson("6", wife1);
		tree.addPerson("7", husband2);
		tree.addPerson("8", wife2);
		tree.addPerson("9", husband3);
		tree.addPerson("1", wife3);
		
		String string = String.join(TreeTools.LINESEPARATOR,
				"5;6;;Warsaw",
				"7;8;;",
				"9;1;6.7.1999;");
		
		List<Marriage> marriages = tools.stringToMarriages(string);
		
		assertEquals(3, marriages.size());
		assertEquals(husband1, marriages.get(0).getHusband());
		assertEquals(husband2, marriages.get(1).getHusband());
		assertEquals(husband3, marriages.get(2).getHusband());
		assertEquals(wife1, marriages.get(0).getWife());
		assertEquals(wife2, marriages.get(1).getWife());
		assertEquals(wife3, marriages.get(2).getWife());
		assertEquals("Warsaw", marriages.get(0).getPlace());
		assertEquals("6.7.1999", marriages.get(2).getDate());
	}

	@Test
	public void stringToMarriages_emptyLine() {
		Person husband1 = rPerson.generate(Sex.MAN);
		Person husband2 = rPerson.generate(Sex.MAN);
		Person husband3 = rPerson.generate(Sex.MAN);
		Person wife1 = rPerson.generate(Sex.WOMAN);
		Person wife2 = rPerson.generate(Sex.WOMAN);
		Person wife3 = rPerson.generate(Sex.WOMAN);
		tree.addPerson("5", husband1);
		tree.addPerson("6", wife1);
		tree.addPerson("7", husband2);
		tree.addPerson("8", wife2);
		tree.addPerson("9", husband3);
		tree.addPerson("1", wife3);
		
		String string = String.join(TreeTools.LINESEPARATOR,
				"5;6;;Warsaw",
				"7;8;;",
				"",
				"9;1;6.7.1999;");
		
		List<Marriage> marriages = tools.stringToMarriages(string);
		
		assertEquals(3, marriages.size());
		assertEquals(husband1, marriages.get(0).getHusband());
		assertEquals(husband2, marriages.get(1).getHusband());
		assertEquals(husband3, marriages.get(2).getHusband());
		assertEquals(wife1, marriages.get(0).getWife());
		assertEquals(wife2, marriages.get(1).getWife());
		assertEquals(wife3, marriages.get(2).getWife());
		assertEquals("Warsaw", marriages.get(0).getPlace());
		assertEquals("6.7.1999", marriages.get(2).getDate());
	}

	@Test
	public void stringToMarriages_null() {
		List<Marriage> marriages = tools.stringToMarriages(null);
		assertNull(marriages);
	}

	@Test
	public void stringToMarriages_empty() {
		List<Marriage> marriages = tools.stringToMarriages("");
		assertEquals(0, marriages.size());
	}
}
