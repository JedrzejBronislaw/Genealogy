package model.pgl.comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import model.pgl.PGL;

public class PGLDiffContainer {
	
	@EqualsAndHashCode
	@ToString
	@Getter
	@AllArgsConstructor
	public static class OtherValue {
		private String section;
		private String keyName;
	}

	@EqualsAndHashCode
	@ToString
	@Getter
	@AllArgsConstructor
	public static class AdditionalKey {
		private PGL pgl;
		private String section;
		private String keyName;
	}

	@EqualsAndHashCode
	@ToString
	@Getter
	@AllArgsConstructor
	public static class AdditionalSection {
		private PGL pgl;
		private String section;
	}

	@Getter
	@Setter(value = AccessLevel.PACKAGE)
	private PGL pgl1, pgl2;
	
	private List<OtherValue> otherValues               = new ArrayList<>();
	private List<AdditionalKey> additionalKeys         = new ArrayList<>();
	private List<AdditionalSection> additionalSections = new ArrayList<>();
	

	private Map<PGL, String> pglNames = new HashMap<>(2);
	
	public void setPGLName(@NonNull PGL pgl, String pglName) {
		pglNames.put(pgl, pglName);
	}
	public String getPGLName(PGL pgl) {
		String name = pglNames.get(pgl);
		return name != null ? name : "";
	}
	
	public List<OtherValue> getOtherValues() {
		return Collections.unmodifiableList(otherValues);
	}
	
	public List<AdditionalKey> getAdditionalKeys() {
		return Collections.unmodifiableList(additionalKeys);
	}
	
	public List<AdditionalSection> getAdditionalSections() {
		return Collections.unmodifiableList(additionalSections);
	}

	public void add(OtherValue diff) {
		otherValues.add(diff);
	}
	public void add(AdditionalKey diff) {
		additionalKeys.add(diff);
	}
	public void add(AdditionalSection diff) {
		additionalSections.add(diff);
	}
	
	public int size() {
		return otherValues.size() +
		       additionalKeys.size() +
		       additionalSections.size();
	}
	
	public PGLDiffContainer clear() {
		otherValues.clear();
		additionalKeys.clear();
		additionalSections.clear();
		
		return this;
	}
}
