package br.com.lacqua.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.lacqua.model.ConsumoAgua;

public class ConsumoAguaDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5578515206544320500L;

	@SuppressWarnings("unchecked")
	public List<ConsumoAgua> listarConsumosAgua(){
		Query q = criarQuery("SELECT * FROM ConsumoAgua c");
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<ConsumoAgua> listarConsumosAguaPorCondominio(Integer idCondominio) {
		Query q = criarQuery("SELECT c FROM ConsumoAgua c WHERE c.condominio.idCondominio = " + idCondominio);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<ConsumoAgua> listarConsumosAguaPorApartamento(Integer idApartamento) {
		Query q = criarQuery("SELECT c FROM ConsumoAgua c WHERE c.apartamento.idApartamento = " + idApartamento);
		return q.getResultList();
	}
}