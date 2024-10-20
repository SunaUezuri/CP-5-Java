package br.com.fiap.locadora.dao;

import br.com.fiap.locadora.exceptions.EnderecoNaoEncontradoException;
import br.com.fiap.locadora.exceptions.IdNaoEncontradoException;
import br.com.fiap.locadora.exceptions.NomeNaoEncontradoException;
import br.com.fiap.locadora.model.Locadora;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocadoraDao {

    //Comandos sql
    private static final String INSERT_SQL = "insert into t_locadora (id_locadora, nm_locadora, ds_endereco, nr_telefone) values (sq_t_locadora.nextval, ?, ?, ?)";
    private static final String SELECT_SQL = "select * from t_locadora order by id_locadora";
    private static final String SELECT_NAME_SQL = "select * from t_locadora where upper(nm_locadora) like upper(?)";
    private static final String SELECT_ENDERECO_SQL = "select * from t_locadora where upper(ds_endereco) like upper(?)";
    private static final String SELECT_ID_SQL = "select * from t_locadora where id_locadora = ?";
    private static final String UPDATE_SQL = "update t_locadora set nm_locadora = ?, ds_endereco = ?, nr_telefone = ? where id_locadora = ?";
    private static final String DELETE_SQL = "delete from t_locadora where id_locadora = ?";

    Connection conexao;

    public LocadoraDao(Connection conexao){
        this.conexao = conexao;
    }

    //Método para cadastrar a locadora

    public void cadastrar(Locadora locadora) throws SQLException{

        //Criando o PreparedStatement
        PreparedStatement stm = conexao.prepareStatement(INSERT_SQL, new String[] {"id_locadora"});

        //Setando os valores
        preencherStatementComLocadora(stm, locadora);

        //Executando o comando SQL
        stm.executeUpdate();

        //Recuperando o ID gerado pela sequence
        ResultSet resultSet = stm.getGeneratedKeys();
        resultSet.next();
        locadora.setId(resultSet.getInt(1));
    }

    //Método para pesquisar as locadoras registradas
    public List<Locadora> pesquisar() throws SQLException{
        //Criando o stament
        Statement stm = conexao.createStatement();

        //Executando o comando SQL
        ResultSet resultSet = stm.executeQuery(SELECT_SQL);

        //Criando a lista de locadoras
        List<Locadora> lista = new ArrayList<>();

        //Percorrer os registros encontrados
        while (resultSet.next()){

            //Adicionando as locadoras encontradas na lista
            lista.add(parseLocadora(resultSet));

        }

        //Retornando a lista com os valores encontrados
        return lista;
    }

    //Método para pesquisar locadoras com um certo nome ou letras
    public List<Locadora> pesquisarNome(String nome) throws SQLException, NomeNaoEncontradoException {

        //Executando o comando SQL
        //Este comando pega todos os itens registrados que tiverem algo semelhante
        // ao input anteriormente ou depois

        PreparedStatement stmt = conexao.prepareStatement(SELECT_NAME_SQL);

        //Setando o valor do comando
        stmt.setString(1, "%" + nome + "%");

        ResultSet resultSet = stmt.executeQuery();

        //Criando a lista de Locadoras
        List<Locadora> lista = new ArrayList<>();

        //Comando para percorrer o banco e salvar os registros encontrados na lista
        while (resultSet.next()){
            lista.add(parseLocadora(resultSet));
        }

        //Comando para ver se a lista está vazia
        if (lista.isEmpty()){
            throw new NomeNaoEncontradoException("Locadora não encontrada.");
        }


        return lista;
    }

    //Pesquisar as locadoras pelo endereço delas
    public List<Locadora> pesquisaEndereco(String endereco) throws SQLException, EnderecoNaoEncontradoException{

        //Criando o statement
        PreparedStatement stmt = conexao.prepareStatement(SELECT_ENDERECO_SQL);

        //Setando o valor
        stmt.setString(1, "%" + endereco + "%");

        ResultSet resultSet = stmt.executeQuery();

        //Criando a lista de Locadoras
        List<Locadora> lista = new ArrayList<>();

        //Comando para buscar os registros e adicionar na lista
        while (resultSet.next()){

            lista.add(parseLocadora(resultSet));

        }

        if (lista.isEmpty()){
            throw new EnderecoNaoEncontradoException("Não temos registros de locadoras neste endereço.");
        }

        return lista;
    }

    //Método para pesquisar a locadora por id
    public Locadora pesquisaId(int id) throws SQLException, IdNaoEncontradoException{

        //Criando o PreparedStatement
        PreparedStatement stmt = conexao.prepareStatement(SELECT_ID_SQL);

        //Setando o valor do id
        stmt.setInt(1, id);

        //Executar o comando SQL caso ache o id
        ResultSet resultSet = stmt.executeQuery();

        if (!resultSet.next()){
            throw new IdNaoEncontradoException("Locadora não encontrada.");
        }

        return parseLocadora(resultSet);
    }

    //Método para atualizar os dados registrados da locadora
    public void atualizar(Locadora locadora) throws SQLException, IdNaoEncontradoException{

        //Criando o PreparedStatement
        PreparedStatement stmt = conexao.prepareStatement(UPDATE_SQL);

        //Setando os valores da querry
        preencherStatementComLocadora(stmt, locadora);

        stmt.setInt(4, locadora.getId());

        //Executando o comando caso o ID exista
        int linhas = stmt.executeUpdate();

        if (linhas == 0){
            throw new IdNaoEncontradoException("Locadora não encontrada.");
        }
    }

    //Método para deletar a locadora
    public void apagar(int id) throws SQLException, IdNaoEncontradoException{

        //Criando o PreparedStatement
        PreparedStatement stmt = conexao.prepareStatement(DELETE_SQL);

        //Setando o valor do ID na querry
        stmt.setInt(1, id);

        //Executando o comando SQL caso ache o id
        int linhas = stmt.executeUpdate();

        if (linhas == 0){
            throw new IdNaoEncontradoException("Locadora não encontrada.");
        }
    }

    //método para preencher os Statements
    private void preencherStatementComLocadora(PreparedStatement stmt, Locadora locadora) throws SQLException{
        //Setando os valores da querry
        stmt.setString(1, locadora.getNome());
        stmt.setString(2, locadora.getEndereco());

        if (locadora.getTelefone() != null){
            stmt.setString(3, locadora.getTelefone());
        } else {
            stmt.setNull(3, Types.VARCHAR);
        }
    }

    private Locadora parseLocadora(ResultSet resultSet) throws SQLException{

        int id = resultSet.getInt("id_locadora");
        String nome = resultSet.getString("nm_locadora");
        String endereco = resultSet.getString("ds_endereco");
        String telefone = resultSet.getString("nr_telefone");

        //Criando o objeto locadora
        Locadora locadora = new Locadora(id, nome, endereco, telefone);
        return locadora;
    }
}