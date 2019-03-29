package br.com.lacqua.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "TB_TORRE")
public class Torre implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5478659300404866411L;

	@Id
	@Column(name = "ID_TORRE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "NUMERO", nullable = false)
	private Integer numero;
	
	@Column(name = "NOME", nullable = false, length = 20)
	private String nome;
	
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
	@JoinColumn(name = "ID_CONDOMINIO", nullable = false)
	private Condominio condominio;
	
	@OneToMany(mappedBy = "torre")
	private List<Apartamento> apartamentos = new ArrayList<>();

	@OneToMany(mappedBy = "torre")
	private List<Leitura> consumoDoCondominio = new ArrayList<>();

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

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public List<Apartamento> getApartamentos() {
		return apartamentos;
	}

	public void setApartamentos(List<Apartamento> apartamentos) {
		this.apartamentos = apartamentos;
	}

	public List<Leitura> getConsumoDoCondominio() {
		return consumoDoCondominio;
	}

	public void setConsumoDoCondominio(List<Leitura> consumoDoCondominio) {
		this.consumoDoCondominio = consumoDoCondominio;
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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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
		Torre other = (Torre) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}