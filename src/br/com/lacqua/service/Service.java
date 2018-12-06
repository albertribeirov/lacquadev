package br.com.lacqua.service;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.transaction.UserTransaction;
import javax.transaction.Status;

/**
 * Superclasse de todos os services da aplica��o
 */
@RequestScoped
public abstract class Service implements Serializable {

	private static final long serialVersionUID = -7516038192555296282L;

	@Resource
	private UserTransaction ut;
	
	/*
	 * Inicia a transa��o
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
	 * Faz o commit da transa��o
	 */
	protected void commitTransaction() {
		try {
			if (ut.getStatus() != Status.STATUS_ACTIVE) {
				ut.commit();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Faz o rollback da transa��o
	 */
	protected void rollbackTransaction() {
		try {
			if (ut.getStatus() != Status.STATUS_ACTIVE) {
				ut.rollback();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}