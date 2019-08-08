package br.com.lacqua.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.lacqua.model.Apartamento;

@SuppressWarnings("serial")
public class ApartamentoDAO extends DAO {

	@SuppressWarnings("unchecked")
	public List<Apartamento> listarApartamentos() {
		Query q = criarQuery("SELECT a FROM Apartamento a ORDER BY a.condominio.nome, a.torre asc NULLS FIRST, a.numero");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Apartamento> listarApartamentosPorCondominio(Integer idCondominio) {
		Query q = criarQuery("SELECT a FROM Apartamento a WHERE a.condominio.id = " + idCondominio + " ORDER BY a.condominio.nome, a.numero");
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Apartamento> listarApartamentosPorCondominioTorre(Integer idCondominio, Integer idTorre) {
		Query q;
		String consulta = "SELECT a FROM Apartamento a WHERE a.condominio.id = " + idCondominio;
		
		if (idTorre != null) {
			consulta += " AND a.torre.id = " + idTorre + " ORDER BY a.condominio.nome, a.torre.nome, a.numero";		
		} else {
			consulta += " ORDER BY a.condominio.nome, a.numero";		
		}
		
		q = criarQuery(consulta);
		
		return q.getResultList();
	}
	
	public Apartamento carregarApartamentoPorNumeroTorreCondominio(Apartamento apartamento) {
		Query q = null;
		String consulta = "SELECT a FROM Apartamento";
		
		if (apartamento.getCondominio() != null) {
			consulta += " WHERE a.condominio.id = " + apartamento.getCondominio().getId();
		}
		
		if (apartamento.getTorre() != null) {
			consulta += " AND a.torre.id = " + apartamento.getTorre().getId();
		}
		
		if (!apartamento.getNumero().toString().equals("")) {
			consulta += " AND a.apartamento.numero = " + apartamento.getNumero().toString();			
		}

		q = criarQuery(consulta);
		return (Apartamento) q.getSingleResult();
	}
}