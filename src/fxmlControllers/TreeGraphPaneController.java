package fxmlControllers;

import java.awt.Dimension;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import model.Person;
import tools.SwingRefresher;
import treeGraphs.TreeGraph;
import treeGraphs.painter.Graphics2DPainter;
import windows.Canvas;

public class TreeGraphPaneController implements Initializable{

	@FXML
	private BorderPane treePane;

	private Person person;
	private TreeGraph graph;
	private SwingNode swingNode;
	
	private Canvas canvas = new Canvas();
	private Graphics2DPainter painter = new Graphics2DPainter();
	
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
			graph.setMainPerson(person);
			
			canvas.setPainter(painter);
			graph.setPainter(painter);

			SwingRefresher.refreshGraph(swingNode);
		}
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		canvas.setPreferredSize(new Dimension(1000, 1000));
		
		swingNode = new SwingNode();
		swingNode.setContent(canvas);
		treePane.setCenter(swingNode);
		
		canvas.setWymiary((sz, wys) -> {
			treePane.setPrefHeight(wys);
			treePane.setPrefWidth(sz);
		});
	}
}
