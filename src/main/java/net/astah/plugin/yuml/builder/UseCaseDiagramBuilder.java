package net.astah.plugin.yuml.builder;

import net.astah.plugin.yuml.draw.Direction;
import net.astah.plugin.yuml.draw.DrawType;
import net.astah.plugin.yuml.draw.Size;
import net.astah.plugin.yuml.draw.UrlType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.jude.api.inf.model.IUseCaseDiagram;

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
		return "No implementation";
	}
}