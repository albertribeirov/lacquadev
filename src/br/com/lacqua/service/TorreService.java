package br.com.lacqua.service;

import java.util.List;

import javax.inject.Inject;
import br.com.lacqua.dao.TorreDAO;
import br.com.lacqua.model.Log.TipoMensagem;
import br.com.lacqua.model.Torre;

/**
 * Métodos de negócio relacionados à entidade Torre
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
     */
	public void salvar(Torre torre) {
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
	 */
	public void atualizar(Torre torre) {
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
	 * @param integer Id da torre a ser excluída
	 * @throws ServiceException
	 */
	public void excluir(Integer id) {
		try {
			beginTransaction();

			Torre torre = torreDAO.carregar(Torre.class, id);
			torreDAO.excluir(torre);
			logService.log("Torre excluída: " + id, TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Lê todos as torres cadastradas no banco de dados
	 * 
	 * @return Lista de torres cadastradas
	 * @throws ServiceException
	 */
	public List<Torre> listarTorres() {
		return torreDAO.listarTorres(); 
	}
	
	/**
	 * Lista as torres por condomínio.
	 * 
	 * @return Lista de torres cadastrados por condomínio
	 * @throws ServiceException
	 */
	public List<Torre> listarTorresPorCondominio(Integer idCondominio) {
		return torreDAO.listarTorresPorCondominio(idCondominio);
	}
}