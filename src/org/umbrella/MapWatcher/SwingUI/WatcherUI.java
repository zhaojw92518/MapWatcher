package org.umbrella.MapWatcher.SwingUI;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import org.umbrella.MapWatcher.ValueWatcher;

public class WatcherUI extends JFrame{
	public WatcherUI(int width, int height, DefaultMutableTreeNode in_node){
		super("WatcherUI");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		watch_tree = new JTree(in_node);
		watch_tree.setCellRenderer(new WatcherTreeCellRenderer());
		watch_tree.setShowsRootHandles(true);
		JScrollPane scroll_pane = new JScrollPane(watch_tree);
		scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(scroll_pane);
		this.setSize(width, height);
		this.setVisible(true);
	}

	public WatcherUI(int width, int height){
		super("WatcherUI");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		watch_tree.setCellRenderer(new MyTableInTreeCellRenderer());
		watch_tree.setShowsRootHandles(true);
		JScrollPane scroll_pane = new JScrollPane(watch_tree);
		scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(scroll_pane);
		this.setSize(width, height);
		this.setVisible(true);
	}
	
	private DefaultMutableTreeNode create_tree_node(Integer in_index){
		DefaultMutableTreeNode return_result = null;
		if(cur_level < tree_level){
			return_result = new DefaultMutableTreeNode(cur_level.toString() + in_index.toString());
			cur_level++;
			for(Integer i = 0; i < tree_width; i++){
				DefaultMutableTreeNode cur_node = create_tree_node(i);
				if(cur_node != null){
					return_result.add(cur_node);
				}
			}
			cur_level--;
		}
		return return_result;
	}
	private int i_size = 1, j_size = 3;
	private Integer tree_level = 3, tree_width = 3, cur_level = 0;
	
	private JTree watch_tree = new JTree(create_tree_node(0));
	
	
	private Object[][] create_test_table(String in_title){
		Object[][] return_result = new Object[i_size][j_size];
		for(Integer i = 0; i.intValue() < i_size; i++){
			for(Integer j = 0; j.intValue() < j_size; j++){
				return_result[i][j] = in_title + " " + i.toString() + " " + j.toString();
			}
		}
		return return_result;
	}
	private Object[] create_column_names(String in_title){
		Object[] return_result = new Object[j_size];
		for(Integer i = 0; i.intValue() < j_size; i++){
			return_result[i] = in_title + " column" + i.toString();
		}
		return return_result;
	}
	
	class MyTableInTreeCellRenderer extends JPanel implements TreeCellRenderer {

        private static final long serialVersionUID = 1L;
        private JTable table;

        public MyTableInTreeCellRenderer() {
            super(new BorderLayout());
            table = new JTable();
            add(table);
            this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        	String cur_str = (String) ((DefaultMutableTreeNode) value).getUserObject();
        	JTable cur_table = new JTable(create_test_table(cur_str), create_column_names(cur_str));
        	table.setModel(cur_table.getModel());
            table.setPreferredScrollableViewportSize(table.getPreferredSize());
            return this;
        }
    }
	
	class WatcherTreeCellRenderer extends JPanel implements TreeCellRenderer {

        private static final long serialVersionUID = 1L;
        private JTable table;

        public WatcherTreeCellRenderer() {
            super(new BorderLayout());
            table = new JTable();
            add(table);
            this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        	JTable cur_table = (JTable) ((DefaultMutableTreeNode) value).getUserObject();
        	table.setModel(cur_table.getModel());
            table.setPreferredScrollableViewportSize(table.getPreferredSize());
            return this;
        }
    }
}
