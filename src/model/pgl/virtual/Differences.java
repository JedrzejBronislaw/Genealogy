package model.pgl.virtual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

public class Differences {
	
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
		private VirtualPGL pgl;
		private String section;
		private String keyName;
	}

	@EqualsAndHashCode
	@ToString
	@Getter
	@AllArgsConstructor
	public static class AdditionalSection {
		private VirtualPGL pgl;
		private String section;
	}

	private List<OtherValue> otherValues               = new ArrayList<>();
	private List<AdditionalKey> additionalKeys         = new ArrayList<>();
	private List<AdditionalSection> additionalSections = new ArrayList<>();
	

	private Map<VirtualPGL, String> pglNames = new HashMap<>(2);
	
	public void setPGLName(@NonNull VirtualPGL pgl, String pglName) {
		pglNames.put(pgl, pglName);
	}
	public String getPGLName(VirtualPGL pgl) {
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
	
	public Differences clear() {
		otherValues.clear();
		additionalKeys.clear();
		additionalSections.clear();
		
		return this;
	}
}
