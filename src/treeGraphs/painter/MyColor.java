package treeGraphs.painter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyColor {
	
	public static final int MAX = 255;
	
	public static final MyColor BLACK = new MyColor(0,   0,   0,   MAX);
	public static final MyColor WHITE = new MyColor(MAX, MAX, MAX, MAX);
	public static final MyColor GREEN = new MyColor(0,   MAX, 0,   MAX);
	
	public static final MyColor BROWN = new MyColor(150, 75,  0,   MAX);
	

	private int red = 0;
	private int green = 0;
	private int blue = 0;
	private int alpha = MAX;
	
	public MyColor(int red, int green, int blue) {
		this.red   = red;
		this.green = green;
		this.blue  = blue;
		this.alpha = MAX;
	}
	
	public MyColor(float red, float green, float blue) {
		this.red   = (int) (red   * MAX);
		this.green = (int) (green * MAX);
		this.blue  = (int) (blue  * MAX);
		this.alpha = MAX;
	}
	
	public MyColor(float red, float green, float blue, float alpha) {
		this.red   = (int) (red   * MAX);
		this.green = (int) (green * MAX);
		this.blue  = (int) (blue  * MAX);
		this.alpha = (int) (alpha * MAX);
	}
}
