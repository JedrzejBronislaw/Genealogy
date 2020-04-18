package fxmlControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import model.Person;
import treeGraphs.TreeGraph;
import treeGraphs.painter.service.FXPainterService;
import treeGraphs.painter.service.PainterService;

public class TreeGraphPaneController implements Initializable{

	@FXML
	private BorderPane treePane;

	private PainterService painterFactory = new FXPainterService();
//	private PainterFactory painterFactory = new Graphics2DPainterFactory();
	
	public void setPerson(Person person) {
		painterFactory.setMainPerson(person);
	}
	
	public void setGraph(TreeGraph graph) {
		painterFactory.setGraph(graph);
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		treePane.setCenter(painterFactory.getCanvas(treePane));
	}
}
