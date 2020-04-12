package searchEngine;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import model.Person;

@RequiredArgsConstructor
public class KeyWordsItem {

	@Getter
	@NonNull
	private Person person;
	
	@Getter
	@NonNull
	private String keyWords;
	
	public boolean checkQueries(String query) {
		return keyWords.contains(query);
	}
	
	public boolean checkQueries(List<String> queries) {
		for (String query : queries)
			if (!keyWords.contains(query))
				return false;
		return true;
	}

}