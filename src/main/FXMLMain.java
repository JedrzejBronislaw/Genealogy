package main;

import java.io.File;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lang.Internationalization;
import lang.Languages;
import model.Tree;
import model.pgl.PGLWriter;
import model.pgl.reader.PGLReader;
import session.Session;
import viewFX.mainWindow.MainWindowBuilder;

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
			Scene scene = buildScene(language);
			stage.hide();
			stage.setScene(scene);
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
		mainWindowBuilder.setFullScreenAction(this::setFullScreen);
		mainWindowBuilder.setIsFullScreen(stage::isFullScreen);
		mainWindowBuilder.setCloseTree(() -> session.setTree(null));
		mainWindowBuilder.setSaveTree(this::saveTree);
		mainWindowBuilder.setSaveTreeAs(this::saveTreeAs);
		mainWindowBuilder.build();
		
		Scene scene = new Scene(mainWindowBuilder.getPane());
		scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
		return scene;
	}
	
	private void setFullScreen(boolean fullscreen) {
		stage.setFullScreen(fullscreen);
	}
	
	private static Tree loadTree(String path)
	{
		Tree tree = new Tree();
		
		try {
			PGLReader file = new PGLReader(path);
			file.load(tree);
		} catch (FileNotFoundException e) {
			System.out.println("File not found (" + path + ").");
			return null;
		}		
		
		return tree;
	}
	
	private boolean saveTree() {
		System.out.println("Save");
		return false;
	}
	
	private boolean saveTreeAs(File file) {
		System.out.println("Save as");

		Tree tree = session.getTree();
		PGLWriter writer = new PGLWriter(file);

		return writer.save(tree);
	}
}
