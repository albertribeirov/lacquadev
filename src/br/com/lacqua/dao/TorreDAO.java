package br.com.lacqua.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.lacqua.model.Torre;

@SuppressWarnings("serial")
public class TorreDAO extends DAO {

	@SuppressWarnings("unchecked")
	public List<Torre> listarTorres() {
		Query q = criarQuery("SELECT t FROM Torre t ORDER BY t.condominio.nome, t.nome");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Torre> listarTorresPorCondominio(Integer idCondominio) {
		Query q = criarQuery("SELECT t FROM Torre t WHERE t.condominio.id = " + idCondominio);
		return q.getResultList();
	}
}