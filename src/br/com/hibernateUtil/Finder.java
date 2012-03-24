package br.com.hibernateUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example.PropertySelector;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;

import br.com.modelo.Entidade;
import br.com.util.Util;
import br.com.util.UtilReflect;


public class Finder {

    private static Configuration hbConfiguration;
    private static final PropertySelector seletor = new SeletorPropriedades();

    public Finder() {
        super();
    }

    /**
     * Retorna o nome dos atributos mapeados, MENOS o nome da chave primaria.
     * 
     * @param entidade
     * @return
     */
    @SuppressWarnings("unchecked")
	public static ArrayList<String> nomesAtributosMapeados(Entidade entidade) {

        ArrayList<String> atributos = new ArrayList<String>();
        PersistentClass classMapping = getHibernateConfiguration().getClassMapping(entidade.getClass().getCanonicalName());

        Iterator<Property> propertyIterator = classMapping.getPropertyIterator();

        if(classMapping.isInherited()) {
            Iterator<Property> superClassPropertyIterator = getHibernateConfiguration().getClassMapping(
                    entidade.getClass().getSuperclass().getCanonicalName()).getPropertyIterator();

            while(superClassPropertyIterator.hasNext()) {
                atributos.add(superClassPropertyIterator.next().getNodeName());
            }
        }

        while(propertyIterator.hasNext()) {
            atributos.add(propertyIterator.next().getNodeName());
        }

        return atributos;
    }

    private static Configuration getHibernateConfiguration() {

        if(hbConfiguration == null) {
            hbConfiguration = HibernateUtil.getAnnotationConfiguration();
        }

        return hbConfiguration;
    }

    /**
     * Um atributo � considerado direto se ele NAO � uma associacao.
     * 
     * @param entidade
     * @param nomeAtributo
     * @return
     */
    public static Boolean isAtributoDireto(Entidade entidade, String nomeAtributo) {

        Property atributo = getHibernateConfiguration().getClassMapping(entidade.getClass().getCanonicalName()).getProperty(nomeAtributo);

        return (atributo != null) && (!atributo.getType().isAssociationType());
    }

    public static Criteria criaFiltro(String alias, Entidade entidade, Criteria criteria, List<String> aliasCriados, MatchMode matchMode) {
        List<String> atributosIndiretos = nomesAtributosIndiretos(entidade);
        Integer valorPk = entidade.getId();

        if(chavePrimariaEstaPreenchida(valorPk)) {
        	/* Caso o id esteja preenchido, *todos* os outros atributos devem ser ignorados, por isso ja retornamos
        	 * o criterio de busca. */
            String nomeCampo = "id";
            criteria.add(Restrictions.eq(geraAssociacao(alias, nomeCampo), valorPk));
            return criteria;
        } else {
            adicionaFiltroOutrosAtributos(alias, entidade, criteria, matchMode);
        }

        for(String nomeAtributo : atributosIndiretos) {
            Object valorAtributo = UtilReflect.retornarValorAtributo(entidade, nomeAtributo);

            if(isLista(valorAtributo) && !isEmpty(valorAtributo)) {
                valorAtributo = ((List<?>) valorAtributo).get(0);
            } else if(isLista(valorAtributo) && isEmpty(valorAtributo)) {
                continue;
            }

            if(Util.preenchido(valorAtributo) && aliasAindaNaoCriado(aliasCriados, valorAtributo)) {
                String proximoAlias = geraAlias(valorAtributo);
                criteria.createAlias(geraAssociacao(alias, nomeAtributo), proximoAlias);
                /* Para nao criar esse alias novamente */
                aliasCriados.add(proximoAlias);
                Finder.criaFiltro(proximoAlias, (Entidade) valorAtributo, criteria, aliasCriados, matchMode);
            }
        }

        return criteria;
    }

    private static boolean aliasAindaNaoCriado(List<String> aliasCriados, Object valorAtributo) {
        return !aliasCriados.contains(geraAlias(valorAtributo));
    }

    private static boolean isEmpty(Object valorAtributo) {
        return ((List<?>) valorAtributo).isEmpty();
    }

    private static boolean isLista(Object valorAtributo) {
        return valorAtributo instanceof List;
    }

    /**
     * Gera uma string de associacao entre um alias e seu atributo
     * 
     * @param alias
     * @param nomeAtributo
     * @return
     */

    public static String geraAssociacao(String alias, String nomeAtributo) {

        if(Util.preenchido(alias)) {
            return alias + "." + nomeAtributo;
        } else {
            return nomeAtributo;
        }

    }

    /**
     * Gera um Alias unico para um atributo qualquer.
     * 
     * @param valorAtributo
     * @return
     */
    public static String geraAlias(Object valorAtributo) {
        String proximoAlias = "";
        if(Util.preenchido(valorAtributo)) {
            proximoAlias = valorAtributo.getClass().getCanonicalName();
        }
        return proximoAlias.replace(".", "_");
    }
    
    public static String geraAlias(Class<? extends Entidade> classe){
        String proximoAlias = "";
        if(Util.preenchido(classe)) {
            proximoAlias = classe.getCanonicalName();
        }
        return proximoAlias.replace(".", "_");
    }
    

    private static boolean chavePrimariaEstaPreenchida(Integer valorPk) {
        return Util.preenchido(valorPk);
    }

    private static void adicionaFiltroOutrosAtributos(String alias, Entidade entidade, Criteria criteria, MatchMode matchMode) {
        List<String> attributos = nomesAtributosDiretos(entidade);

        for(String nomeAtributo : attributos) {
            Object valorAtributo = UtilReflect.retornarValorAtributo(entidade, nomeAtributo);

            if(isAtributoDireto(entidade, nomeAtributo) && Util.preenchido(valorAtributo)) {
                if(isString(valorAtributo)) {
                    criteria.add(Restrictions.like(geraAssociacao(alias, nomeAtributo), (String) valorAtributo, matchMode));
                } else if(seletor.include(valorAtributo, nomeAtributo, null)) {
                    criteria.add(Restrictions.eq(geraAssociacao(alias, nomeAtributo), valorAtributo));
                }
            }
        }
    }

    private static boolean isString(Object valorAtributo) {
        return (valorAtributo instanceof String);
    }

    /**
     * Retorna uma lista com o nome de todos os atributos "indiretos", ou seja, todos os atributos que na verdade
     * refernciam uma Entidade inclusive os atributos tipo Lista (Bag, Set, List, etc)
     * 
     * @param entidade
     * @return
     */
    public static List<String> nomesAtributosIndiretos(Entidade entidade) {

        List<String> atributos = nomesAtributosMapeados(entidade);
        List<String> atributosIndiretos = new ArrayList<String>();

        for(String nomeAtributo : atributos) {
            if(!isAtributoDireto(entidade, nomeAtributo)) {
                atributosIndiretos.add(nomeAtributo);
            }
        }

        return atributosIndiretos;
    }

    public static List<String> nomesAtributosDiretos(Entidade entidade) {

        List<String> atributosDiretos = new ArrayList<String>();

        atributosDiretos = nomesAtributosMapeados(entidade);
        atributosDiretos.removeAll(nomesAtributosIndiretos(entidade));

        return atributosDiretos;
    }
}
