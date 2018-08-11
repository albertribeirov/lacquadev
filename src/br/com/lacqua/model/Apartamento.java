package br.com.lacqua.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TB_APARTAMENTO")
public class Apartamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "SEQ_ID_APARTAMENTO", sequenceName = "SEQ_ID_APARTAMENTO")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SEQ_ID_APARTAMENTO")
	private Integer id;

	@Column(name = "NUMERO", nullable = false)
	private int numero;

	@Column(name = "NOME", nullable = true)
	private String torre;

	@Column(name = "ID_CONDOMINIO")
	private Integer idCondominio;

	@Column(name = "ID_CLIENTE")
	private Integer idCliente;

	@Column(name = "SERIAL_HIDROMETRO")
	private String serialHidrometro;

	/*
	 * Getters/Setters
	 */
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getTorre() {
		return torre;
	}

	public void setTorre(String torre) {
		this.torre = torre;
	}

	public Integer getCondominio() {
		return idCondominio;
	}

	public void setCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}

	public Integer getCliente() {
		return idCliente;
	}

	public void setCliente(Integer cliente) {
		this.idCliente = cliente;
	}

	public String getSerialHidrometro() {
		return serialHidrometro;
	}

	public void setSerialHidrometro(String serialHidrometro) {
		this.serialHidrometro = serialHidrometro;
	}
}