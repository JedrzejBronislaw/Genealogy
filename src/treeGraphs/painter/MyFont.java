package treeGraphs.painter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyFont {

	public static enum Style{REGULAR, BOLD, BOLD_ITALIC, ITALIC};
	
	private String name;
	private Style style;
	private int size;

	public MyFont copy(String name) {
		return new MyFont(name, style, size);
	}
	public MyFont copy(Style style) {
		return new MyFont(name, style, size);
	}
	public MyFont copy(int size) {
		return new MyFont(name, style, size);
	}
	public MyFont copy(double sizeFactor) {
		return new MyFont(name, style, (int)(size*sizeFactor));
	}
}
