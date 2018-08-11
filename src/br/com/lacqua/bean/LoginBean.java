package br.com.lacqua.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@SuppressWarnings("serial")
@Named
@RequestScoped
public class LoginBean implements Serializable {

	private String name;
	private String password;

	/*
	 * Getters e setters
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String doLogin() {
		if ("abc".equals(name) && "123".equals(password)) {
			return "success";
		} else {
			
			FacesContext.getCurrentInstance().addMessage("form:button", new FacesMessage("Login inválido!"));
			return null;
		}
	}
	
	@PostConstruct
	public void init() {
		System.out.println("Bean criado!");
	}
	
	@PreDestroy
	public void destroy() {
		System.out.println("Bean destruido!");
	}
}