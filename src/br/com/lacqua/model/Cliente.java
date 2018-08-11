package br.com.lacqua.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TB_CLIENTE")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "SEQ_ID_CLIENTE", sequenceName = "SEQ_ID_CLIENTE")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ID_CLIENTE")
	private Integer id;

	@Column(name = "NOME", nullable = false, length = 150)
	private String nome;
	
	@Column(name = "NOME_REFERENCIA", nullable = false, length = 50)
	private String nomeReferencia;

	@Column(name = "ID_ENDERECO", nullable = true)
	private Integer idEndereco;

	@Column(name = "ID_APARTAMENTO")
	private Integer idApartamento;

	@Column(name = "TELEFONE")
	private String telefone;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "OBSERVACAO")
	private String observacao;

	@Column(name = "ATIVO")
	private Boolean ativo;

	/*
	 * Getters/Setters
	 */
	
	public Boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeReferencia() {
		return nomeReferencia;
	}

	public void setNomeReferencia(String nomeReferencia) {
		this.nomeReferencia = nomeReferencia;
	}

	public Integer getEnderecoId() {
		return idEndereco;
	}

	public void setEnderecoId(Integer idEndereco) {
		this.idEndereco = idEndereco;
	}

	public Integer getApartamentoId() {
		return idApartamento;
	}

	public void setApartamentoId(Integer idApartamento) {
		this.idApartamento = idApartamento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}