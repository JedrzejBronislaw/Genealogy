package tools;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Node;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class MyFXMLLoader<T extends Initializable>{
	
	@RequiredArgsConstructor
	public static class NodeAndController<T>{

		@NonNull
		@Getter
		private Node node;

		@NonNull
		@Getter
		private T controller;
	}


	private static final String mainDir = "/fxml/";
	private static final String lang = "lang.labels";
	
	
	public NodeAndController<T> create(String fxmlFilePath) {
		FXMLLoader fxmlLoader = new FXMLLoader();

		fxmlLoader.setLocation(getClass().getResource(mainDir + fxmlFilePath));
		fxmlLoader.setResources(ResourceBundle.getBundle(lang));

    	Node node;
		try {
			node = fxmlLoader.load();
		} catch (LoadException e) {
			String firstLine = e.getMessage().split("\n")[0];
			
			if (firstLine.startsWith("Resource") && firstLine.contains("not found"))
				System.out.println("Error in " + mainDir + fxmlFilePath + ": " + firstLine);
			else if (firstLine.isEmpty())
				System.out.println("Error: Missing controller for: " + mainDir + fxmlFilePath);
			else
				e.printStackTrace();
			
			return null;
		} catch (IllegalStateException e) {
			if (e.getMessage().equals("Location is not set."))
				System.out.println("Error: Missing file: " + mainDir + fxmlFilePath);
			else
				e.printStackTrace();
			
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    	
		
		return new NodeAndController<T>(node, fxmlLoader.getController());
	}

}
