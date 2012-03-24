	package br.com.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.com.modelo.Entidade;


public class UtilReflect {

    /**
     * Retorna todos os atributos de uma classe, mesmo os herdados
     * 
     * @param <T> Uma classe que implementa Entidade
     * @param classeEntidade A classe de onde vamos retornar os atributos
     * @return todos os atributos da classe em um Field[]
     */
    public static List<Field> retornaTodosAtributos(Class<? extends Entidade> classeEntidade) {

        List<Field> todosAtributos = new ArrayList<Field>();

        for(Class<?> classe = classeEntidade; classe != null; classe = classe.getSuperclass()) {
            for(Field atributo : classe.getDeclaredFields()) {
                todosAtributos.add(atributo);
            }
        }

        return todosAtributos;
    }

    /**
     * Retorna o resultado do getter de um atributo espec�fico
     * 
     * @param objeto Classe de onde o getter ser� chamado
     * @param nomeAtributo Nome do atributo para o qual o getter ser� chamado.
     * @return Valor do atributo
     * @throws RuntimeException Caso n�o seja poss�vel retornar o atributo
     */
    public static Object retornarValorAtributo(final Object objeto, final String nomeAtributo) throws RuntimeException {

        final String primeiraLetra = nomeAtributo.substring(0, 1);
        final String resto = nomeAtributo.substring(1);
        final String nomeMetodo = primeiraLetra.toUpperCase() + resto;

        Object valorAtributo = null;

        try {
            valorAtributo = invocarMetodo(objeto, "get" + nomeMetodo);

        } catch(Exception exceptionMetodoGet) {
            try {
                valorAtributo = invocarMetodo(objeto, "is" + nomeMetodo);
            } catch(Exception exceptionMetodoIs) {
                throw new RuntimeException("A classe \"" + objeto.getClass() + "\" n�o possui o getter do atributo \"" + nomeAtributo
                        + "\". Não � poss�vel retornar o atributo.");
            }
        }

        return valorAtributo;
    }

    private static Object invocarMetodo(final Object objeto, String nomeMetodo) {

        Object valorAtributo = null;

        try {
            Method metodo = objeto.getClass().getMethod(nomeMetodo, (Class[]) null);
            valorAtributo = metodo.invoke(objeto, (Object[]) null);
        } catch(Exception e) {
            throw new RuntimeException("A classe \"" + objeto.getClass() + "\" n�o possui o metodo " + nomeMetodo
                    + ". Não � poss�vel retornar o atributo.");
        }
        return valorAtributo;
    }

    /**
     * Retorna o tipo de retorno do getter de um atributo espec�fico
     * 
     * @param objeto Classe de onde o getter ser� procurado
     * @param nomeAtributo Nome do atributo para o qual o getter ser� chamado.
     * @return Tipo de retorno do getter
     * @throws RuntimeException Caso n�o seja poss�vel encontrar o getter
     */
    public static Class<?> retornarTipoDeRetornoDoGetter(final Object objeto, final String nomeAtributo) throws RuntimeException {

        final String primeiraLetra = nomeAtributo.substring(0, 1);
        final String resto = nomeAtributo.substring(1);
        final String nomeMetodo = "get" + primeiraLetra.toUpperCase() + resto;
        Method metodo = null;

        try {
            metodo = objeto.getClass().getMethod(nomeMetodo, (Class[]) null);

        } catch(NoSuchMethodException e) {
            throw new RuntimeException("A classe \"" + objeto.getClass() + "\" n�o possui o getter do atributo \"" + nomeAtributo
                    + "\". Não � poss�vel retornar o atributo.");
        } catch(Exception e) {
            throw new RuntimeException("Não foi poss�vel retornar o atributo");
        }

        return metodo.getReturnType();
    }

    /**
     * Preenche um atributo de um objeto com um valor
     * 
     * @param objeto Objeto que terá seu atributo preenchido
     * @param nomeAtributo Atributo que será preenchido
     * @param valor Valor que preencherá o atributo
     * @throws RuntimeException Caso não seja possível preencher o atributo
     */
    public static void setarValorAtributo(Object objeto, String nomeAtributo, Object valor, Class<?> tipo) throws RuntimeException {

        String primeiraLetra = nomeAtributo.substring(0, 1);
        String resto = nomeAtributo.substring(1);
        String nomeMetodo = "set" + primeiraLetra.toUpperCase() + resto;
        Method metodo = null;

        try {
            metodo = objeto.getClass().getMethod(nomeMetodo, tipo);
            metodo.invoke(objeto, valor);
        } catch(NoSuchMethodException e) {
            throw new RuntimeException("A classe " + UtilString.doubleQuote(objeto.getClass().getCanonicalName()) + " não possui o setter "
                    + UtilString.doubleQuote(nomeMetodo) + ", não é possível preencher o atributo");
        } catch(Exception e) {
            throw new RuntimeException("Não foi possível preencher o atributo");
        }
    }

    /**
     * Verifica se uma classe herda a outra
     * 
     * @param classeFilha Classe que verificaremos se herda ou não "classePai"
     * @param classePai Classe que verificaremos se � pai ou não de "classeFilha"
     * @return "True" caso "classeFilha" herde "classePai" e "false" caso contr�rio
     */
    public static boolean herda(Class<?> classeFilha, Class<?> classePai) {

        // se não tiver um pai (superClass == null) não h� o que ver
        if(classeFilha.getSuperclass() != null) {
            for(Class<?> classe = classeFilha; classe != null; classe = classe.getSuperclass()) {
                if(classePai.equals(classe.getSuperclass())) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean implementa(Class<?> classe, Class<?> interfaceImplementada) {

        Class<?>[] interfaces = classe.getInterfaces();
        for(Class<?> c : interfaces) {
            if(c.equals(interfaceImplementada)) {
                return true;
            }
        }

        return false;

    }

    public static boolean possuiAnotacao(Method metodo, Class<? extends Annotation> class2) {

        return (metodo.getAnnotation(class2) != null);

    }

    public static Object instancia(String classe) {
        Object retorno = null;

        try {
            Class<? extends Object> clazz = null;
            clazz = Class.forName(classe);
            retorno = clazz.cast(clazz.newInstance());
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        } catch(InstantiationException e) {
            e.printStackTrace();
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public static boolean possuiAnotacao(Field campo, Class<? extends Annotation> class1) {

        return (campo.getAnnotation(class1) != null);

    }

    public static List<Field> retornaAtributosComAnotacao(Class<? extends Object> objeto, Class<? extends Annotation> anotacao) {

        List<Field> resultado = new ArrayList<Field>();

        for(Field campo : objeto.getDeclaredFields()) {
            if(UtilReflect.possuiAnotacao(campo, anotacao)) {
                resultado.add(campo);
            }
        }
        return resultado;
    }

    public static <O extends Object> O retornarNovaInstanciaConcreta(final O o) {
        try {
            return (O) o.getClass().newInstance();
        } catch(InstantiationException e) {
            throw new RuntimeException(e);
        } catch(IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

}
