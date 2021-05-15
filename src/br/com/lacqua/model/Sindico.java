package br.com.lacqua.model;

import java.io.Serializable;
import java.time.LocalDate;
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
	private LocalDate mandatoInicio;

	@Column(name = "DT_SAIDA")
	private LocalDate mandatoFim;

	@Column(name = "OBSERVACAO", length = 1000)
	private String observacao;

	@Column(name = "CREATETIME", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createDateTime;

	@Column(name = "UPDATETIME", nullable = false)
	@UpdateTimestamp
	private LocalDateTime updateDateTime;

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

	public LocalDate getMandatoInicio() {
		return mandatoInicio;
	}

	public void setMandatoInicio(LocalDate mandatoInicio) {
		this.mandatoInicio = mandatoInicio;
	}

	public LocalDate getMandatoFim() {
		return mandatoFim;
	}

	public void setMandatoFim(LocalDate mandatoFim) {
		this.mandatoFim = mandatoFim;
	}

	public Integer getIdSindico() {
		return idSindico;
	}

	public void setIdSindico(Integer idSindico) {
		this.idSindico = idSindico;
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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}