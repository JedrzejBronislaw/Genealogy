package utils;

import java.util.Timer;
import java.util.TimerTask;

import javafx.embed.swing.SwingNode;

public class SwingRefresher {

	public static void refreshGraph(SwingNode swingNode){
		
        new Timer().schedule(new TimerTask() {
            public void run() {
                swingNode.getContent().repaint();
            }
        }, 100L);
        new Timer().schedule(new TimerTask() {
            public void run() {
                swingNode.getContent().repaint();
            }
        }, 200L);
        
	}
	
	public static void refresh(SwingNode swingNode){
		
        new Timer().schedule(new TimerTask() {
            public void run() {
                swingNode.getContent().repaint();
            }
        }, 500L);
        
	}
	
	public static void refreshNow(SwingNode swingNode){
		swingNode.getContent().repaint();
	}
}
