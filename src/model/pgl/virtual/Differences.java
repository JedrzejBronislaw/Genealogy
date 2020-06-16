package model.pgl.virtual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

public class Differences {
	
	@EqualsAndHashCode
	@ToString
	@AllArgsConstructor
	public static class OtherValue {
		private String section;
		private String keyName;
	}

	@EqualsAndHashCode
	@ToString
	@AllArgsConstructor
	public static class AdditionalKey {
		private VirtualPGL pgl;
		private String section;
		private String keyName;
	}

	@EqualsAndHashCode
	@ToString
	@AllArgsConstructor
	public static class AdditionalSection {
		private VirtualPGL pgl;
		private String section;
	}

	private List<OtherValue> otherValues               = new ArrayList<>();
	private List<AdditionalKey> additionalKeys         = new ArrayList<>();
	private List<AdditionalSection> additionalSections = new ArrayList<>();
	

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
