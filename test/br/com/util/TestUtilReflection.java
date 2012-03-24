package br.com.util;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import org.junit.Test;

import br.com.controle.action.UsuarioAction;
import br.com.modelo.Usuario;

public class TestUtilReflection {
	
	
	@Test
	public void testRetornaMetodos(){
		
		Method[] metodos = UtilReflection.retornaMetodos(new Usuario());
		
		assertEquals("getId",metodos[0].getName());
		assertEquals("getLogin",metodos[1].getName());
		assertEquals("setLogin",metodos[2].getName());
		assertEquals("getNome",metodos[3].getName());
		assertEquals("setNome",metodos[4].getName());
		assertEquals("setId",metodos[5].getName());
			
	}
	

}
