package net.astah.plugin.yuml.model;

import java.util.Map;

import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.IClassifierTemplateParameter;
import com.change_vision.jude.api.inf.model.ITemplateBinding;

public class ClassUtils {
	private ClassUtils() {
	}
	
	public static String getNameLabel(IClass clazz) {
		String label = clazz.getName();
		if (label.equals("")) {
			ITemplateBinding[] templateBindings = clazz.getTemplateBindings();
			if (templateBindings.length > 0) {
				ITemplateBinding templateBinding = templateBindings[0];
				IClass template = templateBinding.getTemplate();
				if (template != null) {
					StringBuilder labelBuilder = new StringBuilder();
					labelBuilder.append(template.getName());
					
					@SuppressWarnings("rawtypes")
					Map actualMap = templateBinding.getActualMap();
					IClassifierTemplateParameter[] templateParameters = template.getTemplateParameters();
					if (templateParameters != null && templateParameters.length > 0) {
						labelBuilder.append("<");
						for (int i = 0; i < templateParameters.length; i++) {
							if (i > 0) labelBuilder.append("&sbquo; "); // TODO replace with comma
							labelBuilder.append(actualMap.get(templateParameters[i]));
						}
						labelBuilder.append(">");
					}
					label = labelBuilder.toString();
				}
			}
		}
		return label;
	}
		
}
