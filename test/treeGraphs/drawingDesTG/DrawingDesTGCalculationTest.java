package treeGraphs.drawingDesTG;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import treeGraphs.painter.Point;

public class DrawingDesTGCalculationTest {

	private double degreeToRad(double degree) {
		return DrawingDesTGCalculation.degreeToRad(degree);
	}
	
	@Test
	public void degreeToRad_0() {
		assertEquals(0, degreeToRad(0), 0);
	}
	
	@Test
	public void degreeToRad_180() {
		assertEquals(Math.PI, degreeToRad(180), 0);
	}
	
	@Test
	public void degreeToRad_90() {
		assertEquals(Math.PI/2, degreeToRad(90), 0);
	}
	
	@Test
	public void degreeToRad_360() {
		assertEquals(0, degreeToRad(360), 0);
	}
	
	
	
	private double circlePercentageToRad(double percent) {
		return DrawingDesTGCalculation.circlePercentageToRad(percent);
	}
	
	@Test
	public void circlePercToRad_0() {
		assertEquals(0, circlePercentageToRad(0), 0);
	}
	
	@Test
	public void circlePercToRad_half() {
		assertEquals(Math.PI, circlePercentageToRad(0.5), 0);
	}

	
	
	private DrawingDesTGCalculation graph(float startAngle, float wholeAngle) {
		DrawingDesTGCalculation graph = new DrawingDesTGCalculation(null);
		graph.setStartAngle(startAngle);
		graph.setWholeAngle(wholeAngle);
		return graph;
	}

	@Test
	public void areaPercToRad_180_180_0() {
		assertEquals(0, graph(180, 180).areaPercentageToRad(0), 0);
	}
	
	@Test
	public void areaPercToRad_180_180_05() {
		assertEquals(Math.PI/2, graph(180, 180).areaPercentageToRad(0.5), 0);
	}
	
	@Test
	public void areaPercToRad_180_180_1() {
		assertEquals(Math.PI, graph(180, 180).areaPercentageToRad(1), 0);
	}

	@Test
	public void areaPercToRad_135_90_0() {
		assertEquals(Math.PI/4, graph(135, 90).areaPercentageToRad(0), 0);
	}
	
	@Test
	public void areaPercToRad_135_90_08() {
		assertEquals(DrawingDesTGCalculation.degreeToRad(117), graph(135, 90).areaPercentageToRad(0.8), 0);
	}
	
	@Test
	public void areaPercToRad_135_90_1() {
		assertEquals(Math.PI*3/4, graph(135, 90).areaPercentageToRad(1), 0);
	}

	

	private Point radAngleToPoint(double angle, double distance, Point point) {
		return DrawingDesTGCalculation.radAngleToPoint(angle, distance, point);
	}
	
	@Test
	public void angleToPoint_1() {
		Point point = radAngleToPoint(0, 100, new Point(0, 0));
		assertEquals(new Point(100, 0), point);
	}
	
	@Test
	public void angleToPoint_2() {
		Point point = radAngleToPoint(Math.PI/2, 100, new Point(0, 0));
		assertEquals(new Point(0, -100), point);
	}
	
	@Test
	public void angleToPoint_3() {
		Point point = radAngleToPoint(Math.PI, 100, new Point(0, 0));
		assertEquals(new Point(-100, 0), point);
	}
	
	@Test
	public void angleToPoint_4() {
		Point point = radAngleToPoint(Math.PI/4, 100, new Point(0, 0));
		assertEquals(new Point(70, -70), point);
	}
	
	@Test
	public void angleToPoint_5() {
		Point point = radAngleToPoint(0, 500, new Point(20, 10));
		assertEquals(new Point(520, 10), point);
	}
}
