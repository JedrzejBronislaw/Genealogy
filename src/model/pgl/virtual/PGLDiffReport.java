package model.pgl.virtual;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;
import model.pgl.PGLFields;
import model.pgl.virtual.Differences.AdditionalKey;
import model.pgl.virtual.Differences.AdditionalSection;
import model.pgl.virtual.Differences.OtherValue;

public class PGLDiffReport {
	
	public static final String newLine = System.lineSeparator();

	@NonNull Differences diff;
	private List<AdditionalKey> notEmptyAdditionalKeys = new ArrayList<>();
	private List<AdditionalKey>    emptyAdditionalKeys = new ArrayList<>();

	public PGLDiffReport(Differences diff) {
		this.diff = diff;
		
		splitAdditionalKeyList();
	}
	
	private void splitAdditionalKeyList() {
		for(int i=0; i<keys().size(); i++) {
			AdditionalKey key = keys().get(i);
			
			if (emptyKeyFilter(key))
				emptyAdditionalKeys.add(key);
			else
				notEmptyAdditionalKeys.add(key);
		}
	}

	private List<OtherValue> values() {
		return diff.getOtherValues();
	}

	private List<AdditionalKey> keys() {
		return diff.getAdditionalKeys();
	}

	private List<AdditionalKey> emptyKeys() {
		return emptyAdditionalKeys;
	}

	private List<AdditionalKey> nEmptyKeys() {
		return notEmptyAdditionalKeys;
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

	private StringBuffer nEmptyKeyList() {
		StringBuffer sb = new StringBuffer();
		
		for(int i=0; i<nEmptyKeys().size(); i++)
			sb.append((i+1) + ". " + toString(nEmptyKeys().get(i)) + newLine);
		
		return sb;
	}

	private StringBuffer valuesList() {
		StringBuffer sb = new StringBuffer();
		
		for(int i=0; i<values().size(); i++)
			sb.append((i+1) + ". " + toString(values().get(i)) + newLine);
		
		return sb;
	}
	
	
	private boolean emptyKeyFilter(AdditionalKey diff) {
		String name = diff.getKeyName();
		String value = diff.getPgl().getValue(diff.getSection(), diff.getKeyName());
		
		if (value.equals("")) {
			return true;
		}
		
		if ((name.equals(PGLFields.sex) || name.equals(PGLFields.lifeStatus)) &&
			value.equals("-1")) {
			return true;
		}
		
		if ((name.equals(PGLFields.children) || name.equals(PGLFields.marriages)) &&
			(value.equals("0"))) {
			return true;
		}
		
		if ((name.equals(PGLFields.birthDate) || name.equals(PGLFields.deathDate) || name.startsWith(PGLFields.weddingDate)) &&
			(value.equals("..") || value.equals("0.0.0") ||
			 value.equals("0..") || value.equals(".0.") || value.equals("..0") ||
			 value.equals(".0.0") || value.equals("0..0") || value.equals("0.0."))) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("Differences: " + diff.size());
		sb.append(" (");
		sb.append("sections: " + sections().size() + " ");
		sb.append("keys: " + keys().size() + " ");
		sb.append("[" + emptyKeys().size() + " empty] ");
		sb.append("value: " + values().size());
		sb.append(")");
		sb.append(newLine);
		
		sb.append("  Sections:" + newLine);
		sb.append(sectionsList());
		sb.append(newLine);
		
		sb.append("  Not empty keys:" + newLine);
		sb.append(nEmptyKeyList());
		sb.append(newLine);
		
		sb.append("  Values:" + newLine);
		sb.append(valuesList());
		sb.append(newLine + newLine);
		
		return sb.toString();
	}
}
