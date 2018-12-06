package br.com.lacqua.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TARIFA_COMPESA")
public class TarifaCompesa implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6891906259900577063L;

	@Id
	@Column(name = "ID_TARIFA_COMPESA")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "ATE_10000", nullable = false)
	private BigDecimal valorAte10k;

	@Column(name = "10001_20000", nullable = false)
	private BigDecimal valor10kAte20k;
	
	@Column(name = "20001_30000", nullable = false)
	private BigDecimal valor20kAte30k;
	
	@Column(name = "30001_50000", nullable = false)
	private BigDecimal valor30kAte50k;
	
	@Column(name = "50001_90000", nullable = false)
	private BigDecimal valor50kAte90k;
	
	@Column(name = "90001_999999", nullable = false)
	private BigDecimal valor90kAte999k;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFIED", nullable = false)
	private Date lastModified;

	/*
	 * 
	 * Getters e setters
	 * 
	 */

	public BigDecimal getValorAte10k() {
		return valorAte10k;
	}

	public void setValorAte10k(BigDecimal valorAte10k) {
		this.valorAte10k = valorAte10k;
	}

	public BigDecimal getValor10kAte20k() {
		return valor10kAte20k;
	}

	public void setValor10kAte20k(BigDecimal valor10kAte20k) {
		this.valor10kAte20k = valor10kAte20k;
	}

	public BigDecimal getValor20kAte30k() {
		return valor20kAte30k;
	}

	public void setValor20kAte30k(BigDecimal valor20kAte30k) {
		this.valor20kAte30k = valor20kAte30k;
	}

	public BigDecimal getValor30kAte50k() {
		return valor30kAte50k;
	}

	public void setValor30kAte50k(BigDecimal valor30kAte50k) {
		this.valor30kAte50k = valor30kAte50k;
	}

	public BigDecimal getValor50kAte90k() {
		return valor50kAte90k;
	}

	public void setValor50kAte90k(BigDecimal valor50kAte90k) {
		this.valor50kAte90k = valor50kAte90k;
	}

	public BigDecimal getValor90kAte999k() {
		return valor90kAte999k;
	}

	public void setValor90kAte999k(BigDecimal valor90kAte999k) {
		this.valor90kAte999k = valor90kAte999k;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
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
		TarifaCompesa other = (TarifaCompesa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
