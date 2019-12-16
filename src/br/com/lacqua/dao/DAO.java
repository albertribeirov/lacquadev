package br.com.lacqua.dao;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@SuppressWarnings("serial")
@RequestScoped
public abstract class DAO implements Serializable {
	
	public static final String ID = "id";
	public static final String NOME = "nome";
	public static final String CNPJ = "cnpj";
	public static final String DATA = "data";
	
	@PersistenceContext
	private transient EntityManager em;

	public <T> T carregar(Class<T> classe, Object id) {
		return em.find(classe, id);
	}

	public <T> void salvar(T entity) {
		em.persist(entity);
	}

	public <T> void alterar(T entity) {
		em.merge(entity);
	}

	public <T> void excluir(T entity) {
		em.remove(entity);
	}

	protected Query criarQuery(String query) {
		return em.createQuery(query);
	}
}