package br.com.lacqua.web;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import br.com.lacqua.model.Cliente;
import br.com.lacqua.util.Constantes;

@Named("clienteBean")
@RequestScoped
public class ClienteBean {

	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction ut;

	private Cliente cliente;

	public String novo() {
		return Constantes.CLIENTE_NOVO;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}