package model.pgl.loader;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import model.pgl.Section;
import model.pgl.PGL;
import tools.Tools;

public class PGLLoader implements IPGLLoader {
	
	private BufferedReader brFile;
	
	
	public PGLLoader(String path) throws FileNotFoundException {
		openFile(path);
	}
	
	private void openFile(String path) throws FileNotFoundException {
		FileInputStream fis;
		DataInputStream dis;
		
		if (path.charAt(1) != ':')
			path = Tools.dirWithJarPath() + path;
		
		fis = new FileInputStream(path);
		dis = new DataInputStream(fis);
		
		brFile = new BufferedReader(new InputStreamReader(dis));
	}
	
	@Override
	public PGL load() {
		try{
			return loadFromFile();
		} catch (IOException e) {
			return null;
		}
	}
	
	private PGL loadFromFile() throws IOException {
		String line;
		Section section = null;
		PGL pgl = new PGL();
		
		while ((line = brFile.readLine()) != null) {
			line = line.trim();
			
			if (isSectionHeader(line))
				section = pgl.newSection(sectionName(line));
			else
				addValue(line, section);
		}

		return pgl;
	}

	private void addValue(String line, Section section) {
		if (section == null) return;
		
		String[] keyValue = line.split("=", 2);
		
		if (keyValue.length == 2)
			section.addKey(keyValue[0], keyValue[1]);
	}

	private String sectionName(String line) {
		if (isSectionHeader(line))
			return line.substring(1, line.length()-1);
		else
			return null;
	}

	private boolean isSectionHeader(String line) {
		return line.startsWith("[")
			&& line.endsWith("]");
	}
}
