package br.com.lacqua.model;

import java.io.Serializable;
import java.util.Calendar;
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
@Table(name = "TB_CONSUMO_AGUA")
public class ConsumoAgua implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "SEQ_ID_CONSUMO_AGUA", sequenceName = "SEQ_ID_CONSUMO_AGUA")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_ID_CONSUMO_AGUA")
	private Integer id;
	
	@Column(name = "ID_APARTAMENTO", nullable = false)
	private Integer idApartamento;
	
	@Column(name = "ID_CLIENTE", nullable = true)
	private Integer idCliente;
	
	@Column(name = "LEITURA", nullable = false)
	private Integer leitura;
	
	@Column(name = "MES", nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar mesLeitura;
	
	@Column(name = "DT_HR_UPDATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar diaLeitura;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdApartamento() {
		return idApartamento;
	}

	public void setIdApartamento(Integer idApartamento) {
		this.idApartamento = idApartamento;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getLeitura() {
		return leitura;
	}

	public void setLeitura(Integer leitura) {
		this.leitura = leitura;
	}

	public Calendar getMesLeitura() {
		return mesLeitura;
	}

	public void setMesLeitura(Calendar mesLeitura) {
		this.mesLeitura = mesLeitura;
	}

	public Calendar getDiaLeitura() {
		return diaLeitura;
	}

	public void setDiaLeitura(Calendar diaLeitura) {
		this.diaLeitura = diaLeitura;
	}
}
