package br.com.lacqua.dao;

import java.util.List;

import br.com.lacqua.model.Sindico;

public interface SindicoDAO {

	public void salvar(Sindico sindico);

	public void atualizar(Sindico sindico);

	public void excluir(Sindico sindico);

	public Sindico buscarPorId(Integer id);

	public List<Sindico> listar();
}