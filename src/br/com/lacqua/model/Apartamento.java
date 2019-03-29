package br.com.lacqua.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "TB_APARTAMENTO")
public class Apartamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_APARTAMENTO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "NUMERO", nullable = false)
	private Integer numero;

	@Column(name = "OBSERVACAO", nullable = true, length = 1000)
	private String observacao;

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
	@JoinColumn(unique = false, name = "ID_CLIENTE", nullable = true, referencedColumnName = "ID_CLIENTE")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(unique = false, name = "ID_TORRE", nullable = true, referencedColumnName = "ID_TORRE")
	private Torre torre;

	@ManyToOne
	@JoinColumn(unique = false, name = "ID_CONDOMINIO", nullable = false, referencedColumnName = "ID_CONDOMINIO")
	private Condominio condominio;

	@OneToMany(mappedBy = "apartamento")
	private List<Leitura> consumosGas;

	/*
	 * Utilizada apenas para receber a leitura, n�o � persistida no banco.
	 */
	@Transient
	private BigDecimal leitura;

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

	public List<Leitura> getConsumosGas() {
		return consumosGas;
	}

	public void setConsumosGas(List<Leitura> consumosGas) {
		this.consumosGas = consumosGas;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getNumero() {
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

	public BigDecimal getLeitura() {
		return leitura;
	}

	public void setLeitura(BigDecimal leitura) {
		this.leitura = leitura;
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