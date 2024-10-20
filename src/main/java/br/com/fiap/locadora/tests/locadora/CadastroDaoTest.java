package br.com.fiap.locadora.tests.locadora;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.LocadoraDao;
import br.com.fiap.locadora.model.Locadora;

public class CadastroDaoTest {
    public static void main(String[] args) {

        try {

            LocadoraDao dao = new LocadoraDao(ConnectionFactory.getConnection());

            Locadora locadora = new Locadora(0, "WFEQW", "SCA", "1116");

            dao.cadastrar(locadora);
            System.out.println("Locadora cadastrada com sucesso!");

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
