package br.com.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class CampoTexto extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

	private String label;
	private String name;
	private String id;
	/**
	 * @param args
	 */
		
		public int doStartTag() throws JspException { 
	        try { 
	        	JspWriter PAGINA = pageContext.getOut();
	        	PAGINA.print("<s:textfield id="+id+"  label=\""+label+"\" name=\""+name+"\"/>"); 
	        	PAGINA.print("<br>");
	        	PAGINA.print("<s:fielderror fieldName=\""+name+"\"/>");
	        } catch (IOException e) { 
	            throw new JspException(e.getMessage()); 
	        }

	        return SKIP_BODY; 
	     }
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}




}
