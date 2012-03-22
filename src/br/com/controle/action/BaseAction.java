package br.com.controle.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import br.com.util.UtilReflection;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validations;

public class BaseAction extends ActionSupport{
	
	private String metodo;
	
	@Action(value = "olamundo", results = {
			@Result(location = "segunda.jsp", name = "segunda") }
	)
	public String execute() {
		return redirecionador(metodo);
	}
	
	public String redirecionador(String metodo){
		return (String) UtilReflection.invocarMetodo(metodo, this);
		
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

}
