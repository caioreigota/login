package br.com.controle.validate;

import java.util.List;

import org.hibernate.Session;

import br.com.hibernateUtil.Buscador;
import br.com.hibernateUtil.HibernateUtil;
import br.com.hibernateUtil.TemplateTransacao;
import br.com.hibernateUtil.Transacao;
import br.com.modelo.Usuario;

public class UsuarioValidate {

	
	public static Usuario salvar(final Usuario usuario){
		return (Usuario) TemplateTransacao.executar(new Transacao() {
			
			@Override
			public Object executarTransacao(Session session) {
				salvar(usuario, session);
				return usuario;
			}
		});
	}
	
	public static List<Usuario> listar(final Usuario usuario){
		return new Buscador(HibernateUtil.getSession2()).listar(usuario);
	}

	public static Usuario acessarEdicao(String parametroMenu) {
		return new Buscador().selecionar(new Usuario(parametroMenu));
	}
}
