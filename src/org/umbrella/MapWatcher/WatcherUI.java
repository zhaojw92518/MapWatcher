package org.umbrella.MapWatcher;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTree;

public class WatcherUI extends JFrame{
	public WatcherUI(int width, int height){
		super("WatcherUI");
		this.setLayout(new BorderLayout());
		
		this.setSize(width, height);
		this.setVisible(true);
	}
	
	private JTree watch_tree = new JTree();
}
