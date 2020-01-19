package br.com.lacqua.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.model.Torre;
import br.com.lacqua.service.TorreService;

@Named
public class ConverterTorre implements Converter {
	
	@Inject
	TorreService service;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		
		if (value == null || value.equals("")) {
			return null;

		} else {
			Integer id = Integer.parseInt(value);
			return service.carregar(id);
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		if (object == null) {
			return null;

		} else {

			Torre torre = (Torre) object;
			return torre.getId().toString();
		}
	}
}