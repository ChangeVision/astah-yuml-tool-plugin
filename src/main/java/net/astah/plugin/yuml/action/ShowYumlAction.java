package net.astah.plugin.yuml.action;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import net.astah.plugin.yuml.exception.DiagramNotFoundException;
import net.astah.plugin.yuml.view.YumlDiagramViewer;

import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IClassDiagram;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.project.ProjectAccessorFactory;
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate;
import com.change_vision.jude.api.inf.ui.IWindow;

public class ShowYumlAction implements IPluginActionDelegate {

	public Object run(IWindow window) throws UnExpectedException {
		try {
			ProjectAccessor projectAccessor = ProjectAccessorFactory.getProjectAccessor();
			IModel project = projectAccessor.getProject();
			List<IDiagram> allDiagrams = getAllDiagrams(project);
			if (allDiagrams.size() == 0) {
				throw new DiagramNotFoundException();
			}
			
			YumlDiagramViewer viewer = new YumlDiagramViewer((Frame) window.getParent(), allDiagrams);
			viewer.setVisible(true);
		} catch (DiagramNotFoundException e) {
			String message = "Diagram is not found. Please open the project containing class diagrams.";
			JOptionPane.showMessageDialog(window.getParent(), message,
					"Information", JOptionPane.INFORMATION_MESSAGE);
		} catch (ProjectNotFoundException e) {
			String message = "Project is not opened. Please open the project or create new project.";
			JOptionPane.showMessageDialog(window.getParent(), message,
					"Warning", JOptionPane.WARNING_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(window.getParent(),
					"Unexpected error has occurred.", "Alert",
					JOptionPane.ERROR_MESSAGE);
			throw new UnExpectedException();
		}
		return null;
	}
	
	public List<IDiagram> getAllDiagrams(IModel project) {
        List<IDiagram> allDiagrams = new ArrayList<IDiagram>();
        pushAllDiagrams(allDiagrams, project);
        return allDiagrams;
	}

	private void pushAllDiagrams(List<IDiagram> allDiagrams, IPackage parent) {
		IDiagram[] diagrams = parent.getDiagrams();
        for (IDiagram diagram : diagrams) {
        	if (diagram instanceof IClassDiagram
        			// TODO
//        			|| diagram instanceof IActivityDiagram
//        			|| diagram instanceof IUseCaseDiagram
        			) {
        		allDiagrams.add(diagram);
        	}
        }
        
        INamedElement[] ownedElements = parent.getOwnedElements();
        for (INamedElement element : ownedElements) {
        	if (element instanceof IPackage) {
        		pushAllDiagrams(allDiagrams, (IPackage) element);
        	}
        }
	}
}
