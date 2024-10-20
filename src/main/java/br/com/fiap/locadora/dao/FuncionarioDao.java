package br.com.fiap.locadora.dao;

import br.com.fiap.locadora.exceptions.IdNaoEncontradoException;
import br.com.fiap.locadora.exceptions.NomeNaoEncontradoException;
import br.com.fiap.locadora.model.Funcionario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDao {

    private static final String INSERT_SQL = "insert into t_funcionario values (sq_t_funcionario.nextval, ?, ?, ?, ?, ?)";
    private static final String SELECT_SQL = "select * from t_funcionario order by id_funcionario";
    private static final String SELECT_ID_SQL = "select * from t_funcionario where id_funcionario = ?";
    private static final String SELECT_NOME_SQL = "select * from t_funcionario where upper(nm_funcionario) like upper(?) " +
            "order by id_funcionario";
    private static final String SELECT_ID_LOCADORA_SQL = "select * from t_funcionario where id_locadora = ? " +
            "order by id_funcionario";
    private static final String UPDATE_SQL = "update t_funcionario set id_locadora = ?," +
            " nr_cpf = ?, nm_funcionario = ?, dt_nascimento = ?, ano_admissao = ? where id_funcionario = ?";
    private static final String DELETE_SQL = "delete from t_funcionario where id_funcionario = ?";

    private Connection conexao;

    public FuncionarioDao(Connection conexao){this.conexao = conexao;}

    public void cadastrar(Funcionario funcionario) throws SQLException{

        //Criando um statement configurável
        PreparedStatement stmt = conexao.prepareStatement(INSERT_SQL, new String[] {"id_funcionario"});

        //Setando os valores da querry
        preencherStatementeComFuncionario(stmt, funcionario);

        //Executando o comando SQL
        stmt.executeUpdate();

        //Recuperando o id gerado pela sequence
        ResultSet resultSet = stmt.getGeneratedKeys();
        resultSet.next();
        funcionario.setId(resultSet.getInt(1));
    }

    //Método para listar todos os funcionários
    public List<Funcionario> listar() throws SQLException{

        //Criando o Statement
        Statement stm = conexao.createStatement();

        //Executando o comando SQL
        ResultSet resultSet = stm.executeQuery(SELECT_SQL);

        //Criando a lista de Funcionários
        List<Funcionario> lista = new ArrayList<>();

        while (resultSet.next()){
            Funcionario funcionario = parseFuncionario(resultSet);
            lista.add(funcionario);
        }

        return lista;
    }

    public Funcionario pesquisarId(int id) throws SQLException, IdNaoEncontradoException{

        //Criado o Prepared statement
        PreparedStatement stmt = conexao.prepareStatement(SELECT_ID_SQL);

        //Setando o id
        stmt.setInt(1, id);

        //Executar o comando SQL
        ResultSet resultSet = stmt.executeQuery();

        if (!resultSet.next()){
            throw new IdNaoEncontradoException("Funcionário não encontrado");
        }

        return parseFuncionario(resultSet);
    }

    //Método para pesquisar o funcionário por nome
    public List<Funcionario> pesquisaNome(String nome) throws SQLException, NomeNaoEncontradoException{

        //Criando o PreparedStament
        PreparedStatement stmt = conexao.prepareStatement(SELECT_NOME_SQL);

        //Setando o valor na Querry
        stmt.setString(1, "%" + nome + "%");

        ResultSet resultSet = stmt.executeQuery();

        //Criando a lista de funcionários
        List<Funcionario> lista = new ArrayList<>();

        while (resultSet.next()){
            Funcionario funcionario = parseFuncionario(resultSet);
            lista.add(funcionario);
        }

        if (lista.isEmpty()){
            throw new NomeNaoEncontradoException("Perdão, não temos um funcionário com este nome.");
        }

        return lista;
    }

    //Método para pesquisar os funcionários pela locadora que eles trabalham (com ID)
    public List<Funcionario> pesquisaPorLocadora(int idConcess) throws SQLException, IdNaoEncontradoException{

        //Criando o PreparedStatement
        PreparedStatement stmt = conexao.prepareStatement(SELECT_ID_LOCADORA_SQL);

        //Setando o valor da querry
        stmt.setInt(1, idConcess);

        ResultSet resultSet = stmt.executeQuery();

        //Criando a lista para salvar os dados encontrados
        List<Funcionario> lista = new ArrayList<>();

        while (resultSet.next()){
            Funcionario funcionario = parseFuncionario(resultSet);
            lista.add(funcionario);
        }

        if (lista.isEmpty()){
            throw new IdNaoEncontradoException("Locadora não encontrada ou não existe um funcionário lá.");
        }

        return lista;
    }

    //Método para atualizar o funcionário registrado
    public void atualizar(Funcionario funcionario) throws SQLException, IdNaoEncontradoException{

        //Criando o statement
        PreparedStatement stmt = conexao.prepareStatement(UPDATE_SQL);

        stmt.setInt(6, funcionario.getId());

        //Setando os valores
        preencherStatementeComFuncionario(stmt, funcionario);
        //Executando o comando caso o ID exista
        int linhas = stmt.executeUpdate();

        if (linhas == 0){
            throw new IdNaoEncontradoException("Funcionário não encontrado.");
        }
    }

    //Método para deletar um funcionário
    public void apagar(int id) throws SQLException, IdNaoEncontradoException{

        //Criando o PreparedStatement
        PreparedStatement stmt = conexao.prepareStatement(DELETE_SQL);

        //Setando os valores da querry
        stmt.setInt(1, id);

        int linhas = stmt.executeUpdate();

        if (linhas == 0){
            throw new IdNaoEncontradoException("Funcionário não encontrado.");
        }
    }

    public void preencherStatementeComFuncionario(PreparedStatement stmt, Funcionario funcionario) throws SQLException{
        if (funcionario.getLocadora() != null){
            stmt.setInt(1, funcionario.getLocadora().getId());
        } else {
            stmt.setNull(1, Types.VARCHAR);
        }
        stmt.setString(2, funcionario.getCpf());
        stmt.setString(3, funcionario.getNome());
        stmt.setDate(4, Date.valueOf(funcionario.getDtNasc()));
        stmt.setInt(5, funcionario.getAnoAdmissao());

    }

    private Funcionario parseFuncionario(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id_funcionario");
        String cpf = resultSet.getString("nr_cpf");
        String nome = resultSet.getString("nm_funcionario");
        LocalDate dtNasc = resultSet.getDate("dt_nascimento").toLocalDate();
        int dtAdmissao = resultSet.getInt("ano_admissao");

        //Criando o objeto cliente
        Funcionario funcionario = new Funcionario(id, cpf, nome, dtNasc, dtAdmissao);

        return funcionario;
    }
}