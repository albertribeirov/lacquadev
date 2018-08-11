package br.com.lacqua.bean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.com.lacqua.model.Linguagem;

@SuppressWarnings("serial")
@Named("cadastro")
@RequestScoped
public class CadastroBean implements Serializable {

	private String nome;
	private String email;
	private String senha;
	private String senha2;
	private Character sexo;
	private boolean receberEmails;
	private String observacoes;
	private Integer[] linguagens;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}

	public Character getSexo() {
		return sexo;
	}

	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}
	
	public boolean isReceberEmails() {
		return receberEmails;
	}
	
	public void setReceberEmails(boolean receberEmails) {
		this.receberEmails = receberEmails;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	public Integer[] getLinguagens() {
		return linguagens;
	}
	
	public void setLinguagens(Integer[] linguagens) {
		this.linguagens = linguagens;
	}
	
	public Linguagem[] getListaLinguagens() {
		return Linguagem.LINGUAGENS;
	}

	public String cadastrar() {
		if (senha.equals(senha2)) {
			return "usersuccess";
		}
		return null;
	}
	
	public String getLinguagensAsString() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Integer linguagem : linguagens) {
			if(!first) {
				sb.append(", ");
			}
			sb.append(linguagem);
			first = false; 
		}
		return sb.toString();
	}
}