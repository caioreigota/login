package br.com.util;

import java.lang.reflect.Method;

public class UtilReflection {
	
	public static Method[] retornaMetodos(Object objeto){
		Class classe = objeto.getClass();
		return classe.getDeclaredMethods();
	}
	
	public static Object invocarMetodo(String nomeMetodo,Object objeto,Class parameterTypes[], Object parametros[]){
		
		try {
			Method metodo = objeto.getClass().getMethod(nomeMetodo, parameterTypes);
			return metodo.invoke(objeto, parametros);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static Object invocarMetodo(String nomeMetodo,Object objeto){
		return invocarMetodo(nomeMetodo, objeto, new Class[0], new Object[0]);
	}
	
	

}
