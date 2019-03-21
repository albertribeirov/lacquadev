package br.com.lacqua.service;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.service.spi.ServiceException;

import br.com.lacqua.dao.LeituraDAO;
import br.com.lacqua.model.Leitura;
import br.com.lacqua.model.Log.TipoMensagem;

/**
 * Métodos de negócio relacionados à entidade Leitura
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
	 * @param integer Número de matrícula do Leitura a ser excluído
	 * @throws ServiceException
	 */
	public void excluir(Integer id) {
		try {
			beginTransaction();

			Leitura consumo = consumoDAO.carregar(Leitura.class, id);
			consumoDAO.excluir(consumo);
			logService.log("Leitura excluído: " + id, TipoMensagem.INFO);

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
	public List<Leitura> listarLeituras() {
		return consumoDAO.listarLeituras();
	}

	public List<Leitura> listarConsumosPorCondominioTorre(Integer idCondominio, Integer idTorre) {
		return consumoDAO.listarConsumosPorCondominioTorre(idCondominio, idTorre);
	}

	public List<Leitura> listarLeiturasPorCondominioTorreMes(Leitura pLeitura, Integer pAno, Integer pMes) {
		Integer idTorre = null;
		if (pLeitura.getTorre() != null) {
			idTorre = pLeitura.getTorre().getId();
		}
		Integer idCondominio = pLeitura.getCondominio().getId();
		Integer mesReferencia = pMes;
		Integer anoReferencia = pAno;
		return consumoDAO.listarLeiturasPorCondominioTorreMes(idCondominio, idTorre, mesReferencia, anoReferencia);
	}
}