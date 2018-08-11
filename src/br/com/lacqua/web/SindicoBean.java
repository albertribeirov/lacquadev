package br.com.lacqua.web;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import br.com.lacqua.model.Sindico;
import br.com.lacqua.util.Constantes;

@Named("sindicoBean")
@RequestScoped
public class SindicoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Sindico sindico;
	
	@PersistenceContext
	private EntityManager em;
	
	@Resource
	private UserTransaction ut;
	
	
	public Sindico getSindico() {
		return sindico;
	}

	public void setSindico(Sindico sindico) {
		this.sindico = sindico;
	}
	
	public String novo() {
		return Constantes.SINDICO_NOVO;
	}
}
