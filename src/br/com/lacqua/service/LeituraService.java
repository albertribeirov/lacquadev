package br.com.lacqua.service;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.service.spi.ServiceException;

import br.com.lacqua.dao.LeituraDAO;
import br.com.lacqua.model.Leitura;
import br.com.lacqua.model.Log.TipoMensagem;

/**
 * M�todos de neg�cio relacionados � entidade Leitura
 */
@SuppressWarnings("serial")
public class LeituraService extends Service {

	@Inject
	private LeituraDAO consumoDAO;

	@Inject
	private LogService logService;

	/**
	 * Carrega um Leitura cadastrado no banco de dados.
	 * 
	 * @param Leitura
	 * @throws ServiceException
	 */
	public Leitura carregar(Integer id) {
		return consumoDAO.carregar(Leitura.class, id);
	}

	/**
	 * Insere um novo LeituraGas no banco de dados
	 * 
	 * @param Leitura Leitura a ser inserido
	 * @throws ServiceException
	 */
	public void inserir(Leitura consumo) {
		try {
			beginTransaction();

			consumo.setLastModified(Calendar.getInstance().getTime());
			consumoDAO.salvar(consumo);
			logService.log("Leitura inserido: " + consumo.getId(), TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Altera um Leitura cadastrado no banco de dados.
	 * 
	 * @param Leitura
	 * @throws ServiceException
	 */
	public void alterar(Leitura consumo) {
		try {
			beginTransaction();

			consumo.setLastModified(Calendar.getInstance().getTime());
			consumoDAO.alterar(consumo);
			logService.log("Leitura alterado: " + consumo.getId(), TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Exclui um Leitura do banco de dados
	 * 
	 * @param integer N�mero de matr�cula do Leitura a ser exclu�do
	 * @throws ServiceException
	 */
	public void excluir(Integer id) {
		try {
			beginTransaction();

			Leitura consumo = consumoDAO.carregar(Leitura.class, id);
			consumoDAO.excluir(consumo);
			logService.log("Leitura exclu�do: " + id, TipoMensagem.INFO);

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
	public List<Leitura> listarConsumosGas() {
		return consumoDAO.listarConsumosGas();
	}

	public List<Leitura> listarConsumosPorCondominioTorre(Integer idCondominio, Integer idTorre) {
		return consumoDAO.listarConsumosPorCondominioTorre(idCondominio, idTorre);
	}

	public List<Leitura> listarConsumosPorCondominioTorreMes(Leitura pLeitura) {
		Integer idTorre = null;
		if (pLeitura.getTorre() != null) {
			idTorre = pLeitura.getTorre().getId();
		}
		Integer idCondominio = pLeitura.getCondominio().getId();
		Integer mesReferencia = pLeitura.getMesReferenciaLeitura();
		Integer anoReferencia = pLeitura.getAno();
		return consumoDAO.listarConsumosPorCondominioTorreMes(idCondominio, idTorre, mesReferencia, anoReferencia);
	}
}