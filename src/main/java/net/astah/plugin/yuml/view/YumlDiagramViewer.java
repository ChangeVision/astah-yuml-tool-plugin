package net.astah.plugin.yuml.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.astah.plugin.yuml.view.model.DiagramListModel;
import net.astah.plugin.yuml.view.renderer.DiagramListCellRenderer;

import com.change_vision.jude.api.inf.model.IDiagram;

@SuppressWarnings("serial")
public class YumlDiagramViewer extends JDialog {
	private JList diagramList;
	private YumlDiagramViewPane diagramViewPane;
	
	private List<IDiagram> diagrams;
	private DiagramListModel diagramListModel;

	public YumlDiagramViewer(Frame parent, List<IDiagram> diagrams) {
		super(parent, "yUML diagram viewer", true);
		
		this.diagrams = diagrams;
		
		initComponents();
		setDefaultSelection();

		setLocationRelativeTo(parent);
	}

	private void setDefaultSelection() {
		if (diagrams.size() > 0) {
			diagramList.setSelectedIndex(0);
		}
	}

	private void initComponents() {
		JSplitPane baseSplitPane = new JSplitPane();
		JScrollPane leftScrollPane = new JScrollPane();
		diagramList = new JList();
		diagramList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		diagramViewPane = new YumlDiagramViewPane();
		diagramListModel = new DiagramListModel(diagrams);
		diagramList.addListSelectionListener(new DiagramListSelectionListener());
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		diagramList.setCellRenderer(new DiagramListCellRenderer());
		diagramList.setModel(diagramListModel);

		leftScrollPane.setViewportView(diagramList);

		baseSplitPane.setLeftComponent(leftScrollPane);
		baseSplitPane.setRightComponent(diagramViewPane);
		baseSplitPane.setDividerLocation(180);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(baseSplitPane, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton closeButton = new JButton("Close");
		closeButton.addMouseListener(new CloseButtonMouseListener());
		buttonPanel.add(closeButton);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		setSize(960, 720);
	}
	
	class DiagramListSelectionListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting()) {
				return;
			}
			
			int selectedIndex = diagramList.getMinSelectionIndex();
			IDiagram selectedDiagram = (IDiagram) diagramListModel.getElementAt(selectedIndex);
			diagramViewPane.updateContents(selectedDiagram);
		}
	}
	
	class CloseButtonMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent mouseevent) {
			YumlDiagramViewer.this.dispose();
		}
	}
}