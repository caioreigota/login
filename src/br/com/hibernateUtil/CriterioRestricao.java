package br.com.hibernateUtil;

public enum CriterioRestricao {
	
	/*
	 * Igual
	 */
	EQ,
	/*
	 * Diferente
	 */
	NE,
	/*
	 * Menor ou igual
	 */
	LE,
	/*
	 * Maior ou igual
	 */
	GE,
	/*
	 * Menor
	 */
	LT,
	/*
	 * Maior
	 */
	GT,
	/*
	 * Vazio
	 */
	IS_EMPTY,
	/*
	 * N�o-Vazio
	 */
	IS_NOT_EMPTY,
	/*
	 * Nulo
	 */
	IS_NULL,
	/*
	 * N�o-Nulo
	 */
	IS_NOT_NULL,
	/*
	 * Like
	 */
	LIKE;
}