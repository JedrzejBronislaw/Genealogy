package treeGraphs.painter;

public class Gradient {

	private MyColor c1, c2;

	public Gradient(MyColor c1, MyColor c2) {
		this.c1 = c1;
		this.c2 = c2;
	}
	
	public MyColor getIntermediateColor(float percent)
	{
		int r, r1, r2;
		int g, g1, g2;
		int b, b1, b2;
		
		r1  = c1.getRed();
		r2  = c2.getRed();
		g1  = c1.getGreen();
		g2  = c2.getGreen();
		b1  = c1.getBlue();
		b2  = c2.getBlue();
		
		r = (int) (r1 + (r2-r1)*percent);
		g = (int) (g1 + (g2-g1)*percent);
		b = (int) (b1 + (b2-b1)*percent);
		
		return new MyColor(r,g,b);
	}
}
