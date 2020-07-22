package br.com.lacqua.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import br.com.lacqua.model.PrecoGas;

public class PrecoGasDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6407571681319848239L;

	@SuppressWarnings("unchecked")
	public List<PrecoGas> listarPrecoGas(){
		Query q = criarQuery("SELECT p FROM PrecoGas p");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<PrecoGas> consultarPrecoGasVigente(Date pData){
		Query q = criarQuery("SELECT p FROM PrecoGas WHERE p.vigenciaInicio < :data AND p.vigenciaFinal > :data");
		
		if (pData != null) {
			q.setParameter(DATA, pData);
		}
		
		return q.getResultList();
	}
}