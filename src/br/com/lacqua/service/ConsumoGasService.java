package br.com.lacqua.service;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.service.spi.ServiceException;

import br.com.lacqua.dao.ConsumoGasDAO;
import br.com.lacqua.model.ConsumoGas;
import br.com.lacqua.model.Log.TipoMensagem;

/**
 * Métodos de negócio relacionados à entidade ConsumoGas
 */
@SuppressWarnings("serial")
public class ConsumoGasService extends Service {

	@Inject
	private ConsumoGasDAO consumoDAO;

	@Inject
	private LogService logService;

	/**
	 * Carrega um ConsumoGas cadastrado no banco de dados.
	 * 
	 * @param ConsumoGas
	 * @throws ServiceException
	 */
	public ConsumoGas carregar(Integer id) {
		return consumoDAO.carregar(ConsumoGas.class, id);
	}

	/**
	 * Insere um novo ConsumoGasGas no banco de dados
	 * 
	 * @param ConsumoGas ConsumoGas a ser inserido
	 * @throws ServiceException
	 */
	public void inserir(ConsumoGas consumo) {
		try {
			beginTransaction();

			consumo.setLastModified(Calendar.getInstance().getTime());
			consumoDAO.salvar(consumo);
			logService.log("ConsumoGas inserido: " + consumo.getId(), TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Altera um ConsumoGas cadastrado no banco de dados.
	 * 
	 * @param ConsumoGas
	 * @throws ServiceException
	 */
	public void alterar(ConsumoGas consumo) {
		try {
			beginTransaction();

			consumo.setLastModified(Calendar.getInstance().getTime());
			consumoDAO.alterar(consumo);
			logService.log("ConsumoGas alterado: " + consumo.getId(), TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Exclui um ConsumoGas do banco de dados
	 * 
	 * @param integer Número de matrícula do ConsumoGas a ser excluído
	 * @throws ServiceException
	 */
	public void excluir(Integer id) {
		try {
			beginTransaction();

			ConsumoGas consumo = consumoDAO.carregar(ConsumoGas.class, id);
			consumoDAO.excluir(consumo);
			logService.log("ConsumoGas excluído: " + id, TipoMensagem.INFO);

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
	public List<ConsumoGas> listarConsumoGass() {
		return consumoDAO.listarConsumosGas();
	}

	public List<ConsumoGas> listarConsumosPorCondominioTorre(Integer idCondominio, Integer idTorre) {
		return consumoDAO.listarConsumosPorCondominioTorre(idCondominio, idTorre);
	}

	public List<ConsumoGas> listarConsumosPorCondominioTorreMes(ConsumoGas pConsumoGas) {
		Integer idTorre = null;
		if (pConsumoGas.getTorre() != null) {
			idTorre = pConsumoGas.getTorre().getId();
		}
		Integer idCondominio = pConsumoGas.getCondominio().getId();
		Integer mesReferencia = pConsumoGas.getMesReferenciaLeitura();
		Integer anoReferencia = pConsumoGas.getAno();
		return consumoDAO.listarConsumosPorCondominioTorreMes(idCondominio, idTorre, mesReferencia, anoReferencia);
	}
}