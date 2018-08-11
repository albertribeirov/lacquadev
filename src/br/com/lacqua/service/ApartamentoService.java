package br.com.lacqua.service;

import java.util.List;

import javax.inject.Inject;
import br.com.lacqua.dao.ApartamentoDAO;
import br.com.lacqua.model.Apartamento;
import br.com.lacqua.model.Log.TipoMensagem;

/**
 * M�todos de neg�cio relacionados � entidade Apartamento
 */
public class ApartamentoService extends Service {
	private static final long serialVersionUID = -7686640269165805365L;

	@Inject
	private ApartamentoDAO ApartamentoDAO;
	
	@Inject
	private LogService logService;

	/**
	 * Insere um novo Apartamento no banco de dados
	 * 
	 * @param Apartamento Apartamento a ser inserido
	 * @throws ServiceException
	 */
	public void inserir(Apartamento Apartamento) {
		try {
			beginTransaction();

			ApartamentoDAO.salvar(Apartamento);
			logService.log("Apartamento inserido: " + Apartamento, TipoMensagem.INFO);

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
	public void alterar(Apartamento Apartamento) {
		try {
			beginTransaction();

			ApartamentoDAO.alterar(Apartamento);
			logService.log("Apartamento alterado: " + Apartamento, TipoMensagem.INFO);

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
	public void excluir(Integer integer) {
		try {
			beginTransaction();

			Apartamento Apartamento = ApartamentoDAO.carregar(integer, Apartamento.class);
			ApartamentoDAO.excluir(Apartamento);
			logService.log("Apartamento exclu�do: " + "alterar", TipoMensagem.INFO);

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
		return ApartamentoDAO.listarApartamentos();
	}
}