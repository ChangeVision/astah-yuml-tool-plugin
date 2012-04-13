package net.astah.plugin.yuml;

import java.io.InputStream;

import net.astah.plugin.yuml.view.YumlDiagramViewPane;

import org.junit.Test;

import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.model.IClassDiagram;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.project.ProjectAccessorFactory;

public class ImageLoaderTest {
	@Test
	public void test() throws Throwable {
        InputStream astaInputStream = ImageLoaderTest.class.getResourceAsStream("test.asta");
//		String astaInputStream = "/Users/shoito/Downloads/JUnit.asta";
        
        ProjectAccessor prjAccessor = ProjectAccessorFactory.getProjectAccessor();
        prjAccessor.open(astaInputStream, true);
        IModel project = prjAccessor.getProject();
        
        printChildren(project);
        
        prjAccessor.close();
	}

	private void printChildren(IPackage parent) throws InvalidUsingException {
		IDiagram[] diagrams = parent.getDiagrams();
        for (IDiagram diagram : diagrams) {
        	if (diagram instanceof IClassDiagram) {
        		YumlDiagramViewPane viewer = new YumlDiagramViewPane();
        		viewer.updateContents(diagram);
        		System.out.println(); // TODO Load image
        	}
        }
        
        INamedElement[] ownedElements = parent.getOwnedElements();
        for (INamedElement element : ownedElements) {
        	if (element instanceof IPackage) {
        		printChildren((IPackage) element);
        	}
        }
	}
}
