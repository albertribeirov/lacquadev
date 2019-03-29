package br.com.lacqua.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.model.Cliente;
import br.com.lacqua.service.ClienteService;
import br.com.lacqua.util.Constantes;

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
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			cliente = clienteService.carregar(id);
			fc.addMessage("message", new FacesMessage("Sucesso!", "Cliente carregado!"));			
			
		} catch (Exception e) {
			handleException(e);
			fc.addMessage("message", new FacesMessage("Erro!", "Cliente não carregado!"));
		}
		return null;
	}
	
	/*
	 * Cancela a alteração
	 */
	public String cancelar() {
		cliente = null;
		clientes = clienteService.listarClientes();
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
		return redirect(Constantes.CLIENTE_CADASTRAR);
	}

	/*
	 * Salva um cliente
	 */
	public String salvar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			if (cliente.getId() == null) {
				clienteService.inserir(cliente);
			} else {
				clienteService.atualizar(cliente);
			}

			cliente = null;
			return redirect(Constantes.CLIENTE_CADASTRAR);

		} catch (Exception e) {
			addMessageToRequest(e.getMessage());
			fc.addMessage("message", new FacesMessage("Erro!", "Cliente não salvo!"));
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