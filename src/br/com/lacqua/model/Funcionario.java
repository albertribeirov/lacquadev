package br.com.lacqua.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "TB_FUNCIONARIO")
public class Funcionario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_FUNCIONARIO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idFuncionario;
	
	@Column(name = "NOME", nullable = false, length = 150)
	private String nome;
	
	@Column(name = "NOME_REFERENCIA", nullable = false, length = 50)
	private String nomeReferencia;
	
	@Column(name = "EMAIL", nullable = false, length = 100)
	private String email;
	
	@Column(name = "CREATETIME", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createDateTime;

	@Column(name = "UPDATETIME", nullable = false)
	@UpdateTimestamp
	private LocalDateTime updateDateTime;

	/*
	 * Getters/Setters
	 */
	
	public Integer getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Integer id) {
		this.idFuncionario = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(LocalDateTime updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
}
