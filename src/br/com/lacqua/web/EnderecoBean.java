package br.com.lacqua.web;

import java.io.Serializable;
import java.math.BigInteger;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import br.com.lacqua.model.Endereco;
import br.com.lacqua.util.Constantes;

@Named("enderecoBean")
@RequestScoped
public class EnderecoBean implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Endereco endereco;

	public EnderecoBean() {
		endereco = new Endereco();
	}

	private BigInteger id;
	private String ruaComNumero;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;

	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction ut;

	/*
	 * Getters/Setters
	 */

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getRuaComNumero() {
		return ruaComNumero;
	}

	public void setRuaComNumero(String ruaComNumero) {
		this.ruaComNumero = ruaComNumero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String novo() {
		return Constantes.ENDERECO_INCLUIR;
	}

	public String salvar() {
		return Constantes.ENDERECO_SALVAR;
	}
}