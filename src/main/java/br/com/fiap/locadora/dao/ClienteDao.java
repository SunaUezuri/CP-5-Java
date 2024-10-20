package br.com.fiap.locadora.dao;

import br.com.fiap.locadora.exceptions.IdNaoEncontradoException;
import br.com.fiap.locadora.exceptions.NomeNaoEncontradoException;
import br.com.fiap.locadora.model.Cliente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

    //Comandos SQL que serão utilizados
    private static final String INSERT_SQL = "insert into t_cliente (id_cliente, nr_cpf, nm_cliente, dt_nascimento, ds_endereco, ds_email) values (sq_t_cliente.nextval, ?, ?, ?, ?, ?)";
    private static final String SELECT_SQL = "select * from t_cliente order by id_cliente";
    private static final String SELECT_ID_SQL = "select * from t_cliente where id_cliente = ?";
    private static final String SELECT_NAME_SQL = "select * from t_cliente where upper(nm_cliente) like upper(?) order by id_cliente";
    private static final String UPDATE_SQL = "update t_cliente set nr_cpf = ?, nm_cliente = ?, dt_nascimento = ?, ds_endereco = ?, ds_email = ? where id_cliente = ?";
    private static final String DELETE_SQL = "delete from t_cliente where id_cliente = ?";

    //Criando um construtor para se ter a conexão
    private Connection conexao;

    public ClienteDao(Connection conexao){
        this.conexao = conexao;
    }

    //Método para cadastrar um cliente
    public void cadastrar(Cliente cliente) throws SQLException{

        //Criando o Statement configurável
        PreparedStatement stmt = conexao.prepareStatement(INSERT_SQL, new String[] {"id_cliente"});

        //Setando os valores na querry
        preencherStatementComCliente(stmt, cliente);
        //Executar o comando SQL
        stmt.executeUpdate();

        //Recuperando o id gerado pela sequence
        ResultSet resultSet = stmt.getGeneratedKeys();
        resultSet.next();
        cliente.setId(resultSet.getInt(1));
    }

    //Método para buscar todos os clientes registrados no banco
    public List<Cliente> listar() throws SQLException{

        //Criando o stament
        Statement stm = conexao.createStatement();

        //Executar o comando SQL
        ResultSet resultSet = stm.executeQuery(SELECT_SQL);

        //Criando a lista de clientes
        List<Cliente> list = new ArrayList<>();

        //Percorrendo os registros
        while (resultSet.next()){

            //Adicionando os registros encontrados na lista
            list.add(parseCliente(resultSet));
        }

        return list;
    }

    //Método para pesquisar o cliente por ID
    public Cliente pesquisaId(int id) throws SQLException, IdNaoEncontradoException{

        //Criado o Prepared statement
        PreparedStatement stmt = conexao.prepareStatement(SELECT_ID_SQL);

        //Setando o id
        stmt.setInt(1, id);

        //Executar o comando SQL
        ResultSet resultSet = stmt.executeQuery();

        if (!resultSet.next()){
            throw new IdNaoEncontradoException("Cliente não encontrado");
        }

        return parseCliente(resultSet);
    }

    //Método para pesquisar o cliente pelo cpf
    public List<Cliente> pesquisaNome(String nome) throws SQLException, NomeNaoEncontradoException{

        //Criando o PreparedStament
        PreparedStatement stmt = conexao.prepareStatement(SELECT_NAME_SQL);

        //Setando o valor na Querry
        stmt.setString(1, "%" + nome + "%");

        ResultSet resultSet = stmt.executeQuery();

        //Criando a lista de funcionários
        List<Cliente> lista = new ArrayList<>();

        while (resultSet.next()){
            lista.add(parseCliente(resultSet));
        }

        if (lista.isEmpty()){
            throw new NomeNaoEncontradoException("Não encontramos um cliente com este nome");
        }

        return lista;
    }

    //Método para atualizar o cliente
    public void atualizar(Cliente cliente) throws SQLException, IdNaoEncontradoException{

        //Criando o PreparedStatement
        PreparedStatement stmt = conexao.prepareStatement(UPDATE_SQL);

        stmt.setInt(6, cliente.getId());

        //Setando os valores
        preencherStatementComCliente(stmt, cliente);

        //Executando o comando caso encontre o ID
        int linhas = stmt.executeUpdate();

        if (linhas == 0){
            throw new IdNaoEncontradoException("Cliente não encontrado.");
        }
    }

    //Método para deletar um cliente pelo ID
    public void apagar(int id) throws SQLException, IdNaoEncontradoException{

        //Instanciando o PreparedStatement
        PreparedStatement stm = conexao.prepareStatement(DELETE_SQL);

        //Setando o valor do ID
        stm.setInt(1, id);

        //Executando o comando SQL caso exista o ID
        int linhas = stm.executeUpdate();

        if (linhas == 0){
            throw new IdNaoEncontradoException("Cliente não encontrado");
        }
    }

    //Método para preencher os Statements
    private void preencherStatementComCliente(PreparedStatement stmt, Cliente cliente) throws SQLException{
        stmt.setString(1, cliente.getCpf());
        stmt.setString(2, cliente.getNome());
        stmt.setDate(3, Date.valueOf(cliente.getDtNasc()));
        stmt.setString(4, cliente.getEndereco());
        stmt.setString(5, cliente.getEmail());
    }

    private Cliente parseCliente(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id_cliente");
        String cpf = resultSet.getString("nr_cpf");
        String nome = resultSet.getString("nm_cliente");
        LocalDate dtNasc = resultSet.getDate("dt_nascimento").toLocalDate();
        String endereco = resultSet.getString("ds_endereco");
        String email = resultSet.getString("ds_email");

        //Criando o objeto cliente
        Cliente cliente = new Cliente(id, cpf, nome, dtNasc, endereco, email);

        return cliente;
    }
}