package br.com.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilString {

    static final String[] SIMBOLOS = { ",", "." };

    public static final String TRUE = "true";
    /** String "false" */
    public static final String FALSE = "false";
    /** String "false" */
    public static final String VALOR_NULO = "---";

    public static String doubleQuote(String string) {
        return "\"" + string + "\"";
    }

    public static String singleQuote(String conteudo) {
        return "'" + conteudo + "'";
    }

    public static String retornarPadrao(final String valor) {
        return retornarPadrao(valor, null);
    }

    /**
     * Imprime o valor caso ele não seja vazio. Caso contrário imprime um valor padrão.
     * 
     * @param valor Valor a ser retornado
     * @param padrao Valor retornado caso "valor" seja vazio.
     */
    public static String retornarPadrao(final String valor, final String padrao) {

        if(Util.preenchido(valor)) {
            return valor;
        } else {
            if(padrao == null) {
                return VALOR_NULO;
            } else {
                return padrao;
            }
        }
    }

    /**
     * Verifica se uma string segue um formato
     * 
     * @param string String a ser verificada
     * @param formato Formato que deve ser seguido
     * @return "True" caso a string siga o formato e "false" caso contrário.
     */
    public static boolean verificarFormato(String string, String formato) {
        Pattern padrao = Pattern.compile(formato);

        Matcher pesquisa = padrao.matcher(string);

        if(pesquisa.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Verifica se "caracter" aparece uma única vez em "string"
     * 
     * @param string
     * @param caracter
     * @return
     */
    public static boolean apareceNoMaximoUmaVez(String string, String caracter) {

        boolean temUm = false;

        for(int i = 0; i < string.length(); i++) {

            // se o caracter for o procurado
            if(String.valueOf(string.charAt(i)).equals(caracter)) {
                if(!temUm) {
                    // nao tinha nada, agora tem um
                    temUm = true;
                } else {
                    // tem mais de um
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Retira todos os 0's (zeros) à direita da String
     * 
     * @param string String que terá os 0's à direita retirados
     * @return A string passada como parâmetro sem zeros à direita
     */
    public static String retiraZeroDireita(String string) {

        for(;;) {
            // caso o último caracter seja "0"
            if(Util.preenchido(string) && String.valueOf(string.charAt(string.length() - 1)).equals("0")) {
                // retira o último caracter, ou seja, o "0"
                string = string.substring(0, string.length() - 1);
            } else {
                break;
            }
        }

        // caso tenha sobrado um "." ou "," no final da string o caracter será retirado
        if(string.contains(SIMBOLOS[0])) {
            if(string.indexOf(SIMBOLOS[0]) == string.length() - 1) {
                string = string.substring(0, string.length() - 1);
            }
        } else if(string.contains(SIMBOLOS[1])) {
            if(string.indexOf(SIMBOLOS[1]) == string.length() - 1) {
                string = string.substring(0, string.length() - 1);
            }
        }

        return string;
    }

    public static String tratarURLEncoding(final String string) {

        String resultado;

        if(Util.preenchido(string)) {
            resultado = string.replace(" ", "%20");
            resultado = resultado.replace("#", "%23");
        } else {
            resultado = "";
        }

        return resultado;
    }
    
    /**
     * Retira todos os 0's (zeros) à esquerda da String
     * 
     * @param string String que terá os 0's à esquerda retirados
     * @return A string passada como parâmetro sem zeros à esquerda
     */
    public static String retiraZeroEsquerda(String string) {

        for(;;) {
            if(Util.preenchido(string) && String.valueOf(string.charAt(0)).equals("0")) {
                string = string.substring(1);
            } else {
                break;
            }
        }

        return string;
    }

    public static String trimToNull(String campo) {

        if(Util.preenchido(campo)) {
            return campo.trim();
        } else {
            return null;
        }
    }
    
}
