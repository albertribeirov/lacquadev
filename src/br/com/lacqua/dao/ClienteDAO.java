package br.com.lacqua.dao;

import java.util.List;

import br.com.lacqua.model.Cliente;

public interface ClienteDAO {

	public void salvar(Cliente cliente);

	public void atualizar(Cliente cliente);

	public void excluir(Cliente cliente);

	public Cliente buscarPorId(Integer id);

	public List<Cliente> listar();
}