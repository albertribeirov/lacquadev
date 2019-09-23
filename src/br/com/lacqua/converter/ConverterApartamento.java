package br.com.lacqua.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.model.Apartamento;
import br.com.lacqua.service.ApartamentoService;

@Named
public class ConverterApartamento implements Converter {
	
	@Inject
	ApartamentoService service;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) throws ConverterException {
		
		if (value == null || value.equals("")) {
			return null;

		} else {
			Integer id = Integer.parseInt(value);
			Apartamento apartamento = service.carregar(id);
			return apartamento;
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) throws ConverterException {
		if (object == null) {
			return null;

		} else {

			Apartamento apartamento = (Apartamento) object;
			String resposta = apartamento.getId().toString();

			return resposta;
		}
	}
}