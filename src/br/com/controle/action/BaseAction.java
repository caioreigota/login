package br.com.controle.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import br.com.util.Util;
import br.com.util.UtilReflection;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validations;

public class BaseAction extends ActionSupport{
	
	final public static String TELA_ERRO = "erro";
	
	private String metodo;
	
	@Action(value = "olamundo", results = {
			@Result(location = "segunda.jsp", name = "segunda"),
			@Result(location = "erro.jsp", name = TELA_ERRO)}
	)
	public String execute() {
		try{
			if(Util.preenchido(metodo)){
				return redirecionador(metodo);
			}else{
				throw new Exception("Parametro metodo não está preenchido");
			}
		}catch(Exception e){
			e.printStackTrace();
			return TELA_ERRO;
		}
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
