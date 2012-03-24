package br.com.hibernateUtil;

import org.hibernate.criterion.Order;

import br.com.modelo.Entidade;



/**
 * Classe responsável pela interface com a classe Order do Hibernate 
 */
public class Ordenacao {
	
	private String nomeAtributo;
	private boolean crescente;
	
	/**
	 * Entidade que possui o atributo que esta sendo referenciado na ordenacao;
	 */
	private Class<? extends Entidade> entidade;
	
	/**
	 * Construtor
	 *  
	 * @param nomeAtributo Nome do atributo da classe que indica o critério de ordenação.
	 * @param crescente Indica se a ordenação será por ordem crescente ou não (decrescente).
	 * @param entidade Entidade que será ordenada.
	 */
	private Ordenacao(String nomeAtributo, boolean crescente, Class<? extends Entidade> entidade) {
		this.nomeAtributo = nomeAtributo;
		this.crescente = crescente;
		this.entidade = entidade;
	}
	
	/**
	 * Utilizado para ordem crescente
	 * 
	 * @param nomeAtributo
	 * @return Ordenacao
	 */
	public static Ordenacao crescente(String nomeAtributo) {
		return new Ordenacao(nomeAtributo, true, null);
	}
	
	
	public static Ordenacao crescente(Class<? extends Entidade> entidade, String atributo){
		return new Ordenacao(atributo, true, entidade);
	}
	
	/**
	 * Utilizado para ordem decrescente
	 * 
	 * @param nomeAtributo
	 * @return Ordenacao
	 */
	public static Ordenacao decrescente(String nomeAtributo) {
		return new Ordenacao(nomeAtributo, false, null);
	}
	
	/**
	 * Retorna um objeto Order (org.hibernate.criterion.Order), que o Hibernate
	 * utiliza para ordenar um criteria.
	 */
	public Order gerarOrder() {
		return gerarOrder(nomeAtributo);
	}
	
	public Order gerarOrder(String attr){
		if(crescente) {
			return Order.asc(attr);
		} else {
			return Order.desc(attr);
		}
	}
	
	
	/** 
	 * Define o atributo utilizado na ordenação
	 * @param nomeAtributo Nome do atributo utilizado na ordenação
	 */
	public void setNomeAtributo(String nomeAtributo) {
	
		this.nomeAtributo = nomeAtributo;
	}

	/**
	 * Retorna o nome do atributo utilizado na ordenação
	 */
	public String getNomeAtributo() {
	
		return nomeAtributo;
	}

	public Class<? extends Entidade> getEntidade() {
		return entidade;
	}

	public void setEntidade(Class<? extends Entidade> entidade) {
		this.entidade = entidade;
	}
}
