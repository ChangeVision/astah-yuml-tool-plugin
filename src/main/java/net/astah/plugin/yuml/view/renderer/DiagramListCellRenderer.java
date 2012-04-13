package net.astah.plugin.yuml.view.renderer;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.change_vision.jude.api.inf.model.IDiagram;

@SuppressWarnings("serial")
public class DiagramListCellRenderer extends JLabel implements ListCellRenderer {
	private static final ImageIcon CLASS_DIAGRAM = new ImageIcon(DiagramListCellRenderer.class.getResource("ClassDiagram.gif"));
	
	public DiagramListCellRenderer() {
		setOpaque(true);
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		IDiagram diagram = (IDiagram) value;
		setText(diagram.getName());
		setIcon(getIcon(diagram));
		setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
		setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
		setPreferredSize(new Dimension(1, 25));
		return this;
	}
	
	private ImageIcon getIcon(IDiagram diagram) {
		return CLASS_DIAGRAM;
	}
}