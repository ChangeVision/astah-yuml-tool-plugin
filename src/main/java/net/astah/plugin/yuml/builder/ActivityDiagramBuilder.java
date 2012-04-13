package net.astah.plugin.yuml.builder;

import net.astah.plugin.yuml.draw.Direction;
import net.astah.plugin.yuml.draw.DrawType;
import net.astah.plugin.yuml.draw.Size;
import net.astah.plugin.yuml.draw.UrlType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.jude.api.inf.model.IActivityDiagram;

public class ActivityDiagramBuilder extends DiagramBuilderBase {
	private static final Logger logger = LoggerFactory.getLogger(ActivityDiagramBuilder.class);
	private static final String DIAGRAM_TYPE = "/activity/";
	
	public ActivityDiagramBuilder(IActivityDiagram diagram) {
		this(diagram, UrlType.PNG, DrawType.PLAIN, Direction.TOP_DOWN, Size.NORMAL);
	}
	
	public ActivityDiagramBuilder(IActivityDiagram diagram, UrlType urlType) {
		this(diagram, urlType, DrawType.PLAIN, Direction.TOP_DOWN, Size.NORMAL);
	}
	
	public ActivityDiagramBuilder(IActivityDiagram diagram, UrlType urlType, DrawType drawType) {
		this(diagram, urlType, drawType, Direction.TOP_DOWN, Size.NORMAL);
	}
	
	public ActivityDiagramBuilder(IActivityDiagram diagram, UrlType urlType, DrawType drawType, Direction direction) {
		this(diagram, urlType, drawType, direction, Size.NORMAL);
	}
	
	public ActivityDiagramBuilder(IActivityDiagram diagram, UrlType urlType, DrawType drawType, Direction direction, Size size) {
		super(diagram, urlType, drawType, direction, size);
	}
	
	public String toYuml() {
		return "No implementation";
	}
}
