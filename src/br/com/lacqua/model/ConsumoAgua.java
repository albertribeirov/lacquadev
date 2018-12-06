package br.com.lacqua.model;

import java.io.Serializable;
import java.util.Calendar;

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
@Table(name = "TB_CONSUMO_AGUA")
public class ConsumoAgua implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_CONSUMO_AGUA")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idConsumoAgua;

	@Column(name = "LEITURA", nullable = false)
	private Integer leitura;

	@Column(name = "MES_DE_REFERENCIA", nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar mesReferenciaLeitura;

	@Column(name = "DIA_REALIZAZAO_LEITURA", nullable = true)
	@Temporal(TemporalType.DATE)
	private Calendar diaRealizacaoLeitura;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFIED", nullable = false)
	private Calendar lastModified;

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
	
	@ManyToOne
	@JoinColumn(name = "ID_TARIFA_COMPESA", nullable = false)
	private TarifaCompesa tarifaCompesa;

	/*
	 * 
	 * Getters e setters
	 * 
	 */

	public Integer getIdConsumoAgua() {
		return idConsumoAgua;
	}

	public void setIdConsumoAgua(Integer idConsumoAgua) {
		this.idConsumoAgua = idConsumoAgua;
	}

	public Integer getId() {
		return idConsumoAgua;
	}

	public void setId(Integer idConsumoAgua) {
		this.idConsumoAgua = idConsumoAgua;
	}

	public Integer getLeitura() {
		return leitura;
	}

	public void setLeitura(Integer leitura) {
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

	public TarifaCompesa getTarifaCompesa() {
		return tarifaCompesa;
	}

	public void setTarifaCompesa(TarifaCompesa tarifaCompesa) {
		this.tarifaCompesa = tarifaCompesa;
	}

	public Calendar getMesReferenciaLeitura() {
		return mesReferenciaLeitura;
	}

	public void setMesReferenciaLeitura(Calendar mesReferenciaLeitura) {
		this.mesReferenciaLeitura = mesReferenciaLeitura;
	}

	public Calendar getDiaRealizacaoLeitura() {
		return diaRealizacaoLeitura;
	}

	public void setDiaRealizacaoLeitura(Calendar diaRealizacaoLeitura) {
		this.diaRealizacaoLeitura = diaRealizacaoLeitura;
	}

	public Calendar getLastModified() {
		return lastModified;
	}

	public void setLastModified(Calendar lastModified) {
		this.lastModified = lastModified;
	}
}
