package br.com.hibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class TemplateTransacao {

    private static SessionFactory sessionFactory;

    public static Object executar(ATransacao transacao) {

        Session sessaoAberta = obterSessaoAberta();

        Transaction transacaoHibernate = sessaoAberta.beginTransaction();

        Object resultadoTransacao = null;

        try {
            resultadoTransacao = transacao.executarTransacao(sessaoAberta);

            if(transacao.precisaDeCommit()) {
                transacaoHibernate.commit();
            }

        } catch(RuntimeException e) {
             transacaoHibernate.rollback();
            throw e;
        } finally {
            sessaoAberta.close();
        }

        return resultadoTransacao;

    }

    private static Session obterSessaoAberta() {
        return obterSessionFactory().openSession();
    }

    private static SessionFactory obterSessionFactory() {
        if(sessionFactory == null) {
            sessionFactory = HibernateUtil.getSession2();
        }
        return sessionFactory;
    }

    public static void setSessionFactory(SessionFactory sessionFactory) {
        TemplateTransacao.sessionFactory = sessionFactory;
    }

}
