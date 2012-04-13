package net.astah.plugin.yuml.model;

import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.presentation.IPresentation;

public abstract class Relation {
	private IPresentation presentation;
	private IClass left;
	private IClass right;
	
	public Relation(IPresentation presentation, IClass left, IClass right) {
		this.presentation = presentation;
		this.left = left;
		this.right = right;
	}
	
	public IPresentation getPresentation() {
		return presentation;
	}
	
	public IClass getLeft() {
		return left;
	}

	public IClass getRight() {
		return right;
	}
	
	public abstract String toYuml();
}
