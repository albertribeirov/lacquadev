package br.com.lacqua.service;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import br.com.lacqua.dao.ClienteDAO;
import br.com.lacqua.model.Cliente;
import br.com.lacqua.model.Log.TipoMensagem;

/**
 * M�todos de neg�cio relacionados � entidade Cliente
 */
@SuppressWarnings("serial")
public class ClienteService extends Service {

	@Inject
	private ClienteDAO clienteDAO;
	
	@Inject
	private LogService logService;
	
	/**
	 * Carrega um cliente cadastrado no banco de dados.
	 * 
	 * @param Condominio
	 * @throws ServiceException
	 */
	public Cliente carregar(Integer id) {
		return clienteDAO.carregar(Cliente.class, id);
	}
	
	/**
	 * Insere um novo Cliente no banco de dados
	 * 
	 * @param Cliente Cliente a ser inserido
	 * @throws ServiceException
	 */
	public void inserir(Cliente cliente) {
		try {
			beginTransaction();

			cliente.setLastModified(Calendar.getInstance().getTime());
			logService.log("Cliente inserido: " + cliente.getNomeReferencia(), TipoMensagem.INFO);
			clienteDAO.salvar(cliente);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Alter um Cliente cadastrado no banco de dados.
	 * 
	 * @param Cliente
	 * @throws ServiceException
	 */
	public void alterar(Cliente cliente) {
		try {
			beginTransaction();

			cliente.setLastModified(Calendar.getInstance().getTime());
			clienteDAO.alterar(cliente);
			logService.log("Cliente alterado: " + cliente.getNomeReferencia(), TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Exclui um Cliente do banco de dados
	 * 
	 * @param integer N�mero de matr�cula do Cliente a ser exclu�do
	 * @throws ServiceException
	 */
	public void excluir(Integer id) {
		try {
			beginTransaction();

			Cliente cliente = clienteDAO.carregar(Cliente.class, id);
			logService.log("Cliente exclu�do: " + cliente.getNomeReferencia(), TipoMensagem.INFO);
			clienteDAO.excluir(cliente);

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
	public List<Cliente> listarClientes() {
		return clienteDAO.listarClientes();
	}
}