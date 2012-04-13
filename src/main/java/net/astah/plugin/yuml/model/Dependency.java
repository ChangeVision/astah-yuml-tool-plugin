package net.astah.plugin.yuml.model;

import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.presentation.IPresentation;

public class Dependency extends Relation {
	public Dependency(IPresentation presentation, IClass left, IClass right) {
		super(presentation, left, right);
	}

	public String toYuml() {
		String left = ClassUtils.getNameLabel(getLeft());
		String right = ClassUtils.getNameLabel(getRight());
		return "[" + left + "]-.->[" + right + "]";
	}
}
