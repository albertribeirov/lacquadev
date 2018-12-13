package br.com.lacqua.service;

import java.util.Calendar;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import br.com.lacqua.dao.ApartamentoDAO;
import br.com.lacqua.model.Apartamento;
import br.com.lacqua.model.Log.TipoMensagem;

/**
 * M�todos de neg�cio relacionados � entidade Apartamento
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
			apartamento.setLastModified(Calendar.getInstance().getTime());
			apartamentoDAO.salvar(apartamento);
			logService.log("Apartamento inserido: " + apartamento, TipoMensagem.INFO);

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
			logService.log("Apartamento alterado: " + apartamento, TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Exclui um Apartamento do banco de dados
	 * 
	 * @param integer N�mero de matr�cula do Apartamento a ser exclu�do
	 * @throws ServiceException
	 */
	public void excluir(Integer id) {
		try {
			beginTransaction();

			Apartamento apartamento = apartamentoDAO.carregar(Apartamento.class, id);
			apartamentoDAO.excluir(apartamento);
			logService.log("Apartamento exclu�do: " + apartamento, TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * L� todos os Apartamentos cadastrados no banco de dados
	 * 
	 * @return Lista de Apartamentos cadastrados
	 * @throws ServiceException
	 */
	public List<Apartamento> listarApartamentos() {
		return apartamentoDAO.listarApartamentos();
	}
}