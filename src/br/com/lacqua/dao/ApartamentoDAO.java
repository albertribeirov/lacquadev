package br.com.lacqua.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.lacqua.model.Apartamento;

@SuppressWarnings("serial")
public class ApartamentoDAO extends DAO {

	@SuppressWarnings("unchecked")
	public List<Apartamento> listarApartamentos() {
		Query q = criarQuery("SELECT a FROM Apartamento a ORDER BY a.condominio.nome, a.numero");
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
		String where;
		String consulta = "SELECT a FROM Apartamento a WHERE a.condominio.id = " + idCondominio;
		
		if (idTorre != null) {
			where = " AND a.torre.id = " + idTorre;			
			consulta = consulta + where;
		}
		 consulta = consulta + " ORDER BY a.condominio.nome, a.torre.nome, a.numero";
		 q = criarQuery(consulta);
		
		return q.getResultList();
	}
	
	public Apartamento carregarPorCondominioENumero(Integer idCondominio, String numero) {
		Query q = null;
		String consulta = "SELECT a FROM Apartamento";
		
		if (idCondominio != null && !numero.equals("")) {
			consulta = consulta + " WHERE a.condominio.id = " + idCondominio + " AND a.apartamento.numero = " + numero;
			q = criarQuery(consulta);
		}

		return (Apartamento) q.getSingleResult();
	}
}