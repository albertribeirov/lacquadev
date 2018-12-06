package br.com.lacqua.service;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import br.com.lacqua.dao.CondominioDAO;
import br.com.lacqua.model.Condominio;
import br.com.lacqua.model.Log.TipoMensagem;

/**
 * Métodos de negócio relacionados à entidade Condominio
 */
@SuppressWarnings("serial")
public class CondominioService extends Service {

	@Inject
	private CondominioDAO condominioDAO;

	@Inject
	private LogService logService;

	/**
	 * Carrega um Condominio cadastrado no banco de dados.
	 * 
	 * @param Condominio
	 * @throws ServiceException
	 */
	public Condominio carregar(Integer id) {
		return condominioDAO.carregar(Condominio.class, id);
	}

	/**
	 * Insere um novo Condominio no banco de dados
	 * 
	 * @param Condominio Condominio a ser inserido
	 * @throws ServiceException
	 */
	public void inserir(Condominio condominio) {
		try {
			beginTransaction();

			condominio.setLastModified(Calendar.getInstance().getTime());
			condominioDAO.salvar(condominio);
			logService.log("Condominio inserido: " + condominio, TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Altera um Condominio cadastrado no banco de dados.
	 * 
	 * @param Condominio
	 * @throws ServiceException
	 */
	public void alterar(Condominio condominio) {
		try {
			beginTransaction();

			condominio.setLastModified(Calendar.getInstance().getTime());
			condominioDAO.alterar(condominio);
			logService.log("Condominio alterado: " + condominio, TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Exclui um Condominio do banco de dados
	 * 
	 * @param integer Número de matrícula do Condominio a ser excluído
	 * @throws ServiceException
	 */
	public void excluir(Integer id) {
		try {
			beginTransaction();

			Condominio condominio = condominioDAO.carregar(Condominio.class, id);
			condominioDAO.excluir(condominio);
			logService.log("Condominio excluído: " + condominio, TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Lê todos os Apartamentos cadastrados no banco de dados
	 * 
	 * @return Lista de Condomínios cadastrados
	 * @throws ServiceException
	 */
	public List<Condominio> listarCondominios() {
		return condominioDAO.listarCondominios();
	}
}