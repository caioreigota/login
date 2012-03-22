package br.com.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import br.com.framework.entidade.Entidade;

@Entity
@SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario")
public class Usuario implements Entidade{

	@Id
	@GeneratedValue(generator = "seq_usuario")
	private Integer id;
	private String login;
	private String nome;
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

}
