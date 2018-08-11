package br.com.lacqua.bean;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.lacqua.model.Log.TipoMensagem;
import br.com.lacqua.service.LogService;

public abstract class AbstractBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private LogService logService;
	
	/**
	 * Faz o log de erro da exceção no banco de dados e imprime a stack trace no console.
	 * @param exception Exceção ocorrida
	 */
	protected void handleException(Exception exception) {
		try {
			exception.printStackTrace();
			logService.log(exception.toString(), TipoMensagem.ERRO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}