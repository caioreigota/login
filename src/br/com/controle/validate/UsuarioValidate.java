package br.com.controle.validate;

import java.util.List;

import org.hibernate.Session;

import br.com.framework.hibernate.Util.Buscador;
import br.com.framework.hibernate.Util.TemplateTransacao;
import br.com.framework.hibernate.Util.Transacao;
import br.com.hibernate.HibernateUtil;
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
}
