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
@Table(name = "TB_FUNCIONARIO")
public class Funcionario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "SEQ_ID_FUNCIONARIO", sequenceName = "SEQ_ID_FUNCIONARIO")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ID_FUNCIONARIO")
	private Integer id;
	
	@Column(name = "NOME", nullable = false, length = 150)
	private String nome;
	
	@Column(name = "NOME_REFERENCIA", nullable = false, length = 50)
	private String nomeReferencia;
	
	@Column(name = "EMAIL", nullable = false, length = 100)
	private String email;

	/*
	 * Getters/Setters
	 */
	
	public Integer getIdFuncionario() {
		return id;
	}

	public void setIdFuncionario(Integer id) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
