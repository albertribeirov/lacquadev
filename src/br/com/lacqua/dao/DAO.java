package br.com.lacqua.dao;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@RequestScoped
public abstract class DAO<T> implements DAOIntf<T> {

	@PersistenceContext
	private EntityManager em;

	@Override
	public <T> T carregar(Object id, Class<T> classe) {
		return em.find(classe, id);
	}

	@Override
	public <T> void salvar(T entity) {
		em.persist(entity);
	}

	@Override
	public <T> void alterar(T entity) {
		em.merge(entity);
	}

	@Override
	public <T> void excluir(T entity) {
		em.remove(entity);
	}

	protected Query criarQuery(String query) {
		return em.createQuery(query);
	}
}