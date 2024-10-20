package br.com.fiap.locadora.tests.cliente;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.ClienteDao;
import br.com.fiap.locadora.model.Cliente;

import java.sql.Connection;
import java.time.LocalDate;

public class CadastroDaoTest {
    public static void main(String[] args) {

        //Realizando o teste de cadastro
        try {

            Connection conn = ConnectionFactory.getConnection();

            //Instanciando o DAO
            ClienteDao dao = new ClienteDao(conn);

            Cliente cliente = new Cliente(0, "19878645895", "Marina Fernandes",
                    LocalDate.of(1985, 5, 30), "Estrada M'Boi Mirim 25", "mf@gmail.com");

            dao.cadastrar(cliente);
            System.out.println("Cliente cadastrado com sucesso!");

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
