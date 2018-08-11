package br.com.lacqua.dao;

import java.util.List;

import br.com.lacqua.model.Empresa;

public interface EmpresaDAO {

	public void salvar(Empresa empresa);

	public void atualizar(Empresa empresa);

	public void excluir(Empresa empresa);

	public Empresa buscarPorId(Integer id);

	public List<Empresa> listar();
}