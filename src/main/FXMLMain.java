package main;

import java.io.FileNotFoundException;

import fxmlBuilders.MainWindowBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lang.Internationalization;
import lang.Languages;
import model.PGLFile;
import model.Tree;
import session.Session;

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
		buildView();
	}
	
	private void buildView() {
		stage.setScene(buildScene());
		stage.setWidth(1000);
		stage.setHeight(600);
		stage.setTitle(WINDOW_TITLE);
		stage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
		});
		
		stage.show();
	}
	
	private void changeLanguage(Languages language) {
		if(Internationalization.getCurrentLanguage() != language) {
			stage.setScene(buildScene(language));
			stage.hide();
			stage.show();
		}
	}

	private Scene buildScene(Languages language) {
		Internationalization.setLanguage(language);
		return buildScene();
	}

	private Scene buildScene() {
		MainWindowBuilder mainWindowBuilder = new MainWindowBuilder();
		mainWindowBuilder.setSession(session);
		mainWindowBuilder.setLoadTree(treeFile -> {
			Tree tree = loadTree(treeFile.getPath());
			session.setTree(tree);
			return tree != null;
		});
		mainWindowBuilder.setChangeLanguage(this::changeLanguage);
		mainWindowBuilder.build();
		
		Scene scene = new Scene(mainWindowBuilder.getPane());
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		return scene;
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
