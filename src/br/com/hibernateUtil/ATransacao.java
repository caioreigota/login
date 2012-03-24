package br.com.hibernateUtil;

import org.hibernate.Session;


import br.com.modelo.Entidade;


/**
 * Classe abstrata que define uma transacao. Define um template a ser implementado por cada transacao do sistema.
 */
public abstract class ATransacao {

    /**
     * Metodo que contem a logica principal da transacao.
     * 
     * @param <ObjetoRetornado> valor retornado pela logica da tranasacao.
     * @param sessaoAberta Uma conexao ja aberta com o banco e que sera fechada ap�s a execu��o da logica principal.
     * @return
     */
    public abstract Object executarTransacao(Session sessaoAberta);

    /**
     * Indica se a transacao corrente precisa ser finalizada com um COMMIT.
     * 
     * @return
     */
    public abstract Boolean precisaDeCommit();
    
    public void excluir(Entidade entidade, Session sessao){
        
        sessao.delete(entidade);
    }
    
    /**
     * Salva um objeto mas não cria histórico.
     */
    public void salvar(Entidade entidade, Session sessao) {

        sessao.saveOrUpdate(entidade);
    }
    
    /**
     * Atualiza um objeto mas não cria histórico.
     */
    public void merge(Entidade entidade, Session sessao) {
        sessao.merge(entidade);
    }
    
    public void flush(Session sessao) {
        sessao.flush();
    }
}
