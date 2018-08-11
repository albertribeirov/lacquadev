package br.com.lacqua.dao;

public interface DAOIntf<T> {

	public <T> void salvar(T entity);

	public <T> void alterar(T entity);

	public <T> T carregar(Object id, Class<T> classe);

	public <T> void excluir(T entity);
}