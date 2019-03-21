package br.com.lacqua.service;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.service.spi.ServiceException;

import br.com.lacqua.dao.ConsumoDAO;
import br.com.lacqua.model.Consumo;
import br.com.lacqua.model.Log.TipoMensagem;

/**
 * M�todos de neg�cio relacionados � entidade Consumo
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
	 * @param Consumo
	 * @throws ServiceException
	 */
	public Consumo carregar(Integer id) {
		return consumoDAO.carregar(Consumo.class, id);
	}

	/**
	 * Insere um novo ConsumoGas no banco de dados
	 * 
	 * @param Consumo Consumo a ser inserido
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
	 * @param Consumo
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
	 * @param integer N�mero de matr�cula do Consumo a ser exclu�do
	 * @throws ServiceException
	 */
	public void excluir(Integer id) {
		try {
			beginTransaction();

			Consumo consumo = consumoDAO.carregar(Consumo.class, id);
			consumoDAO.excluir(consumo);
			logService.log("Consumo exclu�do: " + id, TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * L� todos os Apartamentos cadastrados no banco de dados
	 * 
	 * @return Lista de Condom�nios cadastrados
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