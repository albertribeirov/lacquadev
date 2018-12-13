package br.com.lacqua.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_APARTAMENTO")
public class Apartamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_APARTAMENTO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "NUMERO", nullable = false)
	private String numero;

	@Column(name = "SERIAL_HIDROMETRO", nullable = false, unique = true, length = 20)
	private String serialHidrometro;
	
	@Column(name = "OBSERVACAO", nullable = true, length = 1000)
	private String observacao;
	
	/*
	 * 
	 * Relacionamentos  
	 * 
	 */
	
	@ManyToOne
	@JoinColumn(unique = false, name = "ID_CLIENTE", nullable = true, referencedColumnName = "ID_CLIENTE")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(unique = false, name = "ID_TORRE", nullable = true, referencedColumnName = "ID_TORRE")
	private Torre torre;

	@ManyToOne
	@JoinColumn(unique = false, name = "ID_CONDOMINIO", nullable = false, referencedColumnName = "ID_CONDOMINIO")
	private Condominio condominio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFIED", nullable = false)
	private Date lastModified;
	
	@OneToMany(mappedBy = "apartamento")
	private List<ConsumoGas> consumosGas;

	/*
	 * 
	 * Getters/Setters
	 * 
	 */

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public List<ConsumoGas> getConsumosGas() {
		return consumosGas;
	}

	public void setConsumosGas(List<ConsumoGas> consumosGas) {
		this.consumosGas = consumosGas;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNumero() {
		return numero;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public Torre getTorre() {
		return torre;
	}

	public void setTorre(Torre torre) {
		this.torre = torre;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getSerialHidrometro() {
		return serialHidrometro;
	}

	public void setSerialHidrometro(String serialHidrometro) {
		this.serialHidrometro = serialHidrometro;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
}