package net.astah.plugin.yuml.builder;

import java.util.ArrayList;
import java.util.List;

import net.astah.plugin.yuml.draw.Direction;
import net.astah.plugin.yuml.draw.DrawType;
import net.astah.plugin.yuml.draw.Size;
import net.astah.plugin.yuml.draw.UrlType;
import net.astah.plugin.yuml.model.Clazz;
import net.astah.plugin.yuml.model.Relation;
import net.astah.plugin.yuml.model.usecase.Association;
import net.astah.plugin.yuml.model.usecase.Extend;
import net.astah.plugin.yuml.model.usecase.Generalization;
import net.astah.plugin.yuml.model.usecase.Include;
import net.astah.plugin.yuml.model.usecase.UseCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.jude.api.inf.model.IAssociation;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.IDependency;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.IElement;
import com.change_vision.jude.api.inf.model.IExtend;
import com.change_vision.jude.api.inf.model.IGeneralization;
import com.change_vision.jude.api.inf.model.IInclude;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IRealization;
import com.change_vision.jude.api.inf.model.IUsage;
import com.change_vision.jude.api.inf.model.IUseCase;
import com.change_vision.jude.api.inf.model.IUseCaseDiagram;
import com.change_vision.jude.api.inf.presentation.ILinkPresentation;
import com.change_vision.jude.api.inf.presentation.IPresentation;

public class UseCaseDiagramBuilder extends DiagramBuilderBase {
	private static final Logger logger = LoggerFactory.getLogger(UseCaseDiagramBuilder.class);
	private static final String DIAGRAM_TYPE = "/usecase/";

	public UseCaseDiagramBuilder(IUseCaseDiagram diagram) {
		this(diagram, UrlType.PNG, DrawType.PLAIN, Direction.TOP_DOWN, Size.NORMAL);
	}
	
	public UseCaseDiagramBuilder(IUseCaseDiagram diagram, UrlType urlType) {
		this(diagram, urlType, DrawType.PLAIN, Direction.TOP_DOWN, Size.NORMAL);
	}
	
	public UseCaseDiagramBuilder(IUseCaseDiagram diagram, UrlType urlType, DrawType drawType) {
		this(diagram, urlType, drawType, Direction.TOP_DOWN, Size.NORMAL);
	}
	
	public UseCaseDiagramBuilder(IUseCaseDiagram diagram, UrlType urlType, DrawType drawType, Direction direction) {
		this(diagram, urlType, drawType, direction, Size.NORMAL);
	}
	
	public UseCaseDiagramBuilder(IUseCaseDiagram diagram, UrlType urlType, DrawType drawType, Direction direction, Size size) {
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
		
		List<Clazz> actorsAndUsecases = extractActorsAndUsecasesOn(diagram);
		List<Relation> relations = extractRelationsOn(diagram);
		
		for (int i = 0; i < relations.size(); i++) {
			if (i > 0) yumlBuilder.append(",");
			Relation relation = relations.get(i);
			actorsAndUsecases.remove(relation.getLeft());
			actorsAndUsecases.remove(relation.getRight());
			yumlBuilder.append(relation.toYuml());
			
			logger.debug(relation.toYuml());
		}
		
		if (relations.size() > 0 && actorsAndUsecases.size() > 0) {
			yumlBuilder.append(",");
		}
		
		for (int i = 0; i < actorsAndUsecases.size(); i++) {
			if (i > 0) yumlBuilder.append(",");
			Clazz clazz = actorsAndUsecases.get(i);
			yumlBuilder.append(clazz.toYuml());
			
			logger.debug(clazz.toYuml());
		}
		
		yumlBuilder.append(urlType);
		return yumlBuilder.toString();
	}
	
	private List<Clazz> extractActorsAndUsecasesOn(IDiagram diagram) {
		IPresentation[] presentations = getPresentations(diagram);
		
		List<Clazz> actorsAndUsecases = new ArrayList<Clazz>();
		for (IPresentation presentation : presentations) {
			IElement presentationModel = presentation.getModel();
			if (presentationModel instanceof IUseCase) {
				actorsAndUsecases.add(new UseCase(presentation, (IUseCase) presentationModel));
			} else if (presentationModel instanceof IClass && isActor((IClass) presentationModel)) {
				actorsAndUsecases.add(new Clazz(presentation, (IClass) presentationModel));
			}
		}
		return actorsAndUsecases;
	}

	private List<Relation> extractRelationsOn(IDiagram diagram) {
		IPresentation[] presentations = getPresentations(diagram);
		
		List<Relation> relations = new ArrayList<Relation>();
		for (IPresentation presentation : presentations) {
			IElement presentationModel = presentation.getModel();
			if (presentationModel instanceof IInclude 
					|| presentationModel instanceof IExtend
					|| presentationModel instanceof IAssociation) {
				IPresentation sourceEnd = ((ILinkPresentation) presentation).getSourceEnd();
				IPresentation targetEnd = ((ILinkPresentation) presentation).getTargetEnd();
				IElement source = sourceEnd.getModel();
				IElement target = targetEnd.getModel();
				if (source instanceof IClass && target instanceof IClass) {
					relations.add(createRelation(presentation, (IClass) source, (IClass) target));
				}
			} else if (presentationModel instanceof IGeneralization) {
				IClass superType = ((IGeneralization) presentationModel).getSuperType();
				IClass subType = ((IGeneralization) presentationModel).getSubType();
				relations.add(new Generalization(presentation, (IClass) superType, (IClass) subType));
			} else if (presentationModel instanceof IDependency) { // Unsupported on yUML
				INamedElement client = ((IDependency) presentationModel).getClient();
				INamedElement supplier = ((IDependency) presentationModel).getSupplier();
				if (client instanceof IClass && supplier instanceof IClass) {
					relations.add(new Association( presentation, (IClass) client, (IClass) supplier));
				}
			} else if (presentationModel instanceof IUsage) { // Unsupported on yUML
				INamedElement client = ((IUsage) presentationModel).getClient();
				INamedElement supplier = ((IUsage) presentationModel).getSupplier();
				if (client instanceof IClass && supplier instanceof IClass) {
					relations.add(new Association(presentation, (IClass) client, (IClass) supplier));
				}
			} else if (presentationModel instanceof IRealization) { // Unsupported on yUML
				INamedElement client = ((IRealization) presentationModel).getClient();
				INamedElement supplier = ((IRealization) presentationModel).getSupplier();
				if (client instanceof IClass && supplier instanceof IClass) {
					relations.add(new Association(presentation, (IClass) client, (IClass) supplier));
				}
			}
		}
		return relations;
	}
	
	private Relation createRelation(IPresentation presentation, IClass source, IClass target) {
		Relation relation = null;
		
		IElement presentationModel = presentation.getModel();
		if (presentationModel instanceof IInclude
				&& (source instanceof IUseCase && target instanceof IUseCase)) {
			relation = new Include(presentation, (IUseCase) source, (IUseCase) target);
		} else if (presentationModel instanceof IExtend
				&& (source instanceof IUseCase && target instanceof IUseCase)) {
			relation = new Extend(presentation, (IUseCase) source, (IUseCase) target);
		} else if (presentationModel instanceof IAssociation) {
			relation = new Association(presentation, source, target);
		}
		return relation;
	}

	private boolean isActor(IClass model) {
		String[] stereotypes = model.getStereotypes();
		for (String stereotype : stereotypes) {
			if (stereotype.equalsIgnoreCase("actor")) {
				return true;
			}
		}
		return false;
	}
}