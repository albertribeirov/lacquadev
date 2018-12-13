package br.com.lacqua.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.lacqua.model.ConsumoGas;

public class ConsumoGasDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5578515206544320500L;

	@SuppressWarnings("unchecked")
	public List<ConsumoGas> listarConsumosGas(){
		Query q = criarQuery("SELECT c FROM ConsumoGas c");
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<ConsumoGas> listarConsumosGasPorCondominio(Integer idCondominio) {
		Query q = criarQuery("SELECT c FROM ConsumoGas c WHERE c.condominio.idCondominio = " + idCondominio);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<ConsumoGas> listarConsumosGasPorApartamento(Integer idApartamento) {
		Query q = criarQuery("SELECT c FROM ConsumoGas c WHERE c.apartamento.idApartamento = " + idApartamento);
		return q.getResultList();
	}
}