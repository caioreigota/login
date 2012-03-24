package br.com.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;


public class Util {
	
	public static final Locale LOCALE_PT_BR = new Locale("pt", "BR");
	
    /**
     * Checa se o objeto � vazio. Isso depende do tipo do objeto:
     * <ul>
     *    <li>Qualquer um: caso seja null</li> 
     *    <li>String: Caso seja vazia ou possua apenas caracteres "space" (" ")</li> 
     *    <li>Collection: Caso seja vazia (" ")</li> 
     * </ul>
     * 
     * @param objeto Objeto que será verificado
     * @return Um boolean informando se o objeto � vazio ou não
     */
    public static boolean vazio(Object objeto) {

        if(objeto == null) {
            return true;
        }

        if(objeto instanceof String) {
            if(((String) objeto).trim().equals("")) {
                return true;
            } else {
                return false;
            }
        }

        if(objeto instanceof Collection) {
            if(((Collection<?>) objeto).isEmpty()) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }
    
    public static boolean preenchido(Object objeto) {
        return !vazio(objeto);
    }
    
    public static boolean emailCorreto(String email) {
        return UtilString.verificarFormato(email, ".+@.+\\.[a-z]+");
    }
    
    /**
     * Faz uma cópia profunda de um objeto, ou seja, inclui os tipos não-primitivos
     * 
     * @param <O> uma classe qualquer que implemente Serializable
     * @param objetoOriginal objeto que será copiado
     * @return Uma cópia do objeto original
     * @throws Exception
     */
    public static <O extends Serializable> O deepCopy(O objetoOriginal) {

        ObjectOutputStream output = null;
        ObjectInputStream input = null;
        byte[] byteArray = null;
        ByteArrayInputStream byteInput = null;
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();

        try {
            output = new ObjectOutputStream(byteOutput);

            output.writeObject(objetoOriginal);
            output.flush();

            byteArray = byteOutput.toByteArray();

            byteInput = new ByteArrayInputStream(byteArray);

            input = new ObjectInputStream(byteInput);

            return (O) input.readObject();
        } catch (Exception e) {
            
            e.printStackTrace();
            
            throw new RuntimeException("Houve algum problema na hora de copiar o objeto." +
                    " Talvez ele não seja serializável.");
            
        } finally {
            
            try{
                if(output != null) {
                    output.close();
                }
                
                if(input != null) {
                    input.close();
                }
                
            } catch (Exception e){
                throw new RuntimeException("Houve algum problema na hora de copiar o objeto." +
                        " Talvez ele não seja serializável.");
            }
        }
    }
}
