package net.astah.plugin.yuml.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.presentation.IPresentation;

import net.astah.plugin.yuml.draw.Direction;
import net.astah.plugin.yuml.draw.DrawType;
import net.astah.plugin.yuml.draw.Size;
import net.astah.plugin.yuml.draw.UrlType;

public abstract class DiagramBuilderBase {
	private static final Logger logger = LoggerFactory.getLogger(DiagramBuilderBase.class);
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
	
	protected IPresentation[] getPresentations(IDiagram diagram) {
		IPresentation[] presentations = null;
		try {
			presentations = diagram.getPresentations();
		} catch (InvalidUsingException e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return presentations;
	}
	
	public abstract String toYuml();
}