package br.edu.cs.poo.ac.ordem.mediators;

import br.edu.cs.poo.ac.ordem.daos.ClienteDAO;
import br.edu.cs.poo.ac.ordem.entidades.Cliente;

public class ClienteMediator {

    private ClienteDAO clienteDAO = new ClienteDAO();

    private static ClienteMediator instancia;

    private ClienteMediator() {}

    public static ClienteMediator getInstancia() {
        if (instancia == null) {
            instancia = new ClienteMediator();
        }
        return instancia;
    }

    public ResultadoMediator incluir(Cliente cliente) {}
    public ResultadoMediator alterar(Cliente cliente) {}
    public ResultadoMediator excluir(String cpfCnpj) {}
    public Cliente buscar(String cpfCnpj) {}
    public ResultadoMediator validar(Cliente cliente) {}
}