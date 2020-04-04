package settings;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import lombok.Getter;

public class Settings {
	
	final String SETTINGS_FILENAME = "settings.set";
	
	@Getter
	private RecentFileList recentFiles = new RecentFileList();
	
	public boolean save() {
		Gson gson = new GsonBuilder()
				.excludeFieldsWithModifiers(Modifier.FINAL)
				.setPrettyPrinting()
				.create();
		
		try (FileWriter writer = new FileWriter(SETTINGS_FILENAME)) {
			gson.toJson(this, writer);
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean load() {
		Gson gson = new Gson();
		Settings settings;
		
		try(FileReader reader = new FileReader(SETTINGS_FILENAME)){
			settings = gson.fromJson(reader, Settings.class);
		} catch(JsonIOException | IOException e) {
			return false;
		}
		
		this.recentFiles = settings.recentFiles;
		
		return true;
	}
}
