package settings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecentFileList {


	private List<RecentFile> list = new ArrayList<>();
	
	public void add(File file) {
		RecentFile recentFile = findfile(file);

		if (recentFile != null) {
			list.add(0, recentFile);
			list.remove(list.lastIndexOf(recentFile));
		} else {
			recentFile = new RecentFile(file.getPath());
			recentFile.setName(file.getName());
			recentFile.setFavorite(false);
			
			list.add(0, recentFile);
		}
	}
	
	public int size() {
		return list.size();
	}
	
	public RecentFile get(int index) {
		return list.get(index);
	}
	
	private RecentFile findfile(File file) {		
		for(int i=0; i<list.size(); i++)
			if (list.get(i).equals(file)) return list.get(i);
		
		return  null;
	}

	public List<String> extractPaths() {
		List<String> pathList = new ArrayList<>(size());
		
		list.forEach(file -> pathList.add(file.getPath()));
		
		return pathList;
	}

	public List<RecentFile> copyList() {
		return new ArrayList<>(list);
	}
}
