package br.com.lacqua.dao;

import java.util.List;
import javax.persistence.Query;

import br.com.lacqua.model.Condominio;

@SuppressWarnings("serial")
public class CondominioDAO extends DAO {

	@SuppressWarnings("unchecked")
	public List<Condominio> listarCondominios(){
		Query q = criarQuery("SELECT c FROM Condominio c");
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Condominio> listarMoradoresCondominio(Integer condominioId) {
		Query q = criarQuery("SELECT c FROM Cliente c WHERE c.condominio.idCondominio = " + condominioId);
		return q.getResultList();
	}
}