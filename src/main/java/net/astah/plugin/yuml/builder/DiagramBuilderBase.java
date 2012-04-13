package net.astah.plugin.yuml.builder;

import net.astah.plugin.yuml.draw.Direction;
import net.astah.plugin.yuml.draw.DrawType;
import net.astah.plugin.yuml.draw.Size;
import net.astah.plugin.yuml.draw.UrlType;

import com.change_vision.jude.api.inf.model.IDiagram;

public abstract class DiagramBuilderBase {
	public static final String DEFAULT_URL_PREFIX = "http://yuml.me/diagram/";

	protected IDiagram diagram;
	protected UrlType urlType;
	protected DrawType drawType;
	protected Direction direction;
	protected Size size;

	public DiagramBuilderBase(IDiagram diagram) {
		this(diagram, UrlType.PNG, DrawType.PLAIN, Direction.TOP_DOWN, Size.NORMAL);
	}
	
	public DiagramBuilderBase(IDiagram diagram, UrlType urlType) {
		this(diagram, urlType, DrawType.PLAIN, Direction.TOP_DOWN, Size.NORMAL);
	}
	
	public DiagramBuilderBase(IDiagram diagram, UrlType urlType, DrawType drawType) {
		this(diagram, urlType, drawType, Direction.TOP_DOWN, Size.NORMAL);
	}
	
	public DiagramBuilderBase(IDiagram diagram, UrlType urlType, DrawType drawType, Direction direction) {
		this(diagram, urlType, drawType, direction, Size.NORMAL);
	}
	
	public DiagramBuilderBase(IDiagram diagram, UrlType urlType, DrawType drawType, Direction direction, Size size) {
		this.diagram = diagram;
		this.drawType = drawType;
		this.direction = direction;
		this.size = size;
		this.urlType = urlType;
	}
	
	public abstract String toYuml();
}