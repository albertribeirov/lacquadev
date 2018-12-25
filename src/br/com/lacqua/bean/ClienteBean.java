package br.com.lacqua.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.model.Cliente;
import br.com.lacqua.service.ClienteService;

@SuppressWarnings("serial")
@Named("clienteBean")
@RequestScoped
public class ClienteBean extends AbstractBean {

	@Inject
	private ClienteService clienteService;

	private Cliente cliente;

	private List<Cliente> clientes;

	/*
	 * Listar clientes
	 */
	public List<Cliente> getClientes() throws Exception {
		if (clientes == null) {
			clientes = clienteService.listarClientes();
		}
		return clientes;
	}
	
	/*
	 * Altera um cliente
	 */
	public String alterar(Integer id) {
		cliente = clienteService.carregar(id);
		return null;

	}

	/*
	 * Exclui um cliente
	 */
	public String excluir(Integer id) {
		try {
			clienteService.excluir(id);			
		} catch (Exception e) {
			handleException(e);
		}
		
		this.cliente = null;
		return null;
	}

	/*
	 * Salva um cliente
	 */
	public String salvar() {
		try {
			if (cliente.getId() == null) {
				clienteService.inserir(cliente);
			} else {
				clienteService.atualizar(cliente);
			}

			cliente = null;
			return redirect("cadastrarCliente");

		} catch (Exception e) {
			addMessageToRequest(e.getMessage());
			return null;
		}
	}

	/*
	 * Cadastrar um novo cliente
	 */
	public String novoCliente() {
		return "novoCliente";
	}

	/*
	 * Obter cliente
	 */
	public Cliente getCliente() {
		if (cliente == null) {
			cliente = new Cliente();
		}
		return cliente;
	}

	/*
	 * Setar cliente
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}