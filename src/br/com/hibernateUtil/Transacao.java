package br.com.hibernateUtil;


public abstract class Transacao extends ATransacao {

	@Override
	public Boolean precisaDeCommit() {
		return Boolean.valueOf(true);
	}
	

}
