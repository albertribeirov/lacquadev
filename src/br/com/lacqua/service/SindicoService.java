package br.com.lacqua.service;

import java.util.List;

import javax.inject.Inject;
import br.com.lacqua.dao.SindicoDAO;
import br.com.lacqua.model.Sindico;
import br.com.lacqua.model.Log.TipoMensagem;

/**
 * Métodos de negócio relacionados à entidade Sindico
 */
@SuppressWarnings("serial")
public class SindicoService extends Service {

	@Inject
	private SindicoDAO sindicoDAO;
	
	@Inject
	private LogService logService;
	
	/**
	 * Carrega um sindico cadastrado no banco de dados.
	 * 
	 * @param Condominio
	 * @throws ServiceException
	 */
	public Sindico carregar(Integer id) {
		return sindicoDAO.carregar(Sindico.class, id);
	}
	
	/**
	 * Insere um novo Sindico no banco de dados
	 * 
	 * @param Sindico Sindico a ser inserido
	 * @throws ServiceException
	 */
	public void inserir(Sindico sindico) {
		try {
			beginTransaction();

			logService.log("Sindico inserido: " + sindico.getNomeReferencia(), TipoMensagem.INFO);
			sindicoDAO.salvar(sindico);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Alter um Sindico cadastrado no banco de dados.
	 * 
	 * @param Sindico
	 * @throws ServiceException
	 */
	public void alterar(Sindico sindico) {
		try {
			beginTransaction();

			sindicoDAO.alterar(sindico);
			logService.log("Sindico alterado: " + sindico.getNomeReferencia(), TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Exclui um Sindico do banco de dados
	 * 
	 * @param integer Número de matrícula do Sindico a ser excluído
	 * @throws ServiceException
	 */
	public void excluir(Integer id) {
		try {
			beginTransaction();

			Sindico sindico = sindicoDAO.carregar(Sindico.class, id);
			logService.log("Sindico excluído: " + sindico.getNomeReferencia(), TipoMensagem.INFO);
			sindicoDAO.excluir(sindico);

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
	public List<Sindico> listarSindicos() {
		return sindicoDAO.listarSindicos();
	}
}