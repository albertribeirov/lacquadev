package br.com.lacqua.service;


import java.io.Serializable;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

/**
 * Superclasse de todos os services da aplicação
 */
@RequestScoped
public abstract class Service implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4450871361695553109L;
	/**
	 * Objeto que representa a transação
	 */
	@Resource
	private UserTransaction ut;
	
	/**
	 * Inicia uma nova transação
	 */
	protected void beginTransaction() {
		try {
			if (ut.getStatus() != Status.STATUS_ACTIVE) {
				ut.begin();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Faz o commit da transação
	 */
	protected void commitTransaction() {
		try {
			if (ut.getStatus() == Status.STATUS_ACTIVE) {
				ut.commit();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Faz o rollback da transação
	 */
	protected void rollbackTransaction() {
		try {
			if (ut.getStatus() == Status.STATUS_ACTIVE) {
				ut.rollback();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}