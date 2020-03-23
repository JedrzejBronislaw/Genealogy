package grafyDrzewa;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

public class GrafPotomkowieRysunekObliczeniaTest {


	@Test
	public void stNaRad_0()
	{
		assertEquals(GrafPotomkowieRysunekObliczenia.stNaRad(0),   0, 0);
	}
	@Test
	public void stNaRad_180()
	{
		assertEquals(GrafPotomkowieRysunekObliczenia.stNaRad(180), Math.PI,   0);
	}
	@Test
	public void stNaRad_90()
	{
		assertEquals(GrafPotomkowieRysunekObliczenia.stNaRad(90),  Math.PI/2, 0);
	}
	@Test
	public void stNaRad_360()
	{
		assertEquals(GrafPotomkowieRysunekObliczenia.stNaRad(360), 0, 0);
	}
	
	@Test
	public void procOkreguNaRad_0()
	{
		assertEquals(GrafPotomkowieRysunekObliczenia.procOkreguNaRad(0), 0, 0);
	}
	@Test
	public void procOkreguNaRad_pol()
	{
		assertEquals(GrafPotomkowieRysunekObliczenia.procOkreguNaRad(0.5), Math.PI, 0);
	}

	@Test
	public void procObszaruNaRad_180_180_0()
	{
		GrafPotomkowieRysunekObliczenia graf = new GrafPotomkowieRysunekObliczenia(null);
		graf.setKatPocz(180);
		graf.setKatCaly(180);
		assertEquals(graf.procObszaruNaRad(0), 0, 0);
	}
	@Test
	public void procObszaruNaRad_180_180_05()
	{
		GrafPotomkowieRysunekObliczenia graf = new GrafPotomkowieRysunekObliczenia(null);
		graf.setKatPocz(180);
		graf.setKatCaly(180);
		assertEquals(graf.procObszaruNaRad(0.5), Math.PI/2, 0);
	}
	@Test
	public void procObszaruNaRad_180_180_1()
	{
		GrafPotomkowieRysunekObliczenia graf = new GrafPotomkowieRysunekObliczenia(null);
		graf.setKatPocz(180);
		graf.setKatCaly(180);
		assertEquals(graf.procObszaruNaRad(1), Math.PI, 0);
	}

	@Test
	public void procObszaruNaRad_135_90_0()
	{
		GrafPotomkowieRysunekObliczenia graf = new GrafPotomkowieRysunekObliczenia(null);
		graf.setKatPocz(135);
		graf.setKatCaly(90);
		assertEquals(graf.procObszaruNaRad(0), Math.PI/4, 0);
	}
	@Test
	public void procObszaruNaRad_135_90_08()
	{
		GrafPotomkowieRysunekObliczenia graf = new GrafPotomkowieRysunekObliczenia(null);
		graf.setKatPocz(135);
		graf.setKatCaly(90);
		assertEquals(graf.procObszaruNaRad(0.8), GrafPotomkowieRysunekObliczenia.stNaRad(117), 0);
	}
	@Test
	public void procObszaruNaRad_135_90_1()
	{
		GrafPotomkowieRysunekObliczenia graf = new GrafPotomkowieRysunekObliczenia(null);
		graf.setKatPocz(135);
		graf.setKatCaly(90);
		assertEquals(graf.procObszaruNaRad(1), Math.PI*3/4, 0);
	}

	@Test
	public void katRadNaPunkt_1()
	{
		Point punkt = GrafPotomkowieRysunekObliczenia.katRadNaPunkt(0, 100, new Point(0,0));
		assertTrue(punkt.x == 100 && punkt.y == 0);
	}
	@Test
	public void katRadNaPunkt_2()
	{
		Point punkt = GrafPotomkowieRysunekObliczenia.katRadNaPunkt(Math.PI/2, 100, new Point(0,0));
		assertEquals(punkt.x, 0);
		assertEquals(punkt.y, -100);
	}
	@Test
	public void katRadNaPunkt_3()
	{
		Point punkt = GrafPotomkowieRysunekObliczenia.katRadNaPunkt(Math.PI, 100, new Point(0,0));
		assertTrue(punkt.x == -100 && punkt.y == 0);
	}
	@Test
	public void katRadNaPunkt_4()
	{
		Point punkt = GrafPotomkowieRysunekObliczenia.katRadNaPunkt(Math.PI/4, 100, new Point(0,0));
		assertEquals(punkt.x, 70, 0);
		assertEquals(punkt.y, -70, 0);
	}
	@Test
	public void katRadNaPunkt_5()
	{
		Point punkt = GrafPotomkowieRysunekObliczenia.katRadNaPunkt(0, 500, new Point(20,10));
//		assertTrue(punkt.x == 520 && punkt.y == 10);
		assertEquals(punkt.x, 520);
		assertEquals(punkt.y, 10);
	}
}
