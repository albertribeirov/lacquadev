package br.com.lacqua.service;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.service.spi.ServiceException;

import br.com.lacqua.dao.ConsumoDAO;
import br.com.lacqua.model.Consumo;
import br.com.lacqua.model.Log.TipoMensagem;

/**
 * Métodos de negócio relacionados à entidade Consumo
 */
@SuppressWarnings("serial")
public class ConsumoService extends Service {

	@Inject
	private ConsumoDAO consumoDAO;

	@Inject
	private LogService logService;

	/**
	 * Carrega um Consumo cadastrado no banco de dados.
	 * 
	 * @param id Id do consumo que será carregado
	 * @throws ServiceException
	 */
	public Consumo carregar(Integer id) {
		return consumoDAO.carregar(Consumo.class, id);
	}

	/**
	 * Insere um novo ConsumoGas no banco de dados
	 * 
	 * @param consumo Consumo a ser inserido
	 * @throws ServiceException
	 */
	public void inserir(Consumo consumo) {
		try {
			beginTransaction();

			consumoDAO.salvar(consumo);
			logService.log("Consumo inserido: " + consumo.getId(), TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Altera um Consumo cadastrado no banco de dados.
	 * 
	 * @param consumo Consumo a ser alterado
	 * @throws ServiceException
	 */
	public void alterar(Consumo consumo) {
		try {
			beginTransaction();

			consumoDAO.alterar(consumo);
			logService.log("Consumo alterado: " + consumo.getId(), TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Exclui um Consumo do banco de dados
	 * 
	 * @param id Número de matrícula do consumo a ser excluído
	 * @throws ServiceException
	 */
	public void excluir(Integer id) {
		try {
			beginTransaction();

			Consumo consumo = consumoDAO.carregar(Consumo.class, id);
			consumoDAO.excluir(consumo);
			logService.log("Consumo excluído: " + id, TipoMensagem.INFO);

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
	public List<Consumo> listarConsumos() {
		return consumoDAO.listarConsumos();
	}

	public List<Consumo> listarConsumosPorCondominioTorre(Integer idCondominio, Integer idTorre) {
		return consumoDAO.listarConsumosPorCondominioTorre(idCondominio, idTorre);
	}

	public List<Consumo> listarConsumosPorCondominioTorreMes(Consumo pConsumo) {
		Integer idTorre = null;
		if (pConsumo.getTorre() != null) {
			idTorre = pConsumo.getTorre().getId();
		}
		Integer idCondominio = pConsumo.getCondominio().getId();
		Integer mesReferencia = pConsumo.getMes();
		Integer anoReferencia = pConsumo.getAno();
		return consumoDAO.listarConsumosPorCondominioTorreMes(idCondominio, idTorre, mesReferencia, anoReferencia);
	}
}