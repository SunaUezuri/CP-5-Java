package br.com.fiap.locadora.resource;

import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.FuncionarioDao;
import br.com.fiap.locadora.dto.funcionario.AtualizaFuncionarioDto;
import br.com.fiap.locadora.dto.funcionario.CadastroFuncionarioDto;
import br.com.fiap.locadora.dto.funcionario.DetalhesFuncionarioDto;
import br.com.fiap.locadora.exceptions.IdNaoEncontradoException;
import br.com.fiap.locadora.exceptions.NomeNaoEncontradoException;
import br.com.fiap.locadora.model.Funcionario;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/funcionario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FuncionarioResource {

    private FuncionarioDao funcionarioDao;
    private ModelMapper modelMapper;

    public FuncionarioResource() throws Exception{
        funcionarioDao = new FuncionarioDao(ConnectionFactory.getConnection());
        modelMapper = new ModelMapper();
    }

    //Método POST
    @POST
    public Response cadastrar(@Valid CadastroFuncionarioDto dto, @Context UriInfo uriInfo) throws SQLException {
        Funcionario funcionario = modelMapper.map(dto, Funcionario.class);
        funcionarioDao.cadastrar(funcionario);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(funcionario.getId()));
        return Response.created(builder.build()).build();
    }

    //Método GET
    @GET
    public List<DetalhesFuncionarioDto> buscar() throws SQLException{
        return funcionarioDao.listar().stream()
                .map(f -> modelMapper.map(f, DetalhesFuncionarioDto.class))
                .collect(Collectors.toList());
    }

    //Método GET para utilizar com o Id
    @Path("/{id}")
    @GET
    public DetalhesFuncionarioDto buscarId(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException {
        Funcionario funcionario = funcionarioDao.pesquisarId(id);
        DetalhesFuncionarioDto dto = modelMapper.map(funcionario, DetalhesFuncionarioDto.class);
        return dto;
    }

    @Path("pesquisa/locadora")
    @GET
    public List<DetalhesFuncionarioDto> buscarIdLocadora(@QueryParam("id") int id) throws SQLException, IdNaoEncontradoException {
        return funcionarioDao.pesquisaPorLocadora(id).stream()
                .map(f -> modelMapper.map(f, DetalhesFuncionarioDto.class))
                .collect(Collectors.toList());
    }

    //Método para pesquisar pelo nome
    @Path("pesquisa")
    @GET
    public List<DetalhesFuncionarioDto> buscarNome(@DefaultValue("e") @QueryParam("nome") String nome) throws SQLException, NomeNaoEncontradoException {
        return funcionarioDao.pesquisaNome(nome).stream()
                .map(f -> modelMapper.map(f, DetalhesFuncionarioDto.class))
                .collect(Collectors.toList());
    }

    //Método para dar update
    @PUT
    @Path("/{id}")
    public Response atualizar(@Valid AtualizaFuncionarioDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        Funcionario funcionario = modelMapper.map(dto, Funcionario.class);
        funcionario.setId(id);
        funcionarioDao.atualizar(funcionario);
        return Response.ok().build();
    }

    //Método Delete
    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int id) throws IdNaoEncontradoException, SQLException{
        funcionarioDao.apagar(id);
        return Response.noContent().build();
    }
}
