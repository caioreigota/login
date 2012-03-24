package br.com.hibernateUtil;

public abstract class TransacaoSomenteLeitura extends ATransacao{

	@Override
	public Boolean precisaDeCommit() {
		return Boolean.valueOf(false);
	}

}
