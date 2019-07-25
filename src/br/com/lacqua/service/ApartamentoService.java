package br.com.lacqua.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import br.com.lacqua.dao.ApartamentoDAO;
import br.com.lacqua.model.Apartamento;
import br.com.lacqua.model.Log.TipoMensagem;

/**
 * Métodos de negócio relacionados à entidade Apartamento
 */
@RequestScoped
public class ApartamentoService extends Service {
	private static final long serialVersionUID = -7686640269165805365L;

	@Inject
	private ApartamentoDAO apartamentoDAO;

	@Inject
	private LogService logService;

	/**
	 * Carrega um apartamento com base no seu ID
	 */
	public Apartamento carregar(Integer id) {
		return apartamentoDAO.carregar(Apartamento.class, id);
	}

	/**
	 * Insere um novo Apartamento no banco de dados
	 * 
	 * @param Apartamento Apartamento a ser inserido
	 * @throws ServiceException
	 */
	public void inserir(Apartamento apartamento) {
		try {
			beginTransaction();
			apartamentoDAO.salvar(apartamento);
			logService.log("Apartamento inserido: " + apartamento.getId(), TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Alter um Apartamento cadastrado no banco de dados.
	 * 
	 * @param Apartamento
	 * @throws ServiceException
	 */
	public void alterar(Apartamento apartamento) {
		try {
			beginTransaction();

			apartamentoDAO.alterar(apartamento);
			logService.log("Apartamento alterado: " + apartamento.getId(), TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Exclui um Apartamento do banco de dados
	 * 
	 * @param integer Número de matrícula do Apartamento a ser excluído
	 * @throws ServiceException
	 */
	public void excluir(Integer id) {
		try {
			beginTransaction();

			Apartamento apartamento = apartamentoDAO.carregar(Apartamento.class, id);
			apartamentoDAO.excluir(apartamento);
			logService.log("Apartamento excluído: " + id, TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Lê todos os Apartamentos cadastrados no banco de dados
	 * 
	 * @return Lista de Apartamentos cadastrados
	 * @throws ServiceException
	 */
	public List<Apartamento> listarApartamentos() {
		return apartamentoDAO.listarApartamentos();
	}

	public List<Apartamento> listarApartamentosPorCondominioTorre(Integer idCondominio, Integer idTorre) {
		return apartamentoDAO.listarApartamentosPorCondominioTorre(idCondominio, idTorre);
	}

	public List<Apartamento> listarApartamentosPorCondominio(Integer idCondominio) {
		return apartamentoDAO.listarApartamentosPorCondominio(idCondominio);
	}
	
	public Apartamento carregarApartamentoPorNumeroTorreCondominio(Apartamento apartamento) {
		return apartamentoDAO.carregarApartamentoPorNumeroTorreCondominio(apartamento);
	}
}