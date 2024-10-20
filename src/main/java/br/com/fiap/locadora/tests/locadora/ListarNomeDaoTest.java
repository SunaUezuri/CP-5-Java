package br.com.fiap.locadora.tests.locadora;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.LocadoraDao;
import br.com.fiap.locadora.model.Locadora;

import java.util.List;

public class ListarNomeDaoTest {
    public static void main(String[] args) {

        try {

            LocadoraDao dao = new LocadoraDao(ConnectionFactory.getConnection());

            List<Locadora> lista = dao.pesquisarNome("3d");

            System.out.println("Registros encontrados: " + lista.size());
            for (Locadora l : lista){
                System.out.println(l);
            }

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
