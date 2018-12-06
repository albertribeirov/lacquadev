package br.com.lacqua.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("converterTelefone")
public class ConverterTelefone implements Converter {

	 /**
     * <b>M�todo que remove a m�scara do telefone.</b>
     *
     * @param facesContext
     * @param uIcomponent
     * @param telefone
     * @return Object
     */
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uIcomponent, String telefone) {
        if (telefone.trim().equals("")) {
            return null;
        } else {
            telefone = telefone.replace("(", "");
            telefone = telefone.replace(")", "");
            telefone = telefone.replace("-", "");
            return telefone;
        }
    }

    /**
     * <b>M�todo que retorna a String do telefone.</b>
     *
     * @param facesContext
     * @param uIcomponent
     * @param object
     * @return String
     */
    @Override
    public String getAsString(FacesContext facesContext, UIComponent uIcomponent, Object object) {
        String telefone = (String) object;
        String ddd = "(" + telefone.substring(0, 2) + ") ";
        String resposta = "";
        
        if (telefone.length() == 10) {
        	
        	String primeiraParte = telefone.substring(2, 6);
        	String segundaParte = telefone.substring(6, 10);
        	
        	resposta = ddd + primeiraParte + "-" + segundaParte;
        	
        } else if (telefone.length() == 11) {
        	
        	String trecho = telefone.substring(2, 3);
        	String primeiraParte = telefone.substring(3, 7);
        	String segundaParte = telefone.substring(7, 11);
        	
        	resposta = ddd + trecho + "-" + primeiraParte + "-" + segundaParte;
        }
        
        return resposta;
    }
}