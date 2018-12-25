package br.com.lacqua.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.lacqua.model.ConsumoGas;

@SuppressWarnings("serial")
public class ConsumoDAO extends DAO {

	@SuppressWarnings("unchecked")
	public List<ConsumoGas> listarConsumos(){
		Query q = criarQuery("SELECT c FROM ConsumoGas c");
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<ConsumoGas> listarConsumoPorCondominio(Integer condominioId) {
		Query q = criarQuery("SELECT c FROM ConsumoGas c WHERE c.condominio.idConsumo = " + condominioId);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<ConsumoGas> listarApartamentosPorCondominioTorre(Integer condominioId) {
		Query q = criarQuery("SELECT c FROM ConsumoGas c WHERE c.condominio.idConsumo = " + condominioId);
		return q.getResultList();
	}
}