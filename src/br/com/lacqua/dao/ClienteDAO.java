package br.com.lacqua.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.lacqua.model.Cliente;

public class ClienteDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3077354785508887881L;

	@SuppressWarnings("unchecked")
	public List<Cliente> listarClientes() {
		Query q = criarQuery("SELECT c FROM Cliente c");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> listarClientesPorCondominio(Integer idCondominio) {
		Query q = criarQuery("SELECT c FROM Cliente c WHERE c.condominio.idCondominio = " + idCondominio);
		return q.getResultList();
	}
}