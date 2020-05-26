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
	
}
