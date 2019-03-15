package br.com.lacqua.service;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.service.spi.ServiceException;

import br.com.lacqua.dao.PrecoGasDAO;
import br.com.lacqua.model.PrecoGas;
import br.com.lacqua.model.Log.TipoMensagem;

/**
 * Métodos de negócio relacionados à entidade PrecoGas
 */
@SuppressWarnings("serial")
public class PrecoGasService extends Service {

	@Inject
	private PrecoGasDAO precoDAO;

	@Inject
	private LogService logService;

	/**
	 * Carrega um PrecoGas cadastrado no banco de dados.
	 * 
	 * @param PrecoGas
	 * @throws ServiceException
	 */
	public PrecoGas carregar(Integer id) {
		return precoDAO.carregar(PrecoGas.class, id);
	}

	/**
	 * Insere um novo PrecoGasGas no banco de dados
	 * 
	 * @param PrecoGas PrecoGas a ser inserido
	 * @throws ServiceException
	 */
	public void inserir(PrecoGas preco) {
		try {
			beginTransaction();

			preco.setLastModified(Calendar.getInstance().getTime());
			precoDAO.salvar(preco);
			logService.log("PrecoGas inserido: " + preco.getId(), TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Altera um PrecoGas cadastrado no banco de dados.
	 * 
	 * @param PrecoGas
	 * @throws ServiceException
	 */
	public void alterar(PrecoGas preco) {
		try {
			beginTransaction();

			preco.setLastModified(Calendar.getInstance().getTime());
			precoDAO.alterar(preco);
			logService.log("PrecoGas alterado: " + preco.getId(), TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Exclui um PrecoGas do banco de dados
	 * 
	 * @param integer Id do PrecoGas a ser excluído
	 * @throws ServiceException
	 */
	public void excluir(Integer id) {
		try {
			beginTransaction();

			PrecoGas preco = precoDAO.carregar(PrecoGas.class, id);
			precoDAO.excluir(preco);
			logService.log("PrecoGas excluído: " + id, TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Lê todos os PrecosGas cadastrados no banco de dados
	 * 
	 * @return Lista de PrecosGas cadastrados
	 * @throws ServiceException
	 */
	public List<PrecoGas> listarPrecoGas() {
		return precoDAO.listarPrecoGas();
	}
}