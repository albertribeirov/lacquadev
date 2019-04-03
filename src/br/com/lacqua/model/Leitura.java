package br.com.lacqua.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "TB_LEITURA")
public class Leitura implements Serializable {

	private static final long serialVersionUID = 1L;

	public Leitura() {

	}

	public Leitura(Condominio condominio, Torre torre, Integer ano, Integer mes) {
		this.condominio = condominio;
		if (torre != null) {
			this.torre = torre;
		}
		this.ano = ano;
		this.mesReferenciaLeitura = mes;
	}

	@Id
	@Column(name = "ID_LEITURA")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "LEITURA", nullable = false, precision = 10, scale = 3)
	private BigDecimal leitura;

	@Column(name = "MES", nullable = false)
	private Integer mesReferenciaLeitura;

	@Column(name = "ANO", nullable = false)
	private Integer ano;

	@Column(name = "DATA_REALIZACAO_LEITURA", nullable = true)
	private Date dataRealizacaoLeitura;

	@Column(name = "CREATETIME", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createDateTime;

	@Column(name = "UPDATETIME", nullable = false, updatable = true)
	@UpdateTimestamp
	private LocalDateTime updateDateTime;

	/*
	 * 
	 * Relacionamentos
	 * 
	 */

	@ManyToOne
	@JoinColumn(name = "ID_CONDOMINIO", nullable = false)
	private Condominio condominio;

	@ManyToOne
	@JoinColumn(name = "ID_TORRE", nullable = true)
	private Torre torre;

	@ManyToOne
	@JoinColumn(name = "ID_APARTAMENTO", nullable = false)
	private Apartamento apartamento;

	@ManyToOne
	@JoinColumn(name = "ID_CLIENTE", nullable = true)
	private Cliente cliente;

	/*
	 * 
	 * Getters e setters
	 * 
	 */

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getLeitura() {
		return leitura;
	}

	public void setLeitura(BigDecimal leitura) {
		this.leitura = leitura;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Torre getTorre() {
		return torre;
	}

	public void setTorre(Torre torre) {
		this.torre = torre;
	}

	public Apartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Integer getMesReferenciaLeitura() {
		return mesReferenciaLeitura;
	}

	public void setMesReferenciaLeitura(Integer mesReferenciaLeitura) {
		this.mesReferenciaLeitura = mesReferenciaLeitura;
	}

	public Date getDataRealizacaoLeitura() {
		return dataRealizacaoLeitura;
	}

	public void setDataRealizacaoLeitura(Date dataRealizacaoLeitura) {
		this.dataRealizacaoLeitura = dataRealizacaoLeitura;
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

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}
}