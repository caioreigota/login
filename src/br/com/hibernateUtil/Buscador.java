package br.com.hibernateUtil;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.util.Util;
import br.com.modelo.Entidade;

/**
 * Classe responsavel por todas as consultas feitas ao banco de dados. As consultas sao feitas montando dinamicamente um
 * filtro de busca baseado na Entidade passada como parametro.
 * 
 * @author dalton
 */

public class Buscador {

    private static final Integer PRIMEIRA_PAGINA = 1;
    public static final Integer VALOR_PADRAO_TOTAL_REGISTROS_POR_PAGINA = 10;

    
    public Buscador(SessionFactory sessionFactory){
    	TemplateTransacao.setSessionFactory(sessionFactory);
    }
    
    public Buscador(){
    	
    }
    
    /**
     * Pesquisa que retorna o resultado *sempre* paginado, com um numero limite de registros por pagina.
     * 
     * @param filtro - Filtro usado para fazer a busca
     * @param pagina - Numero da pagina atual, onde o resultado "começa"
     * @param registrosPorpagina - Quantidade de registros por pagina
     * @param ordenacoes - Ordenacoes adicionais a serem aplicadas ao resultado
     * @return
     */
    public <E extends Entidade> ResultadoPesquisa<E> pesquisar(E filtro, Integer pagina, Integer registrosPorpagina,
            Ordenacoes ordenacoes) {
        return pesquisar(filtro, pagina, registrosPorpagina, ordenacoes, MatchMode.ANYWHERE);
    }

    /**
     * Pesquisa passando apenas o filtro de busca
     * 
     * @param usuarioComum
     * @return
     */
    public  <E extends Entidade> ResultadoPesquisa<E> pesquisar(E filtro) {
        return pesquisar(filtro, PRIMEIRA_PAGINA, VALOR_PADRAO_TOTAL_REGISTROS_POR_PAGINA, (Ordenacoes) null);
    }

    /**
     * Pesquisa que retorna o resultado sem qualquer paginacao ou limite de registros.
     * 
     * @param pagina número da página atual
     * @param totalRegistrosPorPagina quantidade de registros que serão exibidos por página
     * @param matchMode TODO (Dalton)
     */
    @SuppressWarnings("unchecked")
    public  <E extends Entidade> ResultadoPesquisa<E> pesquisar(final E filtro, final Integer pagina,
            final Integer totalRegistrosPorPagina, final Ordenacoes ordenacoes, final MatchMode matchMode, final Restricoes restricoes) {

        return (ResultadoPesquisa<E>) TemplateTransacao.executar(new TransacaoSomenteLeitura() {

            @Override
            public Object executarTransacao(Session sessaoAberta) {
                Integer registrosPorPagina = VALOR_PADRAO_TOTAL_REGISTROS_POR_PAGINA;

                List<E> resultado = new ArrayList<E>();

                Criteria criteria = montaCriterioDaBusca(sessaoAberta, filtro, ordenacoes, matchMode, restricoes);

                /* altera o criteria para que possa ser usado para fazer a contagem total de registros (sem paginacao) */
                criteria.setFirstResult(0);
                criteria.setMaxResults(2);
                criteria.setProjection(Projections.rowCount());

                ResultadoPesquisa<E> resultadoPesquisa = new ResultadoPesquisa<E>();
                resultadoPesquisa.setTotalRegistros((Integer) criteria.list().get(0));

                /* cuida da paginação */
                if(Util.preenchido(totalRegistrosPorPagina)) {
                    registrosPorPagina = totalRegistrosPorPagina;
                }
                
                Criteria criteria2 = montaCriterioDaBusca(sessaoAberta, filtro, ordenacoes, matchMode, restricoes);

                criteria2.setMaxResults(registrosPorPagina);

                /* calcula a última página */
                int ultimaPagina = new Double(Math.ceil(resultadoPesquisa.getTotalRegistros().doubleValue() / registrosPorPagina))
                        .intValue();

                /* cuida do inicio do resultado */
                if(Util.preenchido(pagina)) {
                    Integer paginaAtual = pagina;
                    if(pagina > ultimaPagina) {
                        paginaAtual = ultimaPagina;
                    }

                    criteria2.setFirstResult((paginaAtual - 1) * registrosPorPagina);
                }

                /* busca no banco os registros que obedecem à essa busca */
                resultado = criteria2.list();

                resultadoPesquisa.setResultado(resultado);

                return resultadoPesquisa;
            }

        });

    }

    private  Criteria montaCriterioDaBusca(Session sessaoAberta, final Entidade filtro, final Ordenacoes ordenacoes,
            final MatchMode matchMode) {

        return montaCriterioDaBusca(sessaoAberta, filtro, ordenacoes, matchMode, null);
    }

    private  Criteria montaCriterioDaBusca(Session sessaoAberta, final Entidade filtro) {

        return montaCriterioDaBusca(sessaoAberta, filtro, null, MatchMode.EXACT);
    }

    /**
     * metodo que, baseado no filtro passado, monta um "criteria" (do hibernate). esse criteria será usado tanto na
     * busca, quanto na contagem de registros.
     * 
     * @return
     */
    private  Criteria montaCriterioDaBusca(Session sessaoAberta, final Entidade filtro, final Ordenacoes ordenacoes,
            final MatchMode matchMode, Restricoes restricoes) {

        ArrayList<String> aliasCriados = new ArrayList<String>();
        Criteria createCriteria = sessaoAberta.createCriteria(filtro.getClass());

        /* Evita ciclo em relacao à entidade "raiz" (onde a busca comeca) */
        aliasCriados.add(Finder.geraAlias(filtro));

        Criteria criteria = Finder.criaFiltro(null, filtro, createCriteria, aliasCriados, matchMode);

        if(Util.preenchido(ordenacoes) && !ordenacoes.vazio()) {
            for(Ordenacao o : ordenacoes.getOrdenacoes()) {
                String attr = o.getNomeAtributo();
                String alias = Finder.geraAlias(o.getEntidade());
                criteria.addOrder(o.gerarOrder(Finder.geraAssociacao(alias, attr)));
            }
        }

        if(Util.preenchido(restricoes)) {
            for(Restricao restricao : restricoes.getRestricoes()) {
                criteria.add(restricao.gerarRestricao());
            }
        }

        return criteria;
    }

    @SuppressWarnings("unchecked")
    private  <E extends Entidade> List<E> listar(final E filtro, final Ordenacoes ordenacoes, final MatchMode matchMode, final Restricoes restricoes) {

        return (List<E>) TemplateTransacao.executar(new TransacaoSomenteLeitura() {

            @Override
            public Object executarTransacao(Session sessaoAberta) {
                Criteria criteria = montaCriterioDaBusca(sessaoAberta, filtro, ordenacoes, matchMode, restricoes);
                return criteria.list();
            }
        });

    }

    /**
     * Esse metodo espera sempre um resultado unico, geralmente será usado pelo sistema para buscas internas.
     */
    public  <E extends Entidade> E selecionar(E filtro) throws RuntimeException {

        List<E> resultado = listar(filtro, (Ordenacoes) null, MatchMode.EXACT, null);

        if(resultado.isEmpty()) {
            return null;
        } else if(resultado.size() > 1) {
            throw new RuntimeException("O resultado não é único para o objeto da classe " + filtro.getClass().getSimpleName());
        }

        return resultado.get(0);

    }

    public  <E extends Entidade> List<E> listar(final Class<E> classe) {
        
    	return listar(classe, (Integer)null);
    }
    
    
    
    
    
    /**
     * @param classe
     * @param id
     * @return
     * metodo recebe classe uma classe e um id para retornar uma classe abstrata instanciada.
     */
    @SuppressWarnings("unchecked")
	public  <E extends Entidade> List<E> listar(final Class<E> classe, final Integer id) {
    	
    	return (List<E>) TemplateTransacao.executar(new Transacao() {

            @Override
            public Object executarTransacao(Session sessaoAberta) {
                
                Criteria criteria = sessaoAberta.createCriteria(classe);
                
                if(Util.preenchido(id)){
                	criteria.add(Restrictions.eq("id", id));                	
                }
                
                
                return criteria.list();
                
            }
        });
    }
    
    public  <E extends Entidade> List<E> listar(E filtro) {
        return listar(filtro, (Ordenacoes) null, MatchMode.ANYWHERE, null);
    }
    
    public  <E extends Entidade> List<E> listar(E filtro, Ordenacoes ordenacoes) {
        return listar(filtro, ordenacoes, MatchMode.ANYWHERE, null);
    }

    public  <E extends Entidade> List<E> listar(E filtro, Ordenacoes ordenacoes, Restricoes restricoes) {
    	return listar(filtro, ordenacoes, MatchMode.ANYWHERE, restricoes);
    }

    public  <E extends Entidade> ResultadoPesquisa<E> pesquisar(E filtro, Integer pagina) {
        return pesquisar(filtro, pagina, VALOR_PADRAO_TOTAL_REGISTROS_POR_PAGINA, (Ordenacoes) null);
    }

    public  <E extends Entidade> ResultadoPesquisa<E> pesquisar(E filtro, Integer pagina, Ordenacoes ordenacoes) {
        return pesquisar(filtro, pagina, VALOR_PADRAO_TOTAL_REGISTROS_POR_PAGINA, ordenacoes);
    }

    public  <E extends Entidade> ResultadoPesquisa<E> pesquisar(E filtro, Integer pagina, Ordenacoes ordenacoes, Restricoes restricoes) {
        return pesquisar(filtro, pagina, null, ordenacoes, MatchMode.ANYWHERE, restricoes);
    }

    public  <E extends Entidade> ResultadoPesquisa<E> pesquisar(E filtro, Integer pagina, Integer totalRegistrosPorPagina,
            Ordenacoes ordenacoes, MatchMode matchMode) {
        return pesquisar(filtro, pagina, totalRegistrosPorPagina, ordenacoes, matchMode, null);
    }

    public  <E extends Entidade> List<E> listar(E filtro, Boolean likeHabilitado) {

        if(!likeHabilitado) {
            return listar(filtro, null, MatchMode.EXACT, null);
        }

        return listar(filtro);
    }

    public  Integer contarElementos(final Entidade filtro) {

        return (Integer) TemplateTransacao.executar(new TransacaoSomenteLeitura() {

            @Override
            public Object executarTransacao(Session sessaoAberta) {
                Criteria criteria = montaCriterioDaBusca(sessaoAberta, filtro);

                criteria.setProjection(Projections.rowCount());
                return (Integer) criteria.list().get(0);

            }
        });

    }

	public  <E extends Entidade> E selecionar(final Class<E> class1, final int id) { 
		List<E> lista = listar(class1, id);
		if(Util.preenchido(lista)){
        	return (E) lista.get(0);
        }else{
        	return null;
        }
        
   	}
}
