package br.com.hibernateUtil;


/**
 * Essa classe se responsabiliza por organizar e gerar Alias de atributos compostos para que sejam criadas suas Restri��es.
 * H� uma limita��o, no entanto. A limita��o ocorre quando enviamos nomes com mais de uma composi��o, conforme segue o exemplo abaixo:
 * 
 * "itemPedido.cotacao.solicitacaoCotacao.cliente.nome".
 * 
 */
public class Restricoes {
	
	private Restricao[] restricoes;
	
	public Restricoes(Restricao ... restricoes) {
		this.restricoes = restricoes;
	}
	
    public Restricao[] getRestricoes(){
		return this.restricoes;
	}
}
