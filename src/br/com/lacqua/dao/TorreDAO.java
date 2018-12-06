package br.com.lacqua.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.lacqua.model.Torre;

public class TorreDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3341293440568954066L;

	@SuppressWarnings("unchecked")
	public List<Torre> listarTorres() {
		Query q = criarQuery("SELECT t FROM Torre t");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Torre> listarTorresPorCondominio(Integer idCondominio) {
		Query q = criarQuery("SELECT t FROM Torre t WHERE t.condominio.id = " + idCondominio);
		return q.getResultList();
	}
}