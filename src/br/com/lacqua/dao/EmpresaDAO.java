package br.com.lacqua.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.lacqua.model.Empresa;

public class EmpresaDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7487641475509999574L;

	@SuppressWarnings("unchecked")
	public List<Empresa> listarApartamentos() {
		Query q = criarQuery("SELECT * FROM Empresa e");
		return q.getResultList();
	}
}