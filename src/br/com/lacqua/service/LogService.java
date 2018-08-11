package br.com.lacqua.service;
import java.util.Date;

import javax.inject.Inject;

import br.com.lacqua.dao.LogDAO;
import br.com.lacqua.model.Log;
import br.com.lacqua.model.Log.TipoMensagem;

public class LogService extends Service {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private LogDAO logDAO;

	/**
	 * Insere uma nova mensagem de log no banco de dados
	 * 
	 * @param mensagem Mensagem de log
	 * @param tipo     Tipo da mensagem
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public void log(String mensagem, TipoMensagem tipo) {
		try {
			beginTransaction();

			Log log = new Log();
			log.setData(new Date());
			log.setTipo(tipo);
			log.setMensagem(mensagem);
			logDAO.salvar(log);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
}