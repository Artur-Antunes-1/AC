package br.edu.cs.poo.ac.ordem.mediators;

import br.edu.cs.poo.ac.ordem.daos.ClienteDAO;
import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.utils.StringUtils;
import br.edu.cs.poo.ac.utils.ValidadorCPFCNPJ;
import br.edu.cs.poo.ac.utils.ResultadoValidacaoCPFCNPJ;
import br.edu.cs.poo.ac.utils.ListaString;

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

    public ResultadoMediator validar(Cliente cliente) {
        ListaString erros = new ListaString();

        if (StringUtils.estaVazia(cliente.getCpfCnpj())) {
            erros.adicionar("CPF/CNPJ não informado.");
        } else {
            ResultadoValidacaoCPFCNPJ resValidacao = ValidadorCPFCNPJ.validarCPFCNPJ(cliente.getCpfCnpj());
            if (resValidacao.getErroValidacao() != null) {
                erros.adicionar(resValidacao.getErroValidacao().getMensagem());
            }
        }
        if (StringUtils.estaVazia(cliente.getNome())) {
            erros.adicionar("Nome não informado.");
        }
        if (cliente.getContato() == null) {
            erros.adicionar("Contato não informado.");
        } else {
            if (!StringUtils.emailValido(cliente.getContato().getEmail())) {
                erros.adicionar("E-mail inválido.");
            }
            if(StringUtils.estaVazia(cliente.getContato().getContato())) {
                erros.adicionar("Celular não informado.");
            }
        }

        boolean validado = (erros.tamanho() == 0);
        return new ResultadoMediator(validado, false, erros);
    }

    public ResultadoMediator incluir(Cliente cliente) {
        ResultadoMediator resultadoValidacao = validar(cliente);
        if (!resultadoValidacao.isValidado()) {
            return resultadoValidacao;
        }

        ListaString erros = new ListaString();
        boolean operacaoRealizada = clienteDAO.incluir(cliente);
        if (!operacaoRealizada) {
            erros.adicionar("Cliente já existente.");
        }
        return new ResultadoMediator(true, operacaoRealizada, erros);
    }

    public ResultadoMediator alterar(Cliente cliente) {
        ResultadoMediator resultadoValidacao = validar(cliente);
        if (!resultadoValidacao.isValidado()) {
            return resultadoValidacao;
        }

        ListaString erros = new ListaString();
        boolean operacaoRealizada = clienteDAO.alterar(cliente);
        if (!operacaoRealizada) {
            erros.adicionar("Cliente não encontrado para alteração.");
        }
        return new ResultadoMediator(true, operacaoRealizada, erros);
    }

    public ResultadoMediator excluir(String cpfCnpj) {
        ListaString erros = new ListaString();
        boolean operacaoRealizada = clienteDAO.excluir(cpfCnpj);
        if (!operacaoRealizada) {
            erros.adicionar("Cliente não encontrado para exclusão.");
        }
        return new ResultadoMediator(true, operacaoRealizada, erros);
    }

    public Cliente buscar(String cpfCnpj) {
        return clienteDAO.buscar(cpfCnpj);
    }
}