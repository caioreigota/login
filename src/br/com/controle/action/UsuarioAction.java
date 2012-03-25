package br.com.controle.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import br.com.controle.validate.UsuarioValidate;
import br.com.hibernateUtil.HibernateUtil;
import br.com.modelo.Usuario;
import br.com.util.Util;
import br.com.util.UtilReflection;

import com.opensymphony.xwork2.ActionSupport;

			public class UsuarioAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	final public static String TELA_ERRO = "erro";
	final public static String TELA_INICIAL = "inicio";
	final public static String TELA_USUARIO_SALVAR = "usuarioSalvar";
	
	private static String UsuarioSalvar = "usuarioSalvar";
	
	private Usuario usuario;
	private List<Usuario> listaUsuario;
	private String metodo;
	private String parametroMenu;
	
	public String getParametroMenu() {
		return parametroMenu;
	}

	public void setParametroMenu(String parametroMenu) {
		this.parametroMenu = parametroMenu;
	}

	public static String getUsuarioSalvar() {
		return UsuarioSalvar;
	}

	public static void setUsuarioSalvar(String usuarioSalvar) {
		UsuarioSalvar = usuarioSalvar;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	@Action(value = "usuario", results = {
			@Result(location = "usuarioTabela.jsp", name = TELA_INICIAL),
			@Result(location = "usuarioSalvar.jsp", name = TELA_USUARIO_SALVAR),
			@Result(location = "erro.jsp", name = TELA_ERRO)
	})
	public String execute() {
		return listarUsuario();
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
	
	public String criarBanco(){
		HibernateUtil.gerarBanco();
		return TELA_INICIAL;
	}

	
	public String listarUsuario(){
		this.listaUsuario = UsuarioValidate.listar(new Usuario());
		return TELA_INICIAL;
	}
	public String criarUsuario(){
		this.setUsuario(null);
		return TELA_USUARIO_SALVAR;
	}
	public String editarUsuario(){
		if(Util.preenchido(this.getParametroMenu())){
			usuario = UsuarioValidate.acessarEdicao(this.getParametroMenu());
		}
		return TELA_USUARIO_SALVAR;
	}
	public String salvar(){
		UsuarioValidate.salvar(usuario);
		super.addActionMessage(getText("usuario.cadastro.sucesso"));
		return listarUsuario();
	}
	public String editar(){
		UsuarioValidate.salvar(usuario);
		super.addActionMessage(getText("usuario.editado.sucesso"));
		return listarUsuario();
	}
	public String excluirUsuario(){
		UsuarioValidate.excluir(UsuarioValidate.acessarEdicao(this.getParametroMenu()));
		super.addActionMessage(getText("usuario.excluido.sucesso"));
		return listarUsuario();
	}
	public String pesquisar(){
		this.setListaUsuario(UsuarioValidate.listar(usuario));
		return TELA_INICIAL;
	}

}
