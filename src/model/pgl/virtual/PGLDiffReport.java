package model.pgl.virtual;

import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import model.pgl.virtual.Differences.AdditionalKey;
import model.pgl.virtual.Differences.AdditionalSection;
import model.pgl.virtual.Differences.OtherValue;

@RequiredArgsConstructor
public class PGLDiffReport {
	
	public static final String newLine = System.lineSeparator();

	@NonNull Differences diff;

	private List<OtherValue> values() {
		return diff.getOtherValues();
	}

	private List<AdditionalKey> keys() {
		return diff.getAdditionalKeys();
	}

	private List<AdditionalSection> sections() {
		return diff.getAdditionalSections();
	}
	
	
	
	private String toString(AdditionalSection d) {
		return "S: " + diff.getPGLName(d.getPgl()) + " - " + d.getSection();
	}
	
	private String toString(AdditionalKey d) {
		return "K: " + diff.getPGLName(d.getPgl()) + " - " + d.getSection() + " - " + d.getKeyName() + " - " +
				d.getPgl().getValue(d.getSection(), d.getKeyName());
	}
	
	private String toString(OtherValue d) {
		return "V: " + d.getSection() + " - " + d.getKeyName() + " - " +
				diff.getPgl1().getValue(d.getSection(), d.getKeyName()) + "=/=" +
				diff.getPgl2().getValue(d.getSection(), d.getKeyName());
	}


	
	private StringBuffer sectionsList() {
		StringBuffer sb = new StringBuffer();
		
		for(int i=0; i<sections().size(); i++)
			sb.append((i+1) + ". " + toString(sections().get(i)) + newLine);
		
		return sb;
	}

	private StringBuffer keyList() {
		StringBuffer sb = new StringBuffer();
		
		for(int i=0; i<keys().size(); i++)
			sb.append((i+1) + ". " + toString(keys().get(i)) + newLine);
		
		return sb;
	}

	private StringBuffer valuesList() {
		StringBuffer sb = new StringBuffer();
		
		for(int i=0; i<values().size(); i++)
			sb.append((i+1) + ". " + toString(values().get(i)) + newLine);
		
		return sb;
	}
	
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("Differences: " + diff.size());
		sb.append(" (");
		sb.append("sections: " + sections().size() + " ");
		sb.append("keys: " + keys().size() + " ");
		sb.append("value: " + values().size());
		sb.append(")");
		sb.append(newLine);
		
		sb.append("  Sections:" + newLine);
		sb.append(sectionsList());
		sb.append(newLine);
		
		sb.append("  Keys:" + newLine);
		sb.append(keyList());
		sb.append(newLine);
		
		sb.append("  Values:" + newLine);
		sb.append(valuesList());
		sb.append(newLine + newLine);
		
		return sb.toString();
	}
}
