package br.com.hibernateUtil;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.util.Util;


/**
 * Classe responsável pela interface com a classe Restrictions do Hibernate.
 */
public class Restricao {
	
	private String nomeAtributo;
	private Object valorAtributo;
	private CriterioRestricao criterio;
	
	/**
	 * Construtor
	 *  
	 * @param nomeAtributo Nome do atributo da classe que indicar� o crit�rio de restri��o.
	 * @param criterio Indica o tipo de crit�rios utilizado na restri��o.
	 */
	private Restricao(String nomeAtributo, CriterioRestricao criterio) {
	    
	    this.nomeAtributo = nomeAtributo;
	    this.criterio = criterio;
	}

	private Restricao(String nomeAtributo, CriterioRestricao criterio, Object valorAtributo) {
		
		this.nomeAtributo = nomeAtributo;
		this.valorAtributo = valorAtributo;
		this.criterio = criterio;
	}

	
	/**
	 * Retorna um objeto SimpleExpression (org.hibernate.criterion.SimpleExpression), que o Hibernate
	 * utiliza para restringir a busca.
	 */
	public Criterion gerarRestricao() {
		
		switch(criterio){
		case IS_EMPTY:
			return Restrictions.isEmpty(nomeAtributo);
		case IS_NOT_EMPTY:
			return Restrictions.isNotEmpty(nomeAtributo);
		case IS_NULL:
			return Restrictions.isNull(nomeAtributo);
		case IS_NOT_NULL:
			return Restrictions.isNotNull(nomeAtributo);
		case EQ:
			return Restrictions.eq(nomeAtributo, valorAtributo);
		case NE:
			return Restrictions.ne(nomeAtributo, valorAtributo);
		case GE:
			return Restrictions.ge(nomeAtributo, valorAtributo);
		case GT:
			return Restrictions.gt(nomeAtributo, valorAtributo);
		case LE:
			return Restrictions.le(nomeAtributo, valorAtributo);
		case LT:
			return Restrictions.lt(nomeAtributo, valorAtributo);
		case LIKE:
		    return Restrictions.like(nomeAtributo, "%" + valorAtributo + "%");
		default:
			throw new RuntimeException("O tipo de critério de restrição passado não é suportado. Cheque a classe CriterioRestrição.");
		}
	}
	
	
	/** 
	 * Define o atributo utilizado na restri��o.
	 * @param nomeAtributo Nome do atributo utilizado na restri��o.
	 */
	public void setNomeAtributo(String nomeAtributo) {
	
		this.nomeAtributo = nomeAtributo;
	}

	/**
	 * Retorna o nome do atributo utilizado na restri��o.
	 */
	public String getNomeAtributo() {
	
		return nomeAtributo;
	}
	
	/**
	 * Retorna o tipo de crit�rio usado na restri��o.
	 */
	public CriterioRestricao getCriterio() {
		return criterio;
	}
	
	/**
	 * Define o tipo de crit�rio utilizado na restri��o.
	 * @param criterio Enumerador utilizado para reconhecer o tipo de crit�rio da restri��o.
	 */
	public void setCriterio(CriterioRestricao criterio) {
		this.criterio = criterio;
	}
	
	@Override
    public boolean equals(Object object){
		
		Restricao restricao;
		
		if(Util.preenchido(object) && object instanceof Restricao){
			
			restricao = (Restricao) object;
			
			try{
				if(restricao.criterio.equals(this.criterio)
						&& restricao.nomeAtributo.equals(this.nomeAtributo)
						&& restricao.valorAtributo.equals(this.valorAtributo)){
					
					return true;
				}
			} catch (NullPointerException npe){
				
				npe.printStackTrace();
			}
		}
		
		return false;
	}

    
    public Object getValorAtributo() {
        return valorAtributo;
    }

    public void setValorAtributo(Object valorAtributo) {
        this.valorAtributo = valorAtributo;
    }

    public static Restricao gerar(String nomeAtributo, CriterioRestricao criterio) {
        return new Restricao(nomeAtributo, criterio);
    }

    public static Restricao gerar(String nomeAtributo, CriterioRestricao criterio, Object valorAtributo) {
    	return new Restricao(nomeAtributo, criterio, valorAtributo);
    }

}