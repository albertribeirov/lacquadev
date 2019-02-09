package br.com.lacqua.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.lacqua.model.Apartamento;

@SuppressWarnings("serial")
public class ApartamentoDAO extends DAO {

	@SuppressWarnings("unchecked")
	public List<Apartamento> listarApartamentos() {
		Query q = criarQuery("SELECT a FROM Apartamento a");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Apartamento> listarApartamentosPorCondominio(Integer idCondominio) {
		Query q = criarQuery("SELECT a FROM Apartamento a WHERE a.condominio.id = " + idCondominio);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Apartamento> listarApartamentosPorCondominioTorre(Integer idCondominio, Integer idTorre) {
		Query q;
		String where;
		String consulta = "SELECT a FROM Apartamento a WHERE a.condominio.id = " + idCondominio;
		
		if (idTorre != null) {
			where = " AND a.torre.id = " + idTorre;			
			consulta = consulta + where;
			q = criarQuery(consulta);
		} else {
			q = criarQuery(consulta);
		}
		return q.getResultList();
	}
}