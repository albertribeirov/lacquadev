package br.com.lacqua.bean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("loginBean")
@RequestScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String password;

	public String login() {
		if("albert".equals(name) && "senha".equals(password)) {
			return "cadastrarCondominio";
			
		} else {
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Login inv�lido!"));
			return null;
		}
	}
	
	public String cadastrar() {
		return "cadastrarCondominio";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
