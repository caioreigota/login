package br.com.controle.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import br.com.controle.validate.UsuarioValidate;
import br.com.modelo.Usuario;
import br.com.util.UtilReflection;

import com.opensymphony.xwork2.ActionSupport;

public class UsuarioAction extends BaseAction{
	
	private static String INICIO = "inicio";
	private static String UsuarioSalvar = "usuarioSalvar";
	
	private Usuario usuario;
	private List<Usuario> listaUsuario;
	
	@Action(value = "usuario", results = {
			@Result(location = "index.jsp", name = "inicio"),
			@Result(location = "UsuarioSalvar.jsp", name = "usuarioSalvar")
	})
	
	public String execute() {
		return redirecionador(this.getMetodo());
	}
	
	public String redirecionador(String metodo){
		return (String) UtilReflection.invocarMetodo(metodo, this);
		
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	
	public String caregarLista(){
		listaUsuario = UsuarioValidate.listar(new Usuario());
		return INICIO;
	}

}
