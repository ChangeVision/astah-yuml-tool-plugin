package net.astah.plugin.yuml;

import java.io.InputStream;

import net.astah.plugin.yuml.builder.ClassDiagramBuilder;

import org.junit.Test;

import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.model.IClassDiagram;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.project.ProjectAccessorFactory;

public class ParserTest {
	@Test
	public void test() throws Throwable {
        InputStream astaInputStream = ParserTest.class.getResourceAsStream("test.asta");
        
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
        		String yuml = new ClassDiagramBuilder((IClassDiagram) diagram).toYuml();
        		System.out.println(yuml);
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
