package br.com.lacqua.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.lacqua.model.Leitura;

public class LeituraDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5578515206544320500L;

	@SuppressWarnings("unchecked")
	public List<Leitura> listarLeituras(){
		Query q = criarQuery("SELECT l FROM Leitura l");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Leitura> listarConsumoPorCondominio(Integer condominioId) {
		Query q = criarQuery("SELECT l FROM Leitura l WHERE l.condominio.idConsumo = " + condominioId);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Leitura> listarConsumosPorCondominioTorre(Integer idCondominio, Integer idTorre) {
		Query q = criarQuery("SELECT l FROM Leitura l WHERE l.condominio.idConsumo = " + idCondominio + " AND l.torre.idTorre = " + idTorre);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Leitura> listarLeiturasPorCondominioTorreMes(Integer pIdCondominio, Integer pIdTorre, Integer pMesReferencia, Integer pAnoReferencia) {
		String torre = "";
		String mesReferencia = "";
		String anoReferencia = "";
		String query = "";
		Query q = null;

		query = "SELECT l FROM Leitura l WHERE l.condominio.id = " + pIdCondominio;

		if (pIdTorre != null) {
			torre = " AND l.torre.id = " + pIdTorre;
			query = query + torre;
		}

		if (pMesReferencia != null) {
			mesReferencia = " AND l.mesReferenciaLeitura = " + pMesReferencia;
			query = query + mesReferencia;
		}

		if (pAnoReferencia != null) {
			anoReferencia = " AND l.ano = " + pAnoReferencia;
			query = query + anoReferencia;
		}

		q = criarQuery(query);

		return q.getResultList();
	}
}