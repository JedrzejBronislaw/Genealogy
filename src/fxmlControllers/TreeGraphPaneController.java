package fxmlControllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Person;
import treeGraphs.TreeGraph;
import treeGraphs.painter.GraphSaver;
import treeGraphs.painter.service.FXPainterService;
import treeGraphs.painter.service.PainterService;

public class TreeGraphPaneController implements Initializable{

	@FXML
	private BorderPane treePane;
	@FXML
	private Button saveButton;
	
	
	private PainterService painterService = new FXPainterService();
//	private PainterService painterService = new Graphics2DPainterService();
	
	public void setPerson(Person person) {
		painterService.setMainPerson(person);
	}
	
	public void setGraph(TreeGraph graph) {
		painterService.setGraph(graph);
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		treePane.setCenter(painterService.getCanvas(treePane));
		
		saveButton.setOnAction(e -> {
			saveButton.setDisable(true);
			GraphSaver graphSaver = painterService.getGraphSaver();
			
			if (graphSaver != null) {
				FileChooser chooser = new FileChooser();
				File file;
				
				chooser.getExtensionFilters().add(new ExtensionFilter("PNG Files", "*.png"));
				file = chooser.showSaveDialog(null);
				
				graphSaver.save(file);
			}
			saveButton.setDisable(false);
		});
	}
}
