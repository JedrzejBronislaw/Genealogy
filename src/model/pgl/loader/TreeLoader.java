package model.pgl.loader;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import model.LifeStatus;
import model.MyDate;
import model.Person;
import model.Sex;
import model.Tree;
import model.pgl.Section;
import model.pgl.PGLFields;
import model.pgl.PGL;
import model.pgl.comparator.PGLComparator;
import model.pgl.comparator.PGLDiffReport;
import model.pgl.loader.Relation.Type;
import model.pgl.saver.TreeToPGLMapper;

public class TreeLoader implements ITreeLoader {

	@AllArgsConstructor
	class IndexedString {
		int i;
		String value;
	}
	
	
    private IPGLLoader pglLoader;

    
	public TreeLoader(IPGLLoader pglLoader) {
		this.pglLoader = pglLoader;
	}
	
	
	@Override
	public boolean load(Tree tree) {
		try {
			loadFromFile(tree);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public PGLDiffReport loadAndAnalize(Tree tree) {
		PGL pgl;
		
		try {
			pgl = loadFromFile(tree);
		} catch (IOException e) {
			return null;
		}
		
		return analize(tree, pgl);
	}
	
	private PGL loadFromFile(Tree tree) throws IOException {
		List<Relation> relations = new ArrayList<Relation>();
		PGL pgl = pglLoader.load();
		
		loadToTree(tree, pgl, relations);
		relations.forEach(relation -> relation.applyFor(tree));

		return pgl;
	}

	private PGLDiffReport analize(Tree tree, PGL pgl) {
		return new PGLDiffReport(new PGLComparator(
				pgl, "file",
				new TreeToPGLMapper().map(tree), "loaded")
				.compare());
	}

	private boolean isMainSection(Section section) {
		return section.getName().toUpperCase().equals(
				PGLFields.mainSectionName.toUpperCase());
	}

	private void loadMetadataToTree(Tree tree, Section section) {
		section.value(PGLFields.lastOpen)        .flatMap(this::loadDate).ifPresent(tree::setLastOpen);
		section.value(PGLFields.lastModification).flatMap(this::loadDate).ifPresent(tree::setLastModification);
		section.value(PGLFields.numberOfPersons) .flatMap(this::strToInt).ifPresent(tree::setNumberOfPersons);
		
		multiVal(section, PGLFields.commonSurname, 10).forEach(surname -> {
			if (!surname.value.isEmpty()) tree.addCommonSurname(surname.value);
		});
	}

	private Optional<Date> loadDate(String textDate) {
		final SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm:ss yyyy-MM-dd");
		final SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy");
		
		return     tryLoadDate(textDate, PGLFields.dataFormat).
		  or(() -> tryLoadDate(textDate, sdf1)).
		  or(() -> tryLoadDate(textDate, sdf2));
	}

	private Optional<Date> tryLoadDate(String textDate, SimpleDateFormat format) {
		Date date;
		try {
			date = format.parse(textDate);
		} catch (ParseException e) {
			date = null;
		}
		
		return Optional.ofNullable(date);
	}
	
	private void loadToTree(Tree tree, PGL pgl, List<Relation> relations) {
		pgl.getSection(PGLFields.mainSectionName).ifPresent(mainSection ->
			loadMetadataToTree(tree, mainSection)
		);
		
		pgl.forEachSection(section ->
			loadDataToTree(tree, section, relations)
		);
	}
	
	private void loadDataToTree(Tree tree, Section section, List<Relation> relations) {
		if (isMainSection(section)) return;
		
		Person person = new Person();
		String personId = section.getName();
		int numOfChildren  = 0;
		int numOfMarriages = 0;
		
		section.value(PGLFields.firstName)                       .ifPresent(person::setFirstName);
		section.value(PGLFields.lastName)                        .ifPresent(person::setLastName);
		section.value(PGLFields.birthDate).map(MyDate::new)      .ifPresent(person::setBirthDate);
		section.value(PGLFields.deathDate).map(MyDate::new)      .ifPresent(person::setDeathDate);
		section.value(PGLFields.birthPlace)                      .ifPresent(person::setBirthPlace);
		section.value(PGLFields.deathPlace)                      .ifPresent(person::setDeathPlace);
		section.value(PGLFields.lifeStatus).map(LifeStatus::get) .ifPresent(person::setLifeStatus);
		section.value(PGLFields.sex).map(Sex::get)               .ifPresent(person::setSex);
		section.value(PGLFields.alias)                           .ifPresent(person::setAlias);
		section.value(PGLFields.baptismParish)                   .ifPresent(person::setBaptismParish);
		section.value(PGLFields.burialPlace)                     .ifPresent(person::setBurialPlace);

		section.value(PGLFields.contact) .map(this::splitLine).ifPresent(person::setContact);
		section.value(PGLFields.comments).map(this::splitLine).ifPresent(person::setComments);
		
		section.value(PGLFields.father).ifPresent(v -> relations.add(new Relation(v, Type.FATHER, personId)));
		section.value(PGLFields.mother).ifPresent(v -> relations.add(new Relation(v, Type.MOTHER, personId)));
		numOfChildren  = section.value(PGLFields.children) .flatMap(this::strToInt).orElse(0);
		numOfMarriages = section.value(PGLFields.marriages).flatMap(this::strToInt).orElse(0);

		multiVal(section, PGLFields.child,  numOfChildren). forEach(v -> relations.add(new Relation(v.value, Type.CHILD,  personId, v.i)));
		multiVal(section, PGLFields.spouse, numOfMarriages).forEach(v -> relations.add(new Relation(v.value, Type.SPOUSE, personId, v.i)));
		multiVal(section, PGLFields.weddingDate,  numOfMarriages).forEach(v -> Relation.addDateToRelation( relations, personId, Type.SPOUSE, v.i, v.value));
		multiVal(section, PGLFields.weddingPlace, numOfMarriages).forEach(v -> Relation.addPlaceToRelation(relations, personId, Type.SPOUSE, v.i, v.value));

		tree.addPerson(personId, person);
	}
	
	private String splitLine(String test) {
		return test.replace(PGLFields.lineSeparator, System.lineSeparator());
	}
	
	private List<IndexedString> multiVal(Section section, String key, int size) {
		String value;
		List<IndexedString> list = new ArrayList<>();
		
		for (int i=1; i<=size; i++) {
			value = section.getValue(key + i);
			if (value != null) list.add(new IndexedString(i, value));
		}
		
		return list;
	}
	
	private Optional<Integer> strToInt(String stringValue) {
		Optional<Integer> integerValue;
		try{
			integerValue = Optional.of(Integer.parseInt(stringValue));
		} catch (NumberFormatException e) {
			integerValue = Optional.empty();
		}
		
		return integerValue;
	}
}
