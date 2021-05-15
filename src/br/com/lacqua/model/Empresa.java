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
@Table(name = "TB_EMPRESA")
public class Empresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_EMPRESA")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idEmpresa;

	@Column(name = "NOME", nullable = false, length = 150)
	private String nome;

	@Column(name = "NOME_REFERENCIA", nullable = false, length = 50)
	private String nomeReferencia;

	@Column(name = "ID_ENDERECO")
	private Integer idEndereco;

	@Column(name = "CNPJ")
	private String cnpj;

	@Column(name = "EMAIL1")
	private String email1;

	@Column(name = "EMAIL2")
	private String email2;

	@Column(name = "TELEFONE1")
	private String telefone1;

	@Column(name = "TELEFONE2")
	private String telefone2;

	@Column(name = "INSCRICAO_ESTADUAL")
	private String inscricaoEstadual;

	@Column(name = "INSCRICAO_MUNICIPAL")
	private String inscricaoMunicipal;

	@Column(name = "CREATETIME", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createDateTime;

	@Column(name = "UPDATETIME", nullable = false)
	@UpdateTimestamp
	private LocalDateTime updateDateTime;

	/*
	 * 
	 * Relacionamentos
	 * 
	 */

	/*
	 * 
	 * Getters/Setters
	 * 
	 */

	public Integer getId() {
		return idEmpresa;
	}

	public void setId(Integer id) {
		this.idEmpresa = id;
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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	public Integer getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Integer idEndereco) {
		this.idEndereco = idEndereco;
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
