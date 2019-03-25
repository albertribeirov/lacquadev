package br.com.lacqua.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.lacqua.model.Consumo;

public class ConsumoDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5578515206544320500L;

	@SuppressWarnings("unchecked")
	public List<Consumo> listarConsumos(){
		Query q = criarQuery("SELECT c FROM Consumo c");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Consumo> listarConsumoPorCondominio(Integer condominioId) {
		Query q = criarQuery("SELECT c FROM Consumo c WHERE c.condominio.idConsumo = " + condominioId);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Consumo> listarConsumosPorCondominioTorre(Integer idCondominio, Integer idTorre) {
		Query q = criarQuery("SELECT c FROM Consumo c WHERE c.condominio.idConsumo = " + idCondominio + " AND c.torre.idTorre = " + idTorre);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Consumo> listarConsumosPorCondominioTorreMes(Integer pIdCondominio, Integer pIdTorre, Integer pMesReferencia, Integer pAnoReferencia) {
		String torre = "";
		String mesReferencia = "";
		String anoReferencia = "";
		String query = "";
		Query q = null;

		query = "SELECT c FROM Consumo c WHERE c.condominio.id = " + pIdCondominio;

		if (pIdTorre != null) {
			torre = " AND c.torre.id = " + pIdTorre;
			query = query + torre;
		}

		if (pMesReferencia != null) {
			mesReferencia = " AND c.mes = " + pMesReferencia;
			query = query + mesReferencia;
		}

		if (pAnoReferencia != null) {
			anoReferencia = " AND c.ano = " + pAnoReferencia;
			query = query + anoReferencia;
		}
		
		q = criarQuery(query);

		return q.getResultList();
	}
}