package model.pgl.saver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import model.pgl.Section;
import model.pgl.PGL;

@RequiredArgsConstructor
public class PGLSaver {
	
	@NonNull
	private File file;
	private FileWriter writer;
	
	private IOException error = null;
	
	public boolean save(PGL pgl) throws IOException {
		writer = new FileWriter(file);
		
		pgl.forEachSection(this::saveSection);
		if (error != null) throw error;
		
		writer.close();
		
		return true;
	}
	
	private void saveSection(Section section) {
		writeSectionName(section.getName());
		section.forEachKey(this::saveProperty);
	}

	private void writeSectionName(String value) {
		writeLine("[" + value + "]");
	}

	private void saveProperty(String name, String value) {
		if (value == null || value.isEmpty()) return;
		writeLine(name + "=" + value);
	}
	
	private void writeLine(String line) {
		if (error != null) return;
		
		try {
			writer.write(line);
			writer.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
			error = e;
		}
	}
}
