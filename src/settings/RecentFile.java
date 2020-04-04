package settings;

import java.io.File;

import lombok.Getter;
import lombok.Setter;

public class RecentFile {
	
	@Getter
	private String path;
	@Setter
	@Getter
	private String name;
	@Setter
	@Getter
	private boolean favorite;
	
		
	public RecentFile(String path) {
		this.path = path;
	}

	public RecentFile(String path, String name, boolean favorite) {
		this.path = path;
		this.name = name;
		this.favorite = favorite;
	}


	public boolean equals(File file) {
		return file.getPath().equals(path);
	}
}
