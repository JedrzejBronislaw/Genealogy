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

	private String path;
	
	
	public PGLLoader(String path) {
		this.path = path;
	}
	
	private BufferedReader openFile(String path) throws FileNotFoundException {
		FileInputStream fis;
		DataInputStream dis;
		
		if (path.charAt(1) != ':')
			path = Tools.dirWithJarPath() + path;
		
		fis = new FileInputStream(path);
		dis = new DataInputStream(fis);
		
		return new BufferedReader(new InputStreamReader(dis));
	}
	
	@Override
	public PGL load() throws IOException {
		String line;
		Section section = null;
		PGL pgl = new PGL();

		BufferedReader reader = openFile(path);
		
		while ((line = reader.readLine()) != null) {
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
