package br.com.lacqua.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.lacqua.model.Condominio;

@SuppressWarnings("serial")
public class CondominioDAO extends DAO {

	@SuppressWarnings("unchecked")
	public List<Condominio> listarCondominios() {
		Query q = criarQuery("SELECT c FROM Condominio c ORDER BY c.nome");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Condominio> listarMoradoresCondominio(Integer condominioId) {
		Query q = criarQuery("SELECT c FROM Cliente c WHERE c.condominio.idCondominio = " + condominioId + " ORDER BY c.nome");
		return q.getResultList();
	}

	public boolean buscarCondominioPorNome(String nome, Integer id) {
		String query = "SELECT COUNT(c) FROM Condominio c WHERE c.nome = :nome";

		if (id != null) {
			query += " AND c.id != :id ";
		}

		Query q = criarQuery(query).setParameter(NOME, nome);
		 
		if (id != null) {
			 q.setParameter(ID, id);
		 }
		 
		long count = (Long) q.getResultList().get(0);
		return count > 0;
	}

	public boolean buscarCondominioPorCNPJ(String cnpj, Integer id) {
		String query = "SELECT COUNT(c) FROM Condominio c WHERE c.cnpj = :cnpj";
		boolean isIdValido = false;
		
		if (id != null) {
			query += " AND c.id != :id";
			isIdValido = true;
		}

		Query q = criarQuery(query).setParameter(CNPJ, cnpj);
		
		if (isIdValido) {
			q.setParameter(ID, id);
		}
		
		long count = (Long) q.getResultList().get(0);
		return count > 0;
	}
}