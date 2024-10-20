package br.com.fiap.locadora.tests.cliente;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.ClienteDao;
import br.com.fiap.locadora.model.Cliente;

import java.util.List;

public class ListarNomeDaoTest {
    public static void main(String[] args) {
        try {

            ClienteDao dao = new ClienteDao(ConnectionFactory.getConnection());
            List<Cliente> lista = dao.pesquisaNome("A");

            System.out.println("Clientes encontrados: " + lista.size());
            for (Cliente c : lista){
                System.out.println(c);
            }

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
