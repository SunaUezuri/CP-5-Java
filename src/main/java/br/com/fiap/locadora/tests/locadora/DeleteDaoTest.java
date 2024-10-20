package br.com.fiap.locadora.tests.locadora;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.LocadoraDao;

public class DeleteDaoTest {
    public static void main(String[] args) {

        try {

            LocadoraDao dao = new LocadoraDao(ConnectionFactory.getConnection());

            dao.apagar(3);
            System.out.println("Locadora deletada com sucesso!");

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
