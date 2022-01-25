package tools;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class PathUtils {
	
	public static String jarPath() {
		File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
		String path = null;

		try {
			path = URLDecoder.decode(jarDir.getAbsolutePath(), "UTF-8");
			path = path+File.separator;
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};

		return path;
	}
	
	public static String makePathAbsolute(String path) {
		return path = isPathAbsolute(path) ? path : jarPath() + path;
	}

	public static boolean isPathAbsolute(String path) {
		return path.charAt(1) == ':';
	}
}
