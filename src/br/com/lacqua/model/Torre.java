package br.com.lacqua.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "TB_TORRE")
public class Torre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "SEQ_ID_TORRE", sequenceName = "SEQ_ID_TORRE")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ID_TORRE")
	private Integer id;
	
	@Column(name = "NUMERO", nullable = false)
	private Integer numero;
	
	@Column(name = "NOME", nullable = false, length = 20)
	private String nome;
	
	@Column(name = "ID_CONDOMINIO", nullable = false)
	private Integer idCondominio;

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

	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}
}