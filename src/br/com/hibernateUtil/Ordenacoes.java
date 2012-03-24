package br.com.hibernateUtil;


/**
 * Classe que simplifica a adi��o de ordenações nas consultas do Hibernate 
 */
public class Ordenacoes {
	
	/**
	 * Array com os critérios de ordenação que devem ser utilizados na consulta
	 */
	private Ordenacao[] ordenacoes;
	
	public Ordenacoes(Ordenacao ... ordenacoes) {
		this.ordenacoes = ordenacoes;
	}

	public Ordenacao[] getOrdenacoes() {
		return ordenacoes;
	}

	public void setOrdenacoes(Ordenacao[] ordenacoes) {
		this.ordenacoes = ordenacoes;
	}
	
	public boolean vazio(){
		return (this.ordenacoes == null) || (ordenacoes.length == 0);
	}
	
}
