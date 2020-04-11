package other;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Diminutives {

	private static Diminutives stdDiminutives = new Diminutives("resources/diminutives.ust", true);
	private HashMap<String, String> diminutives = new HashMap<String, String>();
	
	
	private Diminutives(String path, boolean resources) {
		if (!loadFromResources(path)) System.out.println("no resource");
	}
	
	public Diminutives(String path) {
		loadFile(path);
	}
	
	private boolean loadFile(String path)
	{
		return fileAnalysis(new File(path));
	}
	
	private boolean loadFromResources(String path)
	{
		ClassLoader loader = getClass().getClassLoader();
		File file;
		
		try {
			file = new File(loader.getResource(path).getFile());
		} catch (NullPointerException e)
		{
			return false;
		}
		
		return fileAnalysis(file);
	}

	private boolean fileAnalysis(File file)
	{
		Scanner scanner;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			return false;
		}
		
		String line;
		String[] splitting;
		
		while(scanner.hasNextLine())
		{
			line = scanner.nextLine();
			splitting = line.split("[|]");
			if (splitting.length > 1)
				diminutives.put(splitting[0], splitting[1]);
		}
		scanner.close();
		return true;		
	}
	
	public static String forNameStd(String name) {
		return stdDiminutives.forName(name);
	}
	public String forName(String name) {
		String diminutive = diminutives.get(name);
		return (diminutive != null) ? diminutive : "";
	}
}
