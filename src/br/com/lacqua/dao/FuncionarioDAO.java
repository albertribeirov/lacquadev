package br.com.lacqua.dao;

import java.util.List;

import br.com.lacqua.model.Funcionario;

public interface FuncionarioDAO {

	public void salvar(Funcionario cliente);

	public void atualizar(Funcionario cliente);

	public void excluir(Funcionario cliente);

	public Funcionario buscarPorId(Integer id);

	public List<Funcionario> listar();
}