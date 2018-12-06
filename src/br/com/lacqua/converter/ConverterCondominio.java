package br.com.lacqua.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.model.Condominio;
import br.com.lacqua.service.CondominioService;

@Named
public class ConverterCondominio implements Converter {
	
	@Inject
	CondominioService service;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) throws ConverterException {
		
		if (value == null || value.equals("")) {
			return null;

		} else {
			Integer id = Integer.parseInt(value);
			Condominio condominio = service.carregar(id);
			return condominio;
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) throws ConverterException {
		if (object == null) {
			return null;

		} else {

			Condominio condominio = (Condominio) object;
			String resposta = condominio.getId().toString();

			return resposta;
		}
	}
}