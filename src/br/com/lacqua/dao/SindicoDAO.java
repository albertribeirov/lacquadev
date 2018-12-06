package br.com.lacqua.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.lacqua.model.Sindico;

public class SindicoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8972189884469708255L;

	@SuppressWarnings("unchecked")
	public List<Sindico> listarSindicos() {
		Query q = criarQuery("SELECT s FROM Sindico s");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Sindico> listarSindicosPorCondominio(Integer idCondominio) {
		Query q = criarQuery("SELECT s FROM Sindico s WHERE s.condominio.idCondominio = " + idCondominio);
		return q.getResultList();
	}

}