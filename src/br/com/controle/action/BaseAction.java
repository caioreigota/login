package br.com.controle.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Validations
public class BaseAction extends ActionSupport{
	
	
	@Action(value = "olamundo", results = {
			@Result(location = "segunda.jsp", name = "segunda") }
	)
	public String execute() {
		System.out.println("Executando a lógica com Struts2");
		return "segunda";
	}

}
