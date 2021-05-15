package br.com.lacqua.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("converterBooleano")
public class ConverterBooleano implements Converter {

	/**
	 * <b>M�todo que converte "True" para "Sim" e "False" para "N�o".</b>
	 *
	 * @param facesContext
	 * @param uIcomponent
	 * @return Object
	 */
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uIcomponent, String booleano) {
		return booleano;
	}

	/**
	 * <b>M�todo que retorna o booleano como Sim ou N�o.</b>
	 *
	 * @param facesContext
	 * @param uIcomponent
	 * @param object
	 * @return String
	 */
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uIcomponent, Object object) {
		Boolean valor = (Boolean) object;
		String status;

		if (valor == Boolean.TRUE) {
			status = "Sim";
		} else {
			status = "N�o";
		}
		return status;
	}
}