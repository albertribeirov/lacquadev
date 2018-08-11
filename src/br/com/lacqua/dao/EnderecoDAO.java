package br.com.lacqua.dao;

import java.util.List;

import br.com.lacqua.model.Endereco;

public interface EnderecoDAO {

	public void salvar(Endereco endereco);

	public void atualizar(Endereco endereco);

	public void excluir(Endereco endereco);

	public Endereco buscarPorId(Integer id);

	public List<Endereco> listar();
}