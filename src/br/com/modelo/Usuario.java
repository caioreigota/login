package br.com.modelo;

import javax.persistence.Id;

import org.hibernate.annotations.Entity;

import br.com.framework.entidade.Entidade;

@Entity
public class Usuario implements Entidade{

	@Id
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
