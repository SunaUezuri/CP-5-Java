package br.com.fiap.locadora.tests.cliente;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.ClienteDao;
import br.com.fiap.locadora.exceptions.IdNaoEncontradoException;

public class DeleteDaoTest {
    public static void main(String[] args) {

        try {

            ClienteDao dao = new ClienteDao(ConnectionFactory.getConnection());

            dao.apagar(1);
            System.out.println("Cliente removido com sucesso!");

            try {
                dao.pesquisaId(1);
            } catch (IdNaoEncontradoException e){
                System.out.println("Cliente não encontrado, assim como deveria ser");
            }

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
