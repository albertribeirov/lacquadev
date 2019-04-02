package br.com.lacqua.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.lacqua.model.Cliente;

@SuppressWarnings("serial")
public class ClienteDAO extends DAO {

	@SuppressWarnings("unchecked")
	public List<Cliente> listarClientes() {
		Query q = criarQuery("SELECT c FROM Cliente c");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> listarClientesPorCondominio(Integer idCondominio) {
		Query q = criarQuery("SELECT c FROM Cliente c WHERE c.condominio.id = " + idCondominio);
		return q.getResultList();
	}
	
	public boolean buscarClientePorNome(String nome, Integer id) {
		String query = "SELECT COUNT(c) FROM Condominio c WHERE c.nome = '" + nome + "'";

		if (id != null) {
			query += " AND c.id != " + id;
		}

		Query q = criarQuery(query);
		long count = (Long) q.getResultList().get(0);
		return count > 0;
	}
}