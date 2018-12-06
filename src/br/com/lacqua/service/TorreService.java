package br.com.lacqua.service;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import br.com.lacqua.dao.TorreDAO;
import br.com.lacqua.model.Log.TipoMensagem;
import br.com.lacqua.model.Torre;

/**
 * M�todos de neg�cio relacionados � entidade Torre
 */
@SuppressWarnings("serial")
public class TorreService extends Service {

	@Inject
	private TorreDAO torreDAO;
	
	@Inject
	private LogService logService;
	
	/**
	 * Carrega uma torre cadastrada no banco de dados.
	 * 
	 * @param Condominio
	 * @throws ServiceException
	 */
	public Torre carregar(Integer id) {
		return torreDAO.carregar(Torre.class, id);
	}
	
	/**
	 * Insere uma novo Torre no banco de dados
	 * 
	 * @param Torre Torre a ser inserido
	 * @throws ServiceException
	 */
	public void inserir(Torre torre) {
		try {
			beginTransaction();

			torre.setLastModified(Calendar.getInstance().getTime());
			torreDAO.salvar(torre);
			logService.log("Torre inserido: " + torre.getId(), TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Altera uma torre cadastrada no banco de dados.
	 * 
	 * @param Torre
	 * @throws ServiceException
	 */
	public void alterar(Torre torre) {
		try {
			beginTransaction();

			torre.setLastModified(Calendar.getInstance().getTime());
			torreDAO.alterar(torre);
			logService.log("Torre alterado: " + torre.getId(), TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Exclui uma torre do banco de dados.
	 * 
	 * @param integer N�mero de matr�cula do Torre a ser exclu�do
	 * @throws ServiceException
	 */
	public void excluir(Integer id) {
		try {
			beginTransaction();

			Torre torre = torreDAO.carregar(Torre.class, id);
			torreDAO.excluir(torre);
			logService.log("Torre exclu�da: " + torre.getId(), TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * L� todos as torres cadastradas no banco de dados
	 * 
	 * @return Lista de Condom�nios cadastrados
	 * @throws ServiceException
	 */
	public List<Torre> listarTorres() {
		return torreDAO.listarTorres(); 
	}
	
	/**
	 * Lista as torres por condom�nio.
	 * 
	 * @return Lista de Condom�nios cadastrados
	 * @throws ServiceException
	 */
	public List<Torre> listarTorresPorCondominio(Integer id) {
		return torreDAO.listarTorresPorCondominio(id);
	}
}