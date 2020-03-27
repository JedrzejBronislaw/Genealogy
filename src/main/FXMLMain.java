package main;

import java.io.FileNotFoundException;

import fxmlBuilders.MainWindowBuilder;
import fxmlBuilders.session.Session;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.PGLFile;
import model.Tree;

public class FXMLMain extends Application {

	private static final String WINDOW_TITLE = "Pogologia";
	private Stage stage;
	
	private Session session = new Session();
		
	public static void main(String[] args) {
		FXMLMain.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		session.setTree(loadTree("tree.pgl"));
		buildView();
	}

	private void buildView() {
		MainWindowBuilder mainWindowBuilder = new MainWindowBuilder();
		mainWindowBuilder.setSession(session);
		mainWindowBuilder.build();
		
		stage.setScene(new Scene(mainWindowBuilder.getPane()));
		
		stage.setTitle(WINDOW_TITLE);
		stage.setOnCloseRequest(e -> Platform.exit());
		
		stage.show();
	}
	
	private static Tree loadTree(String path)
	{
		Tree tree = new Tree();
		
		try {
			PGLFile file = new PGLFile(path);
			file.laduj(tree);
		} catch (FileNotFoundException e) {
			System.out.println("File not found (" + path + ").");
			return null;
		}		
		
		return tree;
	}
}
