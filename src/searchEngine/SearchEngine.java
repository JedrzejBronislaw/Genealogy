package searchEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Person;
import model.Person.Sex;
import model.Tree;
import other.Diminutives;
import tools.Tools;

public class SearchEngine {
	
	private List<KeyWordsItem> keyWords;
	
	public SearchEngine() {
		clearKeyWords();
	}
	
	private void clearKeyWords() {
		keyWords = new ArrayList<>();
	}

	public void setTree(Tree tree)
	{
		if (tree == null) {
			clearKeyWords();
			return;
		}
		
		Person[] all = tree.getAll();
		keyWords = new ArrayList<>(all.length);
		for (Person person : all)
			keyWords.add(new KeyWordsItem(person, generateKeyWords(person)));
	}
	public void refresh() {
		List<KeyWordsItem> newKeyWords = new ArrayList<>(keyWords.size());
		keyWords.forEach(item -> newKeyWords.add(new KeyWordsItem(item.getPerson(), generateKeyWords(item.getPerson()))));
		keyWords = newKeyWords;
	}
	public void refresh(Person person) {
		for(int i=0; i<keyWords.size(); i++)
			if (keyWords.get(i).getPerson() == person) {
				keyWords.remove(i);
				keyWords.add(new KeyWordsItem(person, generateKeyWords(person)));
			}
	}
	
	public void forgetTree() {
		clearKeyWords();
	}

	private String generateKeyWords(Person person) {
		String keyWords = person.nameSurname();
		
		if (person.getSex() == Sex.WOMAN)
			for (int i=0; i<person.numberOfMarriages(); i++)
				keyWords += " " + person.getSpouse(i).getLastName();

		if (person.getAlias() != null)
			keyWords += " " + person.getAlias();
		keyWords += " " + Diminutives.forNameStd(person.getFirstName());
		
		keyWords = keyWords.toUpperCase();		
		return Tools.removePolishChars(keyWords);
	}
	
	public List<Person> run(String query){
		List<String> queries = prepareQuery(query);
		List<Person> found = search(queries);
		List<Person> outcome = sort(found);
		
		return outcome;
	}

	private List<String> prepareQuery(String query) {
		query = query.trim();
		query = query.toUpperCase();
		query = Tools.removePolishChars(query);
		return Arrays.asList(query.split(" "));
	}

	private List<Person> search(List<String> queries) {
		List<Person> found = new ArrayList<>();

		if(queries.size() == 0) return found;
		if(queries.size() == 1 && queries.get(0).isEmpty()) return found;
	
		for (KeyWordsItem kwItem : keyWords)
			if (kwItem.checkQueries(queries))
				found.add(kwItem.getPerson());
		
		return found;
	}
	

	private List<Person> sort(List<Person> found) {
		return found;
	}
}
