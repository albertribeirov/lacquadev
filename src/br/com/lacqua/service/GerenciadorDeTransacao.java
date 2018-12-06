package br.com.lacqua.service;

import javax.persistence.EntityManager;

public class GerenciadorDeTransacao {
	
	private EntityManager manager;
	
	public void executaTransacao() {
		manager.getTransaction().begin();
		
		//TODO Chamar DAOs
		manager.getTransaction().commit();
	}

}
