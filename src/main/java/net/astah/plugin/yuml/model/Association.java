package net.astah.plugin.yuml.model;

import com.change_vision.jude.api.inf.model.IAssociation;
import com.change_vision.jude.api.inf.model.IAttribute;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.presentation.IPresentation;

public class Association extends Relation {
	public Association(IPresentation presentation, IClass left, IClass right) {
		super(presentation, left, right);
	}

	public String toYuml() {
		IAttribute[] memberEnds = ((IAssociation) getPresentation().getModel()).getMemberEnds();
		String connector = "-";
		if (memberEnds[0].isAggregate()) {
			connector = "+-";
		} else if (memberEnds[1].isAggregate()) {
			connector = "-+";
		} else if (memberEnds[0].isComposite()) {
			connector = "++-";
		} else if (memberEnds[1].isComposite()) {
			connector = "-++";
		}
		
		if (memberEnds[0].getNavigability().equals("Navigable")) {
			connector = "<" + connector;
		}
		
		if (memberEnds[1].getNavigability().equals("Navigable")) {
			connector = connector + ">";
		}
		
		String left = ClassUtils.getNameLabel(getLeft());
		String right = ClassUtils.getNameLabel(getRight());
		return "[" + left + "]" + connector + "[" + right + "]";
	}
}
