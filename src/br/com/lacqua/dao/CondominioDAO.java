package br.com.lacqua.dao;

import java.util.List;

import br.com.lacqua.model.Condominio;

public interface CondominioDAO {

	public void incluir(Condominio condominio);

	public void atualizar(Condominio condominio);

	public void excluir(Condominio condominio);

	public Condominio buscarPorId(Integer id);

	public List<Condominio> listar();
}