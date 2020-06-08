package viewFX.treeGraph;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import lombok.Setter;
import model.Person;
import tools.Injection;
import treeGraphs.TreeGraph;
import treeGraphs.TreeGraphParameters;
import treeGraphs.painter.GraphSaver;
import treeGraphs.painter.service.PainterService;
import treeGraphs.painter.service.PainterServiceBuilder;

public class TreeGraphPaneController implements Initializable{

	@FXML
	private BorderPane treePane;
	@FXML
	private Button saveButton;

	@Setter
	private Consumer<Person> onPersonSingleClick;
	@Setter
	private Consumer<Person> onPersonDoubleClick;
	
	@Setter
	private Consumer<TreeGraphParameters> onParametersChange;
	
	private PainterService painterService;
	
	
	public void setPainterService(TreeGraphParameters parameters) {
		this.painterService = new PainterServiceBuilder().setParameters(parameters).build();
		treePane.setCenter(painterService.getCanvas(treePane));

		TreeGraph graph = painterService.getGraph();
		graph.setPersonSingleClickAction(person -> Injection.run(onPersonSingleClick, person));
		graph.setPersonDoubleClickAction(person -> Injection.run(onPersonDoubleClick, person));
		
		Injection.run(onParametersChange, parameters);
	}
	
	public void refreshGraph() {
		if (painterService != null)
			painterService.refreshGraph();
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
