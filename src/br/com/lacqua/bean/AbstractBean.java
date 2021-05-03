package br.com.lacqua.bean;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.lacqua.model.Log.TipoMensagem;
import br.com.lacqua.service.LogService;

public class AbstractBean implements Serializable {
	
	public static final String ERRO = "Erro";
	public static final String MESSAGE = "message";
	public static final String SUCESSO = "Sucesso";

	@Inject
	private LogService logService;

	/**
	 * Faz o log de erro da exce��o no banco de dados e imprime a stack trace no
	 * console.
	 * 
	 * @param exception Exce��o ocorrida
	 */
	protected void handleException(Exception exception) {
		try {
			exception.printStackTrace();
			logService.log(exception.toString(), TipoMensagem.ERRO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adiciona uma mensagem ao request para ser exibida na tela.
	 */
	protected void addMessageToRequest(String mensagem) {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		request.setAttribute(MESSAGE, mensagem);
	}

	/**
	 * Usa faces-redirect para atualizar a tela.
	 */
	protected String redirect(String outcome) {
		return outcome + "?faces-redirect=true";
	}
}