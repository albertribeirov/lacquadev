package br.com.lacqua.dao;

import java.util.List;

import br.com.lacqua.model.Torre;

public interface TorreDAO {

	public void incluir(Torre torre);

	public void atualizar(Torre torre);

	public void excluir(Torre torre);

	public Torre buscarPorId(Integer id);

	public List<Torre> listar();
}