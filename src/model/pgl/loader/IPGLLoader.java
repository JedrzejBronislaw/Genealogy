package model.pgl.loader;

import java.io.IOException;

import model.pgl.PGL;

public interface IPGLLoader {
	
	PGL load() throws IOException;
}
