package br.com.lacqua.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("converterInscricao")
public class ConverterInscricao implements Converter {

	/**
	 * <b>Método que remove a máscara das inscrições estaduais e municipais.</b>
	 *
	 * @param facesContext
	 * @param uIcomponent
	 * @param inscricao
	 * @return Object
	 */
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uIcomponent, String inscricao) {
		if (inscricao.trim().equals("")) {
			return null;
		} else {
			inscricao = inscricao.replace("-", "");
			return inscricao;
		}
	}

	/**
	 * <b>Método que retorna a String da inscrição estadual e municipal.</b>
	 *
	 * @param facesContext
	 * @param uIcomponent
	 * @param object
	 * @return String
	 */
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uIcomponent, Object object) {
		String inscricao = (String) object;

		inscricao = inscricao.substring(0, 4) + "." + inscricao.substring(4, 7) + "-" + inscricao.substring(7, 9);

		return inscricao;
	}
}