package br.com.lacqua.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.model.Torre;
import br.com.lacqua.service.TorreService;

@Named
public class ConverterTorre implements Converter {
	
	@Inject
	TorreService service;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) throws ConverterException {
		
		if (value == null || value.equals("")) {
			return null;

		} else {
			Integer id = Integer.parseInt(value);
			Torre Torre = service.carregar(id);
			return Torre;
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) throws ConverterException {
		if (object == null) {
			return null;

		} else {

			Torre Torre = (Torre) object;
			String resposta = Torre.getId().toString();

			return resposta;
		}
	}
}