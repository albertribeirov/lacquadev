package br.com.lacqua.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_SINDICO")
public class Sindico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_SINDICO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idSindico;

	@Column(name = "NOME", nullable = false, length = 150)
	private String nome;

	@Column(name = "NOME_REFERENCIA", nullable = false, length = 50)
	private String nomeReferencia;

	@Column(name = "TELEFONE1")
	private String telefone1;

	@Column(name = "TELEFONE2")
	private String telefone2;

	@Column(name = "EMAIL1")
	private String email1;

	@Column(name = "EMAIL2")
	private String email2;

	@Column(name = "DT_ENTRADA")
	@Temporal(TemporalType.DATE)
	private Date mandatoInicio;

	@Column(name = "DT_SAIDA")
	@Temporal(TemporalType.DATE)
	private Date mandatoFim;

	@Column(name = "OBSERVACAO", nullable = true, length = 1000)
	private String observacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFIED", nullable = false)
	private Date lastModified;

	/*
	 * Getters/Setters
	 */

	public Integer getId() {
		return idSindico;
	}

	public void setId(Integer id) {
		this.idSindico = id;
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

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public Date getMandatoInicio() {
		return mandatoInicio;
	}

	public void setMandatoInicio(Date mandatoInicio) {
		this.mandatoInicio = mandatoInicio;
	}

	public Date getMandatoFim() {
		return mandatoFim;
	}

	public void setMandatoFim(Date mandatoFim) {
		this.mandatoFim = mandatoFim;
	}

	public Integer getIdSindico() {
		return idSindico;
	}

	public void setIdSindico(Integer idSindico) {
		this.idSindico = idSindico;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date date) {
		this.lastModified = date;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}