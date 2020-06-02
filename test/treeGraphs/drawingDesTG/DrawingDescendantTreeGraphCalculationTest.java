package treeGraphs.drawingDesTG;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import treeGraphs.drawingDesTG.DrawingDescendantTreeGraphCalculation;
import treeGraphs.painter.Point;

public class DrawingDescendantTreeGraphCalculationTest {


	@Test
	public void stNaRad_0()
	{
		assertEquals(DrawingDescendantTreeGraphCalculation.degreeToRad(0),   0, 0);
	}
	@Test
	public void stNaRad_180()
	{
		assertEquals(DrawingDescendantTreeGraphCalculation.degreeToRad(180), Math.PI,   0);
	}
	@Test
	public void stNaRad_90()
	{
		assertEquals(DrawingDescendantTreeGraphCalculation.degreeToRad(90),  Math.PI/2, 0);
	}
	@Test
	public void stNaRad_360()
	{
		assertEquals(DrawingDescendantTreeGraphCalculation.degreeToRad(360), 0, 0);
	}
	
	@Test
	public void procOkreguNaRad_0()
	{
		assertEquals(DrawingDescendantTreeGraphCalculation.circlePercentageToRad(0), 0, 0);
	}
	@Test
	public void procOkreguNaRad_pol()
	{
		assertEquals(DrawingDescendantTreeGraphCalculation.circlePercentageToRad(0.5), Math.PI, 0);
	}

	@Test
	public void procObszaruNaRad_180_180_0()
	{
		DrawingDescendantTreeGraphCalculation graf = new DrawingDescendantTreeGraphCalculation(null);
		graf.setStartAngle(180);
		graf.setWholeAngle(180);
		assertEquals(graf.areaPercentageToRad(0), 0, 0);
	}
	@Test
	public void procObszaruNaRad_180_180_05()
	{
		DrawingDescendantTreeGraphCalculation graf = new DrawingDescendantTreeGraphCalculation(null);
		graf.setStartAngle(180);
		graf.setWholeAngle(180);
		assertEquals(graf.areaPercentageToRad(0.5), Math.PI/2, 0);
	}
	@Test
	public void procObszaruNaRad_180_180_1()
	{
		DrawingDescendantTreeGraphCalculation graf = new DrawingDescendantTreeGraphCalculation(null);
		graf.setStartAngle(180);
		graf.setWholeAngle(180);
		assertEquals(graf.areaPercentageToRad(1), Math.PI, 0);
	}

	@Test
	public void procObszaruNaRad_135_90_0()
	{
		DrawingDescendantTreeGraphCalculation graf = new DrawingDescendantTreeGraphCalculation(null);
		graf.setStartAngle(135);
		graf.setWholeAngle(90);
		assertEquals(graf.areaPercentageToRad(0), Math.PI/4, 0);
	}
	@Test
	public void procObszaruNaRad_135_90_08()
	{
		DrawingDescendantTreeGraphCalculation graf = new DrawingDescendantTreeGraphCalculation(null);
		graf.setStartAngle(135);
		graf.setWholeAngle(90);
		assertEquals(graf.areaPercentageToRad(0.8), DrawingDescendantTreeGraphCalculation.degreeToRad(117), 0);
	}
	@Test
	public void procObszaruNaRad_135_90_1()
	{
		DrawingDescendantTreeGraphCalculation graf = new DrawingDescendantTreeGraphCalculation(null);
		graf.setStartAngle(135);
		graf.setWholeAngle(90);
		assertEquals(graf.areaPercentageToRad(1), Math.PI*3/4, 0);
	}

	@Test
	public void katRadNaPunkt_1()
	{
		Point punkt = DrawingDescendantTreeGraphCalculation.radAngleToPoint(0, 100, new Point(0,0));
		assertTrue(punkt.getX() == 100 && punkt.getY() == 0);
	}
	@Test
	public void katRadNaPunkt_2()
	{
		Point punkt = DrawingDescendantTreeGraphCalculation.radAngleToPoint(Math.PI/2, 100, new Point(0,0));
		assertEquals(punkt.getX(), 0);
		assertEquals(punkt.getY(), -100);
	}
	@Test
	public void katRadNaPunkt_3()
	{
		Point punkt = DrawingDescendantTreeGraphCalculation.radAngleToPoint(Math.PI, 100, new Point(0,0));
		assertTrue(punkt.getX() == -100 && punkt.getY() == 0);
	}
	@Test
	public void katRadNaPunkt_4()
	{
		Point punkt = DrawingDescendantTreeGraphCalculation.radAngleToPoint(Math.PI/4, 100, new Point(0,0));
		assertEquals(punkt.getX(), 70, 0);
		assertEquals(punkt.getY(), -70, 0);
	}
	@Test
	public void katRadNaPunkt_5()
	{
		Point punkt = DrawingDescendantTreeGraphCalculation.radAngleToPoint(0, 500, new Point(20,10));
//		assertTrue(punkt.x == 520 && punkt.y == 10);
		assertEquals(punkt.getX(), 520);
		assertEquals(punkt.getY(), 10);
	}
}
