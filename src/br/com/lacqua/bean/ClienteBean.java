package br.com.lacqua.bean;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.model.Apartamento;
import br.com.lacqua.model.Cliente;
import br.com.lacqua.model.Condominio;
import br.com.lacqua.model.Torre;
import br.com.lacqua.service.ClienteService;
import br.com.lacqua.util.Constantes;

@SuppressWarnings("serial")
@Named("clienteBean")
@RequestScoped
public class ClienteBean extends AbstractBean {

	@Inject
	private ClienteService clienteService;

	private Cliente cliente;

	private Condominio condominio;

	private Torre torre;

	private Apartamento apartamento;

	private List<Apartamento> apartamentos;

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

	public List<Cliente> listarClientes(String query) {
		return clientes.stream().filter(c -> c.getNome().toUpperCase().startsWith(query.toUpperCase())).collect(Collectors.toList());
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
			fc.addMessage("message", new FacesMessage("Erro!", "Cliente n�o carregado!"));
		}
		return null;
	}

	/*
	 * Cancela a altera��o
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
			fc.addMessage("message", new FacesMessage("Erro!", "Cliente n�o salvo!"));
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

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Torre getTorre() {
		return torre;
	}

	public void setTorre(Torre torre) {
		this.torre = torre;
	}

	public List<Apartamento> getApartamentos() {
		return apartamentos;
	}

	public void setApartamentos(List<Apartamento> apartamentos) {
		this.apartamentos = apartamentos;
	}

	public Apartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}
}