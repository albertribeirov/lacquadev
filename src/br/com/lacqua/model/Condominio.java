package br.com.lacqua.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_CONDOMINIO")
public class Condominio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "SEQ_ID_CONDOMINIO", sequenceName = "SEQ_ID_CONDOMINIO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ID_CONDOMINIO")
	private Integer id;

	@Column(name = "NOME", nullable = false, length = 150)
	private String nome;

	@Column(name = "TAXA_LEITURA", precision = 10, scale = 2, nullable = false)
	private BigDecimal taxaDeLeitura;

	@Column(name = "CNPJ", nullable = true, length = 14)
	private String cnpj;

	@Column(name = "TELEFONE1", nullable = false, length = 11)
	private String telefone1;

	@Column(name = "TELEFONE2", nullable = true, length = 11)
	private String telefone2;

	@Column(name = "INSCRICAO_MUNICIPAL", nullable = true, length = 20)
	private String inscricaoMunicipal;

	@Column(name = "INSCRICAO_ESTADUAL", nullable = true, length = 20)
	private String inscricaoEstadual;

	@Column(name = "ID_ENDERECO", nullable = false)
	private Integer idEndereco;

	@Column(name = "ATIVO")
	private Boolean ativo;
	
	@Column(name = "POCO", nullable = false)
	private Boolean poco;
	
	@Temporal(TemporalType.DATE)
	private Date inicioContrato;
	
	@Temporal(TemporalType.DATE)
	private Date fimContrato;
	
	/*
	 * Getters/Setters
	 */
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

	public BigDecimal getTaxaDeLeitura() {
		return taxaDeLeitura;
	}

	public void setTaxaDeLeitura(BigDecimal taxaDeLeitura) {
		this.taxaDeLeitura = taxaDeLeitura;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public Integer getEndereco() {
		return idEndereco;
	}

	public void setEndereco(Integer endereco) {
		this.idEndereco = endereco;
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

	public Boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Date getInicioContrato() {
		return inicioContrato;
	}

	public void setInicioContrato(Date inicioContrato) {
		this.inicioContrato = inicioContrato;
	}

	public Date getFimContrato() {
		return fimContrato;
	}

	public void setFimContrato(Date fimContrato) {
		this.fimContrato = fimContrato;
	}
}
