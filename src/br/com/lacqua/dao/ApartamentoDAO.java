package br.com.lacqua.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.lacqua.model.Apartamento;

public class ApartamentoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5405082403129027933L;

	@SuppressWarnings("unchecked")
	public List<Apartamento> listarApartamentos() {
		Query q = criarQuery("SELECT a FROM Apartamento a");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Apartamento> listarApartamentosPorCondominio(Integer idCondominio) {
		Query q = criarQuery("SELECT a FROM Apartamento a WHERE a.condominio.idCondominio = " + idCondominio);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Apartamento> listarApartamentosPorCondominioTorre(Integer idCondominio, Integer idTorre) {
		Query q = criarQuery("SELECT a FROM Apartamento a WHERE a.condominio.idCondominio = " + idCondominio + " AND a.torre.idTorre = " + idTorre);
		return q.getResultList();
	}
}