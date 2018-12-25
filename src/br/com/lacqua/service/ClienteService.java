package br.com.lacqua.service;

import java.util.List;

import javax.inject.Inject;
import br.com.lacqua.dao.ClienteDAO;
import br.com.lacqua.model.Cliente;
import br.com.lacqua.model.Log.TipoMensagem;

/**
 * Métodos de negócio relacionados à entidade Cliente
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
	public void inserir(Cliente cliente) throws Exception {
		try {
			beginTransaction();

			clienteDAO.salvar(cliente);
			logService.log("Cliente inserido: " + cliente.getNomeReferencia(), TipoMensagem.INFO);

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
	public void atualizar(Cliente cliente) throws Exception {
		try {
			beginTransaction();

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
	 * @param integer Número de matrícula do Cliente a ser excluído
	 * @throws ServiceException
	 */
	public void excluir(Integer id) throws Exception {
		try {
			beginTransaction();

			Cliente cliente = clienteDAO.carregar(Cliente.class, id);
			clienteDAO.excluir(cliente);
			logService.log("Cliente excluído: " + id, TipoMensagem.INFO);

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
	public List<Cliente> listarClientes() {
		return clienteDAO.listarClientes();
	}
}