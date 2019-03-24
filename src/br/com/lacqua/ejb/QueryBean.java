package br.com.lacqua.ejb;

import java.util.List;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Singleton
@Startup
public class QueryBean {

	@PersistenceContext
	private EntityManager em;
	
	public List<?> executar(String jpql) {
		Query q = em.createQuery(jpql);
		return q.getResultList();
	}
}
