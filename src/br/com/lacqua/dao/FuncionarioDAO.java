package br.com.lacqua.dao;

import java.util.List;

import javax.persistence.Query;

import br.com.lacqua.model.Funcionario;

public class FuncionarioDAO extends DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1369437798012071837L;

	@SuppressWarnings("unchecked")
	public List<Funcionario> listarFuncionarios() {
		Query q = criarQuery("SELECT * FROM Funcionario f");
		return q.getResultList();
	}
}