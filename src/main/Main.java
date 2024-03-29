package main;

import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lang.Internationalization;
import lang.Languages;
import model.Tree;
import model.pgl.comparator.PGLDiffReport;
import model.pgl.loader.PGLLoader;
import model.pgl.loader.TreeLoader;
import model.pgl.saver.TreeSaver;
import session.Session;
import viewFX.mainWindow.MainWindowBuilder;

public class Main extends Application {

	private static final String WINDOW_TITLE = "Pogologia";
	private Stage stage;
	
	private Session session = new Session();
		
	public static void main(String[] args) {
		Main.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		buildView();
	}
	
	private void buildView() {
		setIcon();
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

	private void setIcon() {
		try {
			Image icon = new Image("img/pogologia.png");
			stage.getIcons().add(icon);
		} catch (IllegalArgumentException e) {}
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
			TreeAndReport treeAndReport = loadTree(treeFile.getPath());
			if (treeAndReport.getReport().isPermissionToOpen())
				session.setTree(treeAndReport.getTree());
			return treeAndReport.getReport();
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
	
	private static TreeAndReport loadTree(String path) {
		Tree tree = new Tree();

		TreeLoader treeLoader = new TreeLoader(new PGLLoader(path));
		PGLDiffReport report = treeLoader.loadAndAnalize(tree);
		
		System.out.println(report);
		
		if (report == null) {
			System.out.println("Loading error (" + path + ").");
			return new TreeAndReport(null, PGLDiffReport.LOADINGERROR);
		}
		
		return new TreeAndReport(tree, report);
	}
	
	private boolean saveTree() {
		System.out.println("Save");
		return false;
	}
	
	private boolean saveTreeAs(File file) {
		System.out.println("Save as");

		Tree tree = session.getTree();
		TreeSaver writer = new TreeSaver(file);

		return writer.save(tree);
	}
}
