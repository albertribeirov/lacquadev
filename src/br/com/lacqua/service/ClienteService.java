package br.com.lacqua.service;

import java.util.List;

import javax.inject.Inject;
import br.com.lacqua.dao.ClienteDAO;
import br.com.lacqua.exception.ValidationException;
import br.com.lacqua.model.Cliente;
import br.com.lacqua.model.Log.TipoMensagem;
import br.com.lacqua.util.Constantes;

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
	public void inserir(Cliente cliente) throws ValidationException {
		try {
			beginTransaction();

			if(clienteDAO.buscarClientePorNome(cliente.getNome(), null)) {
				throw new ValidationException(Constantes.MSG_ERRO_EXISTE_CLIENTE_NOME);
			}
			
			if(clienteDAO.buscarClientePorNome(cliente.getEmail(), null)) {
				throw new ValidationException(Constantes.MSG_ERRO_EXISTE_CLIENTE_EMAIL);
			}
			
			clienteDAO.salvar(cliente);
			logService.log("Cliente inserido: " + cliente.getNome(), TipoMensagem.INFO);

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
	public void atualizar(Cliente cliente) throws ValidationException {
		try {
			beginTransaction();
			
			if(clienteDAO.buscarClientePorNome(cliente.getNome(), cliente.getId())) {
				throw new ValidationException(Constantes.MSG_ERRO_EXISTE_CLIENTE_NOME);
			}
			
			if(clienteDAO.buscarClientePorNome(cliente.getEmail(), cliente.getId())) {
				throw new ValidationException(Constantes.MSG_ERRO_EXISTE_CLIENTE_EMAIL);
			}

			clienteDAO.alterar(cliente);
			logService.log("Cliente alterado: " + cliente.getId(), TipoMensagem.INFO);

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
	public void excluir(Integer id) throws Exception {
		try {
			beginTransaction();

			Cliente cliente = clienteDAO.carregar(Cliente.class, id);
			clienteDAO.excluir(cliente);
			logService.log("Cliente exclu�do: " + id, TipoMensagem.INFO);

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