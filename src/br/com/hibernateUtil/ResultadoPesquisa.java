package br.com.hibernateUtil;

import java.util.List;

import br.com.modelo.Entidade;


/**
 * Classe que encapsula o resultado de uma pesquisa.
 * O Integer é usado para ser pasado para a tag de paginacao, para que ela possa calcula
 * a quantidade de paginas da pesquisa.
 * @author dalton
 *
 */
public class ResultadoPesquisa<E extends Entidade> {

	private Integer totalRegistros;
	private List<E> resultado;
	
	public Integer getTotalRegistros() {
		return totalRegistros;
	}
	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	public List<E> getResultado() {
		return resultado;
	}
	public void setResultado(List<E> resultado) {
		this.resultado = resultado;
	}
	
}
