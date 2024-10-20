package br.com.fiap.locadora.tests.locadora;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.LocadoraDao;
import br.com.fiap.locadora.model.Locadora;

public class PesquisaIdDaoTest {
    public static void main(String[] args) {

        try {

            LocadoraDao dao = new LocadoraDao(ConnectionFactory.getConnection());

            Locadora locadora = dao.pesquisaId(4);
            System.out.println(locadora);

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
