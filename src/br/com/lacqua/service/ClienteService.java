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
	 * @param id  Id do condom�nio que ser� buscado no banco
	 */
	public Cliente carregar(Integer id) {
		return clienteDAO.carregar(Cliente.class, id);
	}

	/**
	 * Insere um novo Cliente no banco de dados
	 * 
	 * @param cliente Cliente a ser inserido
	 * @throws ValidationException Exce��o de valida��o
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
	 * @param cliente Cliente que ser� alterado
	 * @throws @throws ValidationException Exce��o de valida��o
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
	 * @param id N�mero de matr�cula do Cliente a ser exclu�do
	 */
	public void excluir(Integer id) {
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
	 */
	public List<Cliente> listarClientes() {
		return clienteDAO.listarClientes();
	}
}