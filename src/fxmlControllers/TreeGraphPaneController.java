package fxmlControllers;

import java.awt.Dimension;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import model.Person;
import treeGraphs.TreeGraph;
import windows.Canvas;

public class TreeGraphPaneController implements Initializable{

	@FXML
	private ScrollPane treePane;

	private Person person;
	private TreeGraph graph;
	
	private Canvas canvas = new Canvas();
	
	public void setPerson(Person person) {
		this.person = person;
		build();
	}
	
	public void setGraph(TreeGraph graph) {
		this.graph = graph;
		build();
	}
	
	private void build() {
		if (graph != null && person != null) {
			canvas.setGrafDrzewa(graph);
			graph.setOsobaGlowna(person);
		}
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		canvas.setPreferredSize(new Dimension(1000, 1000));
		
		SwingNode swingNode = new SwingNode();
		swingNode.setContent(canvas);
		treePane.setContent(swingNode);
	}
}
