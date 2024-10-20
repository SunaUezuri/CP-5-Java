package br.com.fiap.locadora.resource;


import br.com.fiap.locadora.connection.ConnectionFactory;
import br.com.fiap.locadora.dao.ClienteDao;
import br.com.fiap.locadora.dto.cliente.AtualizaClienteDto;
import br.com.fiap.locadora.dto.cliente.AtualizaParcial;
import br.com.fiap.locadora.dto.cliente.CadastroClienteDto;
import br.com.fiap.locadora.dto.cliente.DetalhesClienteDto;
import br.com.fiap.locadora.exceptions.IdNaoEncontradoException;
import br.com.fiap.locadora.exceptions.NomeNaoEncontradoException;
import br.com.fiap.locadora.model.Cliente;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


@Path("/cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    //Atibutos para criar o construtor do resource
    private ClienteDao clienteDao;
    private ModelMapper modelMapper;

    //Construtor
    public ClienteResource() throws Exception{
        clienteDao = new ClienteDao(ConnectionFactory.getConnection());
        modelMapper = new ModelMapper();
    }

    //Método POST
    @POST
    public Response cadastrar(@Valid CadastroClienteDto dto, @Context UriInfo uriInfo) throws SQLException{
        Cliente cliente = modelMapper.map(dto, Cliente.class);
        clienteDao.cadastrar(cliente);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(cliente.getId()));
        return Response.created(builder.build()).build();
    }

    //Método GET
    @GET
    public List<DetalhesClienteDto> buscar() throws SQLException{
        return clienteDao.listar().stream()
                .map(c -> modelMapper.map(c, DetalhesClienteDto.class))
                .collect(Collectors.toList());
    }

    //Método GET para utilizar com o Id
    @Path("/{id}")
    @GET
    public DetalhesClienteDto buscarId(@PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        Cliente cliente = clienteDao.pesquisaId(id);
        DetalhesClienteDto dto = modelMapper.map(cliente, DetalhesClienteDto.class);
        return dto;
    }

    //Método para pesquisar pelo nome
    @Path("pesquisa")
    @GET
    public List<DetalhesClienteDto> buscarNome(@DefaultValue("e") @QueryParam("nome") String nome) throws SQLException, NomeNaoEncontradoException {
        return clienteDao.pesquisaNome(nome).stream()
                .map(c -> modelMapper.map(c, DetalhesClienteDto.class))
                .collect(Collectors.toList());
    }

    //Método para dar update
    @PUT
    @Path("/{id}")
    public Response atualizar(@Valid AtualizaClienteDto dto, @PathParam("id") int id) throws SQLException, IdNaoEncontradoException{
        Cliente cliente = modelMapper.map(dto, Cliente.class);
        cliente.setId(id);
        clienteDao.atualizar(cliente);
        return Response.ok().build();
    }

    //Método DELETE
    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") int id) throws IdNaoEncontradoException, SQLException{
        clienteDao.apagar(id);
        return Response.noContent().build();
    }

    //Método para atualizar parcialmente
    @PATCH
    @Path("/{id}")
    public Response atualizaParcial(@Valid @PathParam("id") int id, AtualizaParcial dto) throws IdNaoEncontradoException, SQLException{
        //Pesquisar o cliente no banco
        Cliente cliente = clienteDao.pesquisaId(id);

        //Verificando se o valor existe, e injetar o valor novo
        if (dto.getCpf() != null)
            cliente.setNome(dto.getCpf());
        if (dto.getNome() != null)
            cliente.setCpf(dto.getCpf());
        if (dto.getDtNasc() != null)
            cliente.setDtNasc(dto.getDtNasc());
        if (dto.getEndereco() != null)
            cliente.setEndereco(dto.getEndereco());
        if (dto.getEmail() != null)
            cliente.setEmail(dto.getEmail());

        return Response.ok().build();
    }
}
