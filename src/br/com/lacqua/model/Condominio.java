package br.com.lacqua.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.ws.rs.FormParam;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "TB_CONDOMINIO")
public class Condominio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -165819754007060269L;

	public Condominio(){

	}

	public Condominio(String nome, String cnpj) {
		this.nome = nome;
		this.cnpj = cnpj;
	}

	@Id
	@Column(name = "ID_CONDOMINIO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "NOME", nullable = false, length = 150, unique = true)
	private String nome;

	@Column(name = "TAXA_LEITURA", precision = 10, scale = 2, nullable = false)
	private BigDecimal taxaLeitura;

	@Column(name = "CNPJ", length = 18, unique = true)
	private String cnpj;

	@Column(name = "TELEFONE1", length = 15)
	private String telefone1;

	@Column(name = "TELEFONE2", length = 15)
	private String telefone2;

	@Column(name = "INSCRICAO_MUNICIPAL", length = 20)
	private String inscricaoMunicipal;

	@Column(name = "INSCRICAO_ESTADUAL", length = 20)
	private String inscricaoEstadual;

	@Column(name = "ATIVO", nullable = false)
	private boolean ativo;

	@Column(name = "INICIO_CONTRATO")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate inicioContrato;

	@Column(name = "FIM_CONTRATO")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate fimContrato;

	@Column(name = "OBSERVACAO", length = 1000)
	private String observacao;

	@Column(name = "EMAIL", length = 150)
	private String email;

	@Column(name = "RUA_NUMERO", length = 100)
	private String ruaComNumero;

	@Column(name = "BAIRRO", length = 50)
	private String bairro;

	@Column(name = "CIDADE", length = 50)
	private String cidade;

	@Column(name = "CEP", length = 10)
	private String cep;

	@Column(name = "CREATETIME", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createDateTime;

	@Column(name = "UPDATETIME", nullable = false)
	@UpdateTimestamp
	private LocalDateTime updateDateTime;

	@JsonIgnore
	@OneToMany(mappedBy = "condominio")
	private List<Torre> torres = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "condominio", targetEntity = Apartamento.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Apartamento> apartamentos = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "condominio", targetEntity = Leitura.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Leitura> consumos = new ArrayList<>();

	/*
	 * 
	 * Getters/Setters
	 * 
	 */
	@FormParam("nome")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getTaxaLeitura() {
		return taxaLeitura;
	}

	public void setTaxaLeitura(BigDecimal taxaLeitura) {
		this.taxaLeitura = taxaLeitura;
	}

	@FormParam("cnpj")
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDate getInicioContrato() {
		return inicioContrato;
	}

	public void setInicioContrato(LocalDate inicioContrato) {
		this.inicioContrato = inicioContrato;
	}

	public LocalDate getFimContrato() {
		return fimContrato;
	}

	public void setFimContrato(LocalDate fimContrato) {
		this.fimContrato = fimContrato;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public List<Torre> getTorres() {
		return torres;
	}

	public void setTorres(List<Torre> torres) {
		this.torres = torres;
	}

	public List<Apartamento> getApartamentos() {
		return apartamentos;
	}

	public void setApartamentos(List<Apartamento> apartamentos) {
		this.apartamentos = apartamentos;
	}

	public List<Leitura> getConsumos() {
		return consumos;
	}

	public void setConsumos(List<Leitura> consumos) {
		this.consumos = consumos;
	}
	
	public int compareTo(Condominio condominio) {
		return this.nome.compareTo(condominio.nome);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Condominio other = (Condominio) obj;
		if (id == null) {
			return other.id == null;
		} else
			return id.equals(other.id);
	}

	@Override
	public String toString() {
		return "{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", taxaLeitura=" + taxaLeitura +
				", cnpj='" + cnpj + '\'' +
				", telefone1='" + telefone1 + '\'' +
				", telefone2='" + telefone2 + '\'' +
				", inscricaoMunicipal='" + inscricaoMunicipal + '\'' +
				", inscricaoEstadual='" + inscricaoEstadual + '\'' +
				", ativo=" + ativo +
				", observacao='" + observacao + '\'' +
				", email='" + email + '\'' +
				", ruaComNumero='" + ruaComNumero + '\'' +
				", bairro='" + bairro + '\'' +
				", cidade='" + cidade + '\'' +
				", cep='" + cep + '\'' +
				'}';
	}
}