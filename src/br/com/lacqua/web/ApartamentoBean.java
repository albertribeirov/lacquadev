package br.com.lacqua.web;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import br.com.lacqua.model.Apartamento;
import br.com.lacqua.util.Constantes;

@Named("apartamentoBean")
@RequestScoped
public class ApartamentoBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;
	
	@Resource
	private UserTransaction ut;

	private Apartamento apartamento;
	
	private String numero = "";
	private String id = "";
	private String condominioId = "";
	private String clienteId = "";
	private String ativo = "";
	private String serialHidrometro = "";

	public String getNumero() {
		return numero;
	}

	public Apartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getSerialHidrometro() {
		return serialHidrometro;
	}

	public void setSerialHidrometro(String serialHidrometro) {
		this.serialHidrometro = serialHidrometro;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCondominioId() {
		return condominioId;
	}

	public void setCondominioId(String condominioId) {
		this.condominioId = condominioId;
	}

	public String getClienteId() {
		return clienteId;
	}

	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	// Métodos de operações da classe
	public String novo() {
		return Constantes.APARTAMENTO_NOVO;
	}

	// TODO Falta implementação do método salvar (validações)
	public String salvar() {
		return Constantes.APARTAMENTO_SALVAR;
	}
}
