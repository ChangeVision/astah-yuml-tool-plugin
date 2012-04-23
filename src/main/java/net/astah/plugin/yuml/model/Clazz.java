package net.astah.plugin.yuml.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.presentation.IPresentation;

public class Clazz {
	protected IPresentation presentation;
	protected IClass clazz;
	
	public Clazz(IPresentation presentation, IClass clazz) {
		this.presentation = presentation;
		this.clazz = clazz;
	}

	public String toYuml() {
		String name = ClassUtils.getNameLabel(clazz);
		return "[" + name + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		
		Clazz rhs = (Clazz) obj;
		return new EqualsBuilder()
					.appendSuper(super.equals(obj))
					.append(presentation, rhs.presentation)
					.append(clazz, rhs.clazz)
					.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
					.appendSuper(super.hashCode())
					.append(presentation)
					.append(clazz)
					.toHashCode();
	}
}
