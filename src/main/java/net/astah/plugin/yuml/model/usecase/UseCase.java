package net.astah.plugin.yuml.model.usecase;

import net.astah.plugin.yuml.model.ClassUtils;
import net.astah.plugin.yuml.model.Clazz;

import com.change_vision.jude.api.inf.model.IUseCase;
import com.change_vision.jude.api.inf.presentation.IPresentation;

public class UseCase extends Clazz {
	public UseCase(IPresentation presentation, IUseCase clazz) {
		super(presentation, clazz);
	}

	public String toYuml() {
		String name = ClassUtils.getNameLabel(clazz);
		return "(" + name + ")";
	}
}
