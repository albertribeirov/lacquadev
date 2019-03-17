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
@Table(name = "TB_CONSUMO")
public class Consumo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_CONSUMO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "VALOR_CONTA", nullable = false, precision = 10, scale = 2)
	private BigDecimal valorConta;

	@Column(name = "MES", nullable = false)
	private Integer mes;

	@Column(name = "ANO", nullable = false)
	private Integer ano;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFIED", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP()")
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
	@JoinColumn(name = "ID_TORRE", nullable = true)
	private Torre torre;

	@ManyToOne
	@JoinColumn(name = "ID_APARTAMENTO", nullable = false)
	private Apartamento apartamento;

	@ManyToOne
	@JoinColumn(name = "ID_CLIENTE", nullable = true)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "ID_PRECO_GAS", nullable = false)
	private PrecoGas precoGas;

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

	public BigDecimal getValorConta() {
		return valorConta;
	}

	public void setValorConta(BigDecimal valorConta) {
		this.valorConta = valorConta;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
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

	public PrecoGas getPrecoGas() {
		return precoGas;
	}

	public void setPrecoGas(PrecoGas precoGas) {
		this.precoGas = precoGas;
	}

	public Date getLastModified() {
		return lastModified;
	}	
}