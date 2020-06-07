package treeGraphs.stdDescendantsTG;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GraphParameters {

	public static final GraphParameters ZERO = new GraphParametersBuilder().build();
	
	public static final GraphParameters DEFAULT = new GraphParametersBuilder().
			spouseIndentation(20).
			minParentLineLength(3).
			childArrowLength(15).
			lineMargin(5).
			
			betweenSpousesSpace(4).
			betweenSiblingsSpace(10).
			betwennCousisnsSpace(20).
			
			beforeMarriageNumLineLength(2).
			marriageNumSpace(5).
			marriageNumMargin(2).
			
			build();
	
	private int spouseIndentation;
	private int minParentLineLength;
	private int childArrowLength;
	private int lineMargin;
	public int getBetweenGenerationsSpace() {
		return minParentLineLength + childArrowLength + lineMargin*2;
	}
	private int betweenSpousesSpace;
	private int betweenSiblingsSpace;
	private int betwennCousisnsSpace;
	
	//marriage number
	private int beforeMarriageNumLineLength;
	private int marriageNumSpace;
	private int marriageNumMargin;
	public int getMarriageNumOffset() {
		return beforeMarriageNumLineLength + marriageNumMargin;
	}
	public int getAfterMarriageNumLineLength() {
		return childArrowLength - getMarriageNumOffset() - marriageNumSpace;
	}
}
