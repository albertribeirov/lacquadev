package br.com.lacqua.service;

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
	 * Insere uma nova torre no banco de dados
	 * 
	 * @param Torre Torre a ser inserida
	 * @throws ServiceException
	 */
	public void salvar(Torre torre) throws Exception {
		try {
			beginTransaction();

			torreDAO.salvar(torre);
			logService.log("Torre inserida: " + torre.getId(), TipoMensagem.INFO);

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
	public void atualizar(Torre torre) throws Exception {
		try {
			beginTransaction();

			torreDAO.alterar(torre);
			logService.log("Torre alterada: " + torre.getId(), TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Exclui uma torre do banco de dados.
	 * 
	 * @param integer Id da torre a ser exclu�da
	 * @throws ServiceException
	 */
	public void excluir(Integer id) throws Exception {
		try {
			beginTransaction();

			Torre torre = torreDAO.carregar(Torre.class, id);
			torreDAO.excluir(torre);
			logService.log("Torre exclu�da: " + id, TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * L� todos as torres cadastradas no banco de dados
	 * 
	 * @return Lista de torres cadastradas
	 * @throws ServiceException
	 */
	public List<Torre> listarTorres() {
		return torreDAO.listarTorres(); 
	}
	
	/**
	 * Lista as torres por condom�nio.
	 * 
	 * @return Lista de torres cadastrados por condom�nio
	 * @throws ServiceException
	 */
	public List<Torre> listarTorresPorCondominio(Integer idCondominio) {
		return torreDAO.listarTorresPorCondominio(idCondominio);
	}
}