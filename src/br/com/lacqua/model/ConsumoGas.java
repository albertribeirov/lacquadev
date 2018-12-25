package br.com.lacqua.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_CONSUMO_GAS")
public class ConsumoGas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_CONSUMO_GAS")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idConsumoGas;

	@Column(name = "LEITURA", nullable = false, scale = 3)
	private BigDecimal leitura;

	@Column(name = "MES_DE_REFERENCIA", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date mesReferenciaLeitura;

	@Column(name = "DIA_REALIZACAO_LEITURA", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date diaRealizacaoLeitura;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFIED", nullable = false)
	private Date lastModified;

	/*
	 * 
	 * Relacionamentos
	 * 
	 */

	@ManyToOne
	@JoinColumn(name = "ID_CONDOMINIO", nullable = false)
	private Condominio condominio;

	@ManyToOne
	@JoinColumn(name = "ID_TORRE", nullable = false)
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

	public Integer getIdConsumoAgua() {
		return idConsumoGas;
	}

	public void setIdConsumoAgua(Integer idConsumoGas) {
		this.idConsumoGas = idConsumoGas;
	}

	public Integer getId() {
		return idConsumoGas;
	}

	public void setId(Integer idConsumoGas) {
		this.idConsumoGas = idConsumoGas;
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

	public Date getMesReferenciaLeitura() {
		return mesReferenciaLeitura;
	}

	public void setMesReferenciaLeitura(Date mesReferenciaLeitura) {
		this.mesReferenciaLeitura = mesReferenciaLeitura;
	}

	public Date getDiaRealizacaoLeitura() {
		return diaRealizacaoLeitura;
	}

	public void setDiaRealizacaoLeitura(Date diaRealizacaoLeitura) {
		this.diaRealizacaoLeitura = diaRealizacaoLeitura;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
}
