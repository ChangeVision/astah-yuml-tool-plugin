package net.astah.plugin.yuml.builder;

import java.util.ArrayList;
import java.util.List;

import net.astah.plugin.yuml.draw.Direction;
import net.astah.plugin.yuml.draw.DrawType;
import net.astah.plugin.yuml.draw.Size;
import net.astah.plugin.yuml.draw.UrlType;
import net.astah.plugin.yuml.model.Association;
import net.astah.plugin.yuml.model.ClassUtils;
import net.astah.plugin.yuml.model.Dependency;
import net.astah.plugin.yuml.model.Generalization;
import net.astah.plugin.yuml.model.Realization;
import net.astah.plugin.yuml.model.Relation;
import net.astah.plugin.yuml.model.TemplateBinding;
import net.astah.plugin.yuml.model.Usage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.model.IAssociation;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.IClassDiagram;
import com.change_vision.jude.api.inf.model.IDependency;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.IElement;
import com.change_vision.jude.api.inf.model.IGeneralization;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IRealization;
import com.change_vision.jude.api.inf.model.ITemplateBinding;
import com.change_vision.jude.api.inf.model.IUsage;
import com.change_vision.jude.api.inf.presentation.ILinkPresentation;
import com.change_vision.jude.api.inf.presentation.IPresentation;

public class ClassDiagramBuilder extends DiagramBuilderBase {
	private static final Logger logger = LoggerFactory.getLogger(ClassDiagramBuilder.class);
	private static final String DIAGRAM_TYPE = "/class/";

	public ClassDiagramBuilder(IClassDiagram diagram) {
		this(diagram, UrlType.PNG, DrawType.PLAIN, Direction.TOP_DOWN, Size.SMALL);
	}
	
	public ClassDiagramBuilder(IClassDiagram diagram, UrlType urlType) {
		this(diagram, urlType, DrawType.PLAIN, Direction.TOP_DOWN, Size.SMALL);
	}
	
	public ClassDiagramBuilder(IClassDiagram diagram, UrlType urlType, DrawType drawType) {
		this(diagram, urlType, drawType, Direction.TOP_DOWN, Size.SMALL);
	}
	
	public ClassDiagramBuilder(IClassDiagram diagram, UrlType urlType, DrawType drawType, Direction direction) {
		this(diagram, urlType, drawType, direction, Size.SMALL);
	}
	
	public ClassDiagramBuilder(IClassDiagram diagram, UrlType urlType, DrawType drawType, Direction direction, Size size) {
		super(diagram, urlType, drawType, direction, size);
	}

	public String toYuml() {
		StringBuilder yumlBuilder = new StringBuilder();
		yumlBuilder.append(DEFAULT_URL_PREFIX);
		yumlBuilder.append(drawType);
		yumlBuilder.append(";");
		yumlBuilder.append("dir:");
		yumlBuilder.append(direction);
		yumlBuilder.append(";");
		yumlBuilder.append("scale:");
		yumlBuilder.append(size.getScale());
		yumlBuilder.append(";");
		yumlBuilder.append(DIAGRAM_TYPE);
		
		List<IClass> classes = extractClassesOn(diagram);
		List<Relation> relations = extractRelationsOn(diagram);
		
		for (int i = 0; i < relations.size(); i++) {
			if (i > 0) yumlBuilder.append(",");
			Relation relation = relations.get(i);
			classes.remove(relation.getLeft());
			classes.remove(relation.getRight());
			yumlBuilder.append(relation.toYuml());
			
			logger.debug(relation.toYuml());
		}
		
		if (relations.size() > 0 && classes.size() > 0) {
			yumlBuilder.append(",");
		}
		
		for (int i = 0; i < classes.size(); i++) {
			if (i > 0) yumlBuilder.append(",");
			IClass clazz = classes.get(i);
			String name = ClassUtils.getNameLabel(clazz);
			yumlBuilder.append("[" + name + "]");
			
			logger.debug("[" + name + "]");
		}
		
		yumlBuilder.append(urlType);
		return yumlBuilder.toString();
	}
	
	private List<IClass> extractClassesOn(IDiagram diagram) {
		IPresentation[] presentations = null;
		try {
			presentations = ((IClassDiagram) diagram).getPresentations();
		} catch (InvalidUsingException e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		
		List<IClass> classes = new ArrayList<IClass>();
		for (IPresentation presentation : presentations) {
			IElement presentationModel = presentation.getModel();
			if (presentationModel instanceof IClass) {
				IClass clazz = (IClass) presentationModel;
				classes.add(clazz);
			}
		}
		return classes;
	}

	private List<Relation> extractRelationsOn(IDiagram diagram) {
		IPresentation[] presentations = null;
		try {
			presentations = ((IClassDiagram) diagram).getPresentations();
		} catch (InvalidUsingException e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		
		List<Relation> relations = new ArrayList<Relation>();
		for (IPresentation presentation : presentations) {
			IElement presentationModel = presentation.getModel();
			if (presentationModel instanceof IAssociation) {
				IPresentation sourceEnd = ((ILinkPresentation) presentation).getSourceEnd();
				IPresentation targetEnd = ((ILinkPresentation) presentation).getTargetEnd();
				IElement source = sourceEnd.getModel();
				IElement target = targetEnd.getModel();
				if (source instanceof IClass && target instanceof IClass) {
					relations.add(new Association(presentation, (IClass) source, (IClass) target));
				}
			} else if (presentationModel instanceof IGeneralization) {
				IClass superType = ((IGeneralization) presentationModel).getSuperType();
				IClass subType = ((IGeneralization) presentationModel).getSubType();
				relations.add(new Generalization(presentation, (IClass) superType, (IClass) subType));
			} else if (presentationModel instanceof IDependency) {
				INamedElement client = ((IDependency) presentationModel).getClient();
				INamedElement supplier = ((IDependency) presentationModel).getSupplier();
				if (client instanceof IClass && supplier instanceof IClass) {
					relations.add(new Dependency( presentation, (IClass) client, (IClass) supplier));
				}
			} else if (presentationModel instanceof IUsage) {
				INamedElement client = ((IUsage) presentationModel).getClient();
				INamedElement supplier = ((IUsage) presentationModel).getSupplier();
				if (client instanceof IClass && supplier instanceof IClass) {
					relations.add(new Usage(presentation, (IClass) client, (IClass) supplier));
				}
			} else if (presentationModel instanceof IRealization) {
				INamedElement client = ((IRealization) presentationModel).getClient();
				INamedElement supplier = ((IRealization) presentationModel).getSupplier();
				if (client instanceof IClass && supplier instanceof IClass) {
					relations.add(new Realization(presentation, (IClass) client, (IClass) supplier));
				}
			} else if (presentationModel instanceof ITemplateBinding) {
				IClass boundElement = ((ITemplateBinding) presentationModel).getBoundElement();
				IClass template = ((ITemplateBinding) presentationModel).getTemplate();
				if (boundElement instanceof IClass && template instanceof IClass) {
					relations.add(new TemplateBinding(presentation, (IClass) boundElement, (IClass) template));
				}
			}
		}
		return relations;
	}
}
