package model.pgl.virtual;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import model.pgl.virtual.Differences.AdditionalKey;
import model.pgl.virtual.Differences.AdditionalSection;
import model.pgl.virtual.Differences.OtherValue;

@RequiredArgsConstructor
public class PGLComparator {
	
	@NonNull private final VirtualPGL pgl1, pgl2;
	@Getter  private Differences differences = new Differences();
	
	public PGLComparator(@NonNull VirtualPGL pgl1, String pgl1Name, @NonNull VirtualPGL pgl2, String pgl2Name) {
		this.pgl1 = pgl1;
		this.pgl2 = pgl2;

		differences.setPGLName(pgl1, pgl1Name);
		differences.setPGLName(pgl2, pgl2Name);
	}
	
	public Differences compare() {
		if (pgl1.equals(pgl2)) return differences.clear();

		checkSections(pgl1, pgl2);
		checkKeys(pgl1, pgl2);
		checkValues(pgl1, pgl2);

		return differences;
	}
	
	
	private List<String> sectionsNames(VirtualPGL pgl1) {

		return pgl1.getSections().stream().
				map(section -> section.getName()).
				collect(Collectors.toList());
	}

	private boolean hasSection(List<String> sections, String name) {
		return sections.stream().anyMatch(sec -> sec.equals(name));
	}

//-----

	private void checkSections(VirtualPGL pgl1, VirtualPGL pgl2) {
		List<String> sections1 = sectionsNames(pgl1);
		List<String> sections2 = sectionsNames(pgl2);

		checkSections(sections1, sections2, pgl1);
		checkSections(sections2, sections1, pgl2);
	}

	private void checkSections(List<String> sections1, List<String> sections2, VirtualPGL pgl1) {
		
		sections1.stream().
			filter(section -> !hasSection(sections2, section)).
			map(section -> new AdditionalSection(pgl1, section)).
			forEach(differences::add);
	}

//-----

	private void checkKeys(VirtualPGL pgl1, VirtualPGL pgl2) {

		List<String> sections1 = sectionsNames(pgl1);
		List<String> sections2 = sectionsNames(pgl2);

		sections1.stream().
			filter(sectionName -> hasSection(sections2, sectionName)).
			forEach(sectionName -> {
				checkKeys(pgl1, pgl2, sectionName);
				checkKeys(pgl2, pgl1, sectionName);
			});
	}
	
	private void checkKeys(VirtualPGL pgl1, VirtualPGL pgl2, String section) {

		Set<String> keys1 = pgl1.get(section).get().getKeys().keySet();
		Set<String> keys2 = pgl2.get(section).get().getKeys().keySet();

		keys1.stream().
			filter(key -> !keys2.contains(key)).
			map(key -> new AdditionalKey(pgl1, section, key)).
			forEach(differences::add);
	}

//-----
	
	private void checkValues(VirtualPGL pgl1, VirtualPGL pgl2) {

		List<String> sections1 = sectionsNames(pgl1);
		List<String> sections2 = sectionsNames(pgl2);

		sections1.stream().
			filter(sec -> hasSection(sections2, sec)).
			forEach(sec -> checkValues(pgl1, pgl2, sec));
	}
	
	private void checkValues(VirtualPGL pgl1, VirtualPGL pgl2, String section) {

		Set<String> keys1 = pgl1.get(section).get().getKeys().keySet();
		
		keys1.stream().
			filter(key -> !equalValue(pgl1, pgl2, section, key)).
			map(key -> new OtherValue(section, key)).
			forEach(differences::add);
	}

	private boolean equalValue(VirtualPGL pgl1, VirtualPGL pgl2, String section, String key) {
		String key1Value = pgl1.getValue(section, key);
		String key2Value = pgl2.getValue(section, key);
		
		return key1Value.equals(key2Value);
	}
}
