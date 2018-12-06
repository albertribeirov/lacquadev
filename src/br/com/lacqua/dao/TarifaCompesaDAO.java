package br.com.lacqua.dao;

import java.util.List;
import javax.persistence.Query;

import br.com.lacqua.model.TarifaCompesa;

@SuppressWarnings("serial")
public class TarifaCompesaDAO extends DAO {

	@SuppressWarnings("unchecked")
	public List<TarifaCompesa> listarTarifasCompesa() {
		Query q = criarQuery("SELECT t FROM TarifaCompesa t");
		return q.getResultList();
	}
}