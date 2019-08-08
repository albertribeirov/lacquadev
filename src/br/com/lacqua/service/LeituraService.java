package br.com.lacqua.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.service.spi.ServiceException;

import br.com.lacqua.dao.LeituraDAO;
import br.com.lacqua.exception.ValidationException;
import br.com.lacqua.model.Apartamento;
import br.com.lacqua.model.Leitura;
import br.com.lacqua.model.Log.TipoMensagem;

/**
 * M�todos de neg�cio relacionados � entidade Leitura
 */
@SuppressWarnings("serial")
public class LeituraService extends Service {

	@Inject
	private LeituraDAO leituraDAO;

	@Inject
	private LogService logService;

	/**
	 * Carrega um Leitura cadastrado no banco de dados.
	 * 
	 * @param Leitura
	 * @throws ServiceException
	 */
	public Leitura carregar(Integer id) {
		return leituraDAO.carregar(Leitura.class, id);
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

			leituraDAO.salvar(consumo);
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

			leituraDAO.alterar(consumo);
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

			Leitura consumo = leituraDAO.carregar(Leitura.class, id);
			leituraDAO.excluir(consumo);
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
	public List<Leitura> listarLeituras() {
		return leituraDAO.listarLeituras();
	}

	public List<Leitura> listarConsumosPorCondominioTorre(Integer idCondominio, Integer idTorre) {
		return leituraDAO.listarConsumosPorCondominioTorre(idCondominio, idTorre);
	}

	public List<Leitura> listarLeiturasPorCondominioTorreMes(Leitura pLeitura, Integer pAno, Integer pMes) throws ValidationException {

		List<Leitura> leituras = new ArrayList<Leitura>();
		Integer idTorre = null;
		if (pLeitura.getTorre() != null) {
			idTorre = pLeitura.getTorre().getId();
		}
		Integer idCondominio = pLeitura.getCondominio().getId();
		Integer mesReferencia = pMes;
		Integer anoReferencia = pAno;
		leituras = leituraDAO.listarLeiturasPorCondominioTorreMes(idCondominio, idTorre, mesReferencia, anoReferencia);

		if (leituras.isEmpty() || leituras == null) {
			throw new ValidationException("N�o h� leituras para este condom�nio/torre no m�s informado.");
		}

		return leituras;
	}
	
	public boolean carregarLeituraDoApartamentoNoMesAno(Integer mes, Integer ano, Apartamento apartamento) throws ValidationException {
		if (leituraDAO.carregarLeituraDoApartamentoNoMesAno(mes, ano, apartamento)) {
			throw new ValidationException("Existem leituras registradas neste per�odo.");
		} else {
			return true;
		}
	}
}