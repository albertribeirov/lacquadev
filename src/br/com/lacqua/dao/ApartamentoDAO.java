package br.com.lacqua.dao;

import java.util.List;

import br.com.lacqua.model.Apartamento;

public interface ApartamentoDAO {

	public void salvar(Apartamento apartamento);

	public void excluir(Apartamento apartamento);

	public void alterar(Apartamento apartamento);

	public Apartamento carregar(Integer integer, Class<Apartamento> class1);

	public List<Apartamento> listarApartamentos();
}