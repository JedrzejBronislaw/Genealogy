package model.pgl.virtual;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import model.pgl.PGLFields;
import model.pgl.virtual.Differences.AdditionalKey;
import model.pgl.virtual.Differences.AdditionalSection;
import model.pgl.virtual.Differences.OtherValue;

public class PGLDiffReport {
	
	public static final String newLine = System.lineSeparator();

	public static final PGLDiffReport FILENOTFOUND = new PGLDiffReport(false);

	@NonNull Differences diff;
	private List<AdditionalKey> notEmptyAdditionalKeys = new ArrayList<>();
	private List<AdditionalKey>    emptyAdditionalKeys = new ArrayList<>();
	private List<OtherValue> similarOtherValues = new ArrayList<>();
	private List<OtherValue>    realOtherValues = new ArrayList<>();
	
	@Getter
	private boolean permissionToOpen = false;
	
	private PGLDiffReport(boolean permissionToOpen) {
		this.diff = new Differences();
		this.permissionToOpen = permissionToOpen;
	}
	
	public PGLDiffReport(Differences diff) {
		this.diff = diff;
		
		splitAdditionalKeyList();
		splitOtherValueList();
		setPermissionToOpen();
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
	
	private void splitOtherValueList() {
		for(int i=0; i<values().size(); i++) {
			OtherValue value = values().get(i);
			
			if (equalValueFilter(value))
				similarOtherValues.add(value);
			else
				realOtherValues.add(value);
		}
	}
	
	private void setPermissionToOpen() {
		permissionToOpen =
				sections().size()    == 0 &&
				nEmptyKeys().size()  == 0 &&
				otherValues().size() == 0;
	}

	private List<OtherValue> values() {
		return diff.getOtherValues();
	}

	private List<OtherValue> otherValues() {
		return realOtherValues;
	}

	private List<OtherValue> similarValues() {
		return similarOtherValues;
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

	private StringBuffer otherValuesList() {
		StringBuffer sb = new StringBuffer();
		
		for(int i=0; i<otherValues().size(); i++)
			sb.append((i+1) + ". " + toString(otherValues().get(i)) + newLine);
		
		return sb;
	}

	
	private boolean isIgnored(String key) {
		if (key.equals(PGLFields.lastOpen) ||
			key.equals(PGLFields.lastModification) ||
			key.equals(PGLFields.numberOfPersons) ||
			PGLFields.isListElement(PGLFields.commonSurname, key))
			return true;
		
		return false;
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
			(value.equals("0") || value.startsWith("-"))) {
			return true;
		}
		
		if ((name.equals(PGLFields.birthDate) || name.equals(PGLFields.deathDate) || name.startsWith(PGLFields.weddingDate)) &&
			(value.equals("..") || value.equals("0.0.0") ||
			 value.equals("0..") || value.equals(".0.") || value.equals("..0") ||
			 value.equals(".0.0") || value.equals("0..0") || value.equals("0.0."))) {
			return true;
		}
		
		if (!PGLFields.contains(name)) return true;
		
		if (isIgnored(name)) return true;
		
		return false;
	}

	private boolean equalValueFilter(OtherValue d) {
		String section = d.getSection();
		String key = d.getKeyName();
		String value1 = diff.getPgl1().getValue(section, key);
		String value2 = diff.getPgl2().getValue(section, key);

		if ((key.equals(PGLFields.birthDate) || key.equals(PGLFields.deathDate) || key.startsWith(PGLFields.weddingDate)) &&
			(compareDates(value1, value2)))
			return true;

		if (isIgnored(key)) return true;
		
		return false;
	}
	
	private boolean compareDates(String date1, String date2) {
		String[] d1 = date1.split("[.]");
		String[] d2 = date2.split("[.]");
		
		int len1 = d1.length;
		int len2 = d2.length;
		
		if (len1 != len2) return false;
		
		for (int i=0; i<len1; i++)
			if (!cutBeginZeros(d1[i]).equals(cutBeginZeros(d2[i])))
				return false;
		
		return true;
	}
	
	private String cutBeginZeros(String text) {
		int zeros =  0;
		
		for (int i=0; i<text.length(); i++)
			if (text.charAt(i) == '0') zeros++;
		
		return text.substring(zeros);
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("Differences: " + diff.size());
		sb.append(" (");
		sb.append("sections: " + sections().size() + " ");
		sb.append("keys: " + keys().size() + " ");
		sb.append("[" + emptyKeys().size() + " empty] ");
		sb.append("value: " + values().size() + " ");
		sb.append("[" + similarValues().size() + " similar]");
		sb.append(")");
		sb.append(newLine);
		
		sb.append("  Sections:" + newLine);
		sb.append(sectionsList());
		sb.append(newLine);
		
		sb.append("  Not empty keys:" + newLine);
		sb.append(nEmptyKeyList());
		sb.append(newLine);
		
		sb.append("  Real other values:" + newLine);
		sb.append(otherValuesList());
		sb.append(newLine + newLine);
		
		return sb.toString();
	}
}
