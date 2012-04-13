package net.astah.plugin.yuml.view.model;

import java.util.List;

import javax.swing.DefaultListModel;

import com.change_vision.jude.api.inf.model.IDiagram;

public class DiagramListModel extends DefaultListModel {
	private static final long serialVersionUID = 5636401811346234264L;
	
	protected List<IDiagram> diagrams;

	public DiagramListModel(List<IDiagram> diagrams) {
		this.diagrams = diagrams;
	}

	@Override
	public Object getElementAt(int index) {
		return diagrams.get(index);
	}

	@Override
	public int getSize() {
		return diagrams.size();
	}
}
