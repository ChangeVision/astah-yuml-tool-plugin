package net.astah.plugin.yuml;

import javax.swing.JFrame;

import net.astah.plugin.yuml.exception.APIException;

import com.change_vision.jude.api.inf.editor.BasicModelEditor;
import com.change_vision.jude.api.inf.editor.ClassDiagramEditor;
import com.change_vision.jude.api.inf.editor.IDiagramEditorFactory;
import com.change_vision.jude.api.inf.editor.IModelEditorFactory;
import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.model.IClassDiagram;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.project.ProjectAccessorFactory;
import com.change_vision.jude.api.inf.view.IDiagramViewManager;
import com.change_vision.jude.api.inf.view.IViewManager;

public class AstahAPIHandler {
//	private static final Logger logger = LoggerFactory.getLogger(AstahAPIHandler.class);
	
	public IClassDiagram getClassDiagram() {
		IDiagramViewManager diagramViewManager = getDiagramViewManager();
		return (IClassDiagram)diagramViewManager.getCurrentDiagram();
	}
	
	public IDiagramViewManager getDiagramViewManager() {
		IViewManager viewManager = getViewManager();
	    IDiagramViewManager diagramViewManager = viewManager.getDiagramViewManager();
		return diagramViewManager;
	}

	public ClassDiagramEditor getClassDiagramEditor() {
		try {
			return getDiagramEditorFactory().getClassDiagramEditor();
		} catch (InvalidUsingException e) {
			throw new APIException(e);
		}
	}

	public BasicModelEditor getBasicModelEditor() {
		try {
			return getModelEditorFactory().getBasicModelEditor();
		} catch (InvalidEditingException e) {
			throw new APIException(e);
		}
	}

	public ProjectAccessor getProjectAccessor() {
		ProjectAccessor projectAccessor = null;
		try {
			projectAccessor = ProjectAccessorFactory.getProjectAccessor();
		} catch (ClassNotFoundException e) {
	        throw new APIException(e);
		}
		if(projectAccessor == null) throw new IllegalStateException("projectAccessor is null.");
		return projectAccessor;
	}

	public JFrame getMainFrame() {
		try {
			return getProjectAccessor().getViewManager().getMainFrame();
		} catch (InvalidUsingException e) {
			throw new APIException(e);
		}
	}
	
	public String getEdition() {
		return getProjectAccessor().getAstahEdition();
	}

	private IViewManager getViewManager() {
		ProjectAccessor projectAccessor = getProjectAccessor();
		IViewManager viewManager = null;
		try {
			viewManager = projectAccessor.getViewManager();
		} catch (InvalidUsingException e) {
			throw new APIException(e);
		}
		if(viewManager == null) throw new APIException("ViewManager is null.");
		return viewManager;
	}

	private IModelEditorFactory getModelEditorFactory() {
		ProjectAccessor projectAccessor = getProjectAccessor();
		IModelEditorFactory modelEditorFactory = projectAccessor.getModelEditorFactory();
		if(modelEditorFactory == null) throw new APIException("modelEditorFactory is null.");
		return modelEditorFactory;
	}

	private IDiagramEditorFactory getDiagramEditorFactory() {
		ProjectAccessor projectAccessor = getProjectAccessor();
		IDiagramEditorFactory diagramEditorFactory = projectAccessor.getDiagramEditorFactory();
		if(diagramEditorFactory == null) throw new APIException("diagramEditorFactory is null.");
		return diagramEditorFactory;
	}

}