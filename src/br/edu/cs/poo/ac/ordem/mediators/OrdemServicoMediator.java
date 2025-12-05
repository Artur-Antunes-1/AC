package br.edu.cs.poo.ac.ordem.mediators;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.edu.cs.poo.ac.excecoes.ExcecaoNegocio;
import br.edu.cs.poo.ac.ordem.daos.ClienteDAO;
import br.edu.cs.poo.ac.ordem.daos.DAORegistro;
import br.edu.cs.poo.ac.ordem.daos.DesktopDAO;
import br.edu.cs.poo.ac.ordem.daos.NotebookDAO;
import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Equipamento;
import br.edu.cs.poo.ac.ordem.entidades.FechamentoOrdemServico;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.ordem.entidades.OrdemServico;
import br.edu.cs.poo.ac.ordem.entidades.PrecoBase;
import br.edu.cs.poo.ac.ordem.entidades.StatusOrdem;
import br.edu.cs.poo.ac.utils.ListaString;
import static br.edu.cs.poo.ac.utils.StringUtils.*;
import static br.edu.cs.poo.ac.utils.ValidadorCPFCNPJ.isCPF;

public class OrdemServicoMediator {
    private static OrdemServicoMediator instancia;
    private DAORegistro<OrdemServico> daoOrdem = new DAORegistro<>(OrdemServico.class);
    private ClienteDAO daoCliente = new ClienteDAO();
    private DesktopDAO daoDesktop = new DesktopDAO();
    private NotebookDAO daoNotebook = new NotebookDAO();

    private static final String ORDEM_JA_FOI_FECHADA = "Ordem já foi fechada";
    private static final String ORDEM_JA_FOI_CANCELADA = "Ordem já foi cancelada";
    private static final String NUMERO_DE_ORDEM_NAO_ENCONTRADO = "Número de ordem não encontrado";
    private static final String NUMERO_DE_ORDEM_NAO_INFORMADO = "Número de ordem não informado";
    private static final String RELATORIO_FINAL_NAO_INFORMADO = "Relatório final não informado";
    private static final String NUMERO_DE_ORDEM_DEVE_SER_INFORMADO = "Número de ordem deve ser informado";
    private static final String MOTIVO_DEVE_SER_INFORMADO = "Motivo deve ser informado";

    public static OrdemServicoMediator getInstancia() {
        if (instancia == null) {
            instancia = new OrdemServicoMediator();
        }
        return instancia;
    }

    private OrdemServicoMediator() {
    }

    public void incluir(DadosOrdemServico dados) throws ExcecaoNegocio {
        ResultadoMediator resultado = validarInclusao(dados);
        if (!resultado.isValidado()) {
            throw new ExcecaoNegocio(resultado);
        }

        Cliente cliente = daoCliente.buscar(dados.getCpfCnpjCliente());
        PrecoBase precoBase = PrecoBase.getPrecoBase(dados.getCodigoPrecoBase());
        Equipamento equipamento = buscarEquipamento(dados.getIdEquipamento());

        double valor = calcularValor(cliente, precoBase, equipamento);
        int prazo = calcularPrazo(cliente, precoBase, equipamento);

        LocalDateTime dataHoraAbertura = LocalDateTime.now();
        OrdemServico ordem = new OrdemServico(cliente, precoBase, equipamento, dataHoraAbertura, prazo, valor);
        ordem.setVendedor(dados.getVendedor());
        ordem.setStatus(StatusOrdem.ABERTA);

        try {
            daoOrdem.incluir(ordem);
        } catch (Exception e) {
            ListaString erros = new ListaString();
            erros.adicionar(e.getMessage());
            throw new ExcecaoNegocio(new ResultadoMediator(false, false, erros));
        }
    }

    public void cancelar(String numero, String motivo, LocalDateTime dataHoraCancelamento) throws ExcecaoNegocio {
        ResultadoMediator resultado = validarCancelamento(numero, motivo, dataHoraCancelamento);
        if (!resultado.isValidado()) {
            throw new ExcecaoNegocio(resultado);
        }

        OrdemServico ordem = daoOrdem.buscar(numero);
        if (ordem == null) {
            ListaString erros = new ListaString();
            erros.adicionar(NUMERO_DE_ORDEM_NAO_ENCONTRADO);
            throw new ExcecaoNegocio(new ResultadoMediator(false, false, erros));
        }

        if (ordem.getStatus() == StatusOrdem.CANCELADA) {
            ListaString erros = new ListaString();
            erros.adicionar(ORDEM_JA_FOI_CANCELADA);
            throw new ExcecaoNegocio(new ResultadoMediator(false, false, erros));
        }

        if (ordem.getStatus() == StatusOrdem.FECHADA) {
            ListaString erros = new ListaString();
            erros.adicionar(ORDEM_JA_FOI_FECHADA);
            throw new ExcecaoNegocio(new ResultadoMediator(false, false, erros));
        }

        LocalDateTime dataAbertura = ordem.getDataHoraAbertura();
        if (dataHoraCancelamento.isAfter(dataAbertura.plusDays(2))) {
            ListaString erros = new ListaString();
            erros.adicionar("Ordem aberta há mais de dois dias não pode ser cancelada");
            throw new ExcecaoNegocio(new ResultadoMediator(false, false, erros));
        }

        ordem.setStatus(StatusOrdem.CANCELADA);
        ordem.setMotivoCancelamento(motivo);
        ordem.setDataHoraCancelamento(dataHoraCancelamento);

        try {
            daoOrdem.alterar(ordem);
        } catch (Exception e) {
            ListaString erros = new ListaString();
            erros.adicionar(e.getMessage());
            throw new ExcecaoNegocio(new ResultadoMediator(false, false, erros));
        }
    }

    public void fechar(FechamentoOrdemServico fechamento) throws ExcecaoNegocio {
        ResultadoMediator resultado = validarFechamento(fechamento);
        if (!resultado.isValidado()) {
            throw new ExcecaoNegocio(resultado);
        }

        OrdemServico ordem = daoOrdem.buscar(fechamento.getNumeroOrdemServico());
        if (ordem == null) {
            ListaString erros = new ListaString();
            erros.adicionar(NUMERO_DE_ORDEM_NAO_ENCONTRADO);
            throw new ExcecaoNegocio(new ResultadoMediator(false, false, erros));
        }

        if (ordem.getStatus() == StatusOrdem.CANCELADA) {
            ListaString erros = new ListaString();
            erros.adicionar(ORDEM_JA_FOI_CANCELADA);
            throw new ExcecaoNegocio(new ResultadoMediator(false, false, erros));
        }

        if (ordem.getStatus() == StatusOrdem.FECHADA) {
            ListaString erros = new ListaString();
            erros.adicionar(ORDEM_JA_FOI_FECHADA);
            throw new ExcecaoNegocio(new ResultadoMediator(false, false, erros));
        }

        ordem.setStatus(StatusOrdem.FECHADA);
        ordem.setDadosFechamento(fechamento);

        try {
            daoOrdem.alterar(ordem);
        } catch (Exception e) {
            ListaString erros = new ListaString();
            erros.adicionar(e.getMessage());
            throw new ExcecaoNegocio(new ResultadoMediator(false, false, erros));
        }
    }

    public OrdemServico buscar(String numero) {
        return daoOrdem.buscar(numero);
    }

    private ResultadoMediator validarInclusao(DadosOrdemServico dados) {
        ListaString erros = new ListaString();

        if (dados == null) {
            erros.adicionar("Dados básicos da ordem de serviço não informados");
            return new ResultadoMediator(false, false, erros);
        }

        PrecoBase precoBase = PrecoBase.getPrecoBase(dados.getCodigoPrecoBase());
        if (precoBase == null) {
            erros.adicionar("Código do preço base inválido");
        } else {
            if (precoBase == PrecoBase.REVISAO) {
                Cliente cliente = daoCliente.buscar(dados.getCpfCnpjCliente());
                if (cliente != null && isCPF(cliente.getCpfCnpj())) {
                    erros.adicionar("Prazo e valor não podem ser avaliados pois o preço base é incompatível com cliente pessoa física");
                }
            }
        }

        if (estaVazia(dados.getVendedor())) {
            erros.adicionar("Vendedor não informado");
        }

        if (estaVazia(dados.getCpfCnpjCliente())) {
            erros.adicionar("CPF/CNPJ do cliente não informado");
        } else {
            Cliente cliente = daoCliente.buscar(dados.getCpfCnpjCliente());
            if (cliente == null) {
                erros.adicionar("CPF/CNPJ do cliente não encontrado");
            }
        }

        if (estaVazia(dados.getIdEquipamento())) {
            erros.adicionar("Id do equipamento não informado");
        } else {
            Equipamento equipamento = buscarEquipamento(dados.getIdEquipamento());
            if (equipamento == null) {
                erros.adicionar("Id do equipamento não encontrado");
            }
        }

        boolean validado = (erros.tamanho() == 0);
        return new ResultadoMediator(validado, false, erros);
    }

    private ResultadoMediator validarCancelamento(String numero, String motivo, LocalDateTime dataHoraCancelamento) {
        ListaString erros = new ListaString();

        if (estaVazia(motivo)) {
            erros.adicionar(MOTIVO_DEVE_SER_INFORMADO);
        }

        if (dataHoraCancelamento == null) {
            erros.adicionar("Data/hora cancelamento deve ser informada");
        } else {
            if (dataHoraCancelamento.isAfter(LocalDateTime.now())) {
                erros.adicionar("Data/hora cancelamento deve ser menor do que a data hora atual");
            }
        }

        if (estaVazia(numero)) {
            erros.adicionar(NUMERO_DE_ORDEM_DEVE_SER_INFORMADO);
        }

        boolean validado = (erros.tamanho() == 0);
        return new ResultadoMediator(validado, false, erros);
    }

    private ResultadoMediator validarFechamento(FechamentoOrdemServico fechamento) {
        ListaString erros = new ListaString();

        if (fechamento == null) {
            erros.adicionar("Dados do fechamento de ordem não informados");
            return new ResultadoMediator(false, false, erros);
        }

        if (estaVazia(fechamento.getRelatorioFinal())) {
            erros.adicionar(RELATORIO_FINAL_NAO_INFORMADO);
        }

        if (fechamento.getDataFechamento() == null) {
            erros.adicionar("Data de fechamento não informada");
        } else {
            if (fechamento.getDataFechamento().isAfter(LocalDate.now())) {
                erros.adicionar("Data de fechamento maior que a data atual");
            }
        }

        if (estaVazia(fechamento.getNumeroOrdemServico())) {
            erros.adicionar(NUMERO_DE_ORDEM_NAO_INFORMADO);
        }

        boolean validado = (erros.tamanho() == 0);
        return new ResultadoMediator(validado, false, erros);
    }

    private Equipamento buscarEquipamento(String idEquipamento) {
        if (idEquipamento == null || idEquipamento.length() < 2) {
            return null;
        }

        String prefixo = idEquipamento.substring(0, 2);
        String serial = idEquipamento.substring(2);

        if ("DE".equals(prefixo)) {
            return daoDesktop.buscar(idEquipamento);
        } else if ("NO".equals(prefixo)) {
            return daoNotebook.buscar(idEquipamento);
        }

        return null;
    }

    private double calcularValor(Cliente cliente, PrecoBase precoBase, Equipamento equipamento) {
        boolean ehCPF = isCPF(cliente.getCpfCnpj());
        boolean ehNotebook = equipamento instanceof Notebook;

        switch (precoBase) {
            case MANUTENCAO_NORMAL:
                return ehCPF ? 180.00 : 240.00;
            case MANUTENCAO_EMERGENCIAL:
                return ehCPF ? 280.00 : 340.00;
            case REVISAO:
                return 270.00;
            case LIMPEZA:
                return ehCPF ? 210.00 : 250.00;
            default:
                return 0.0;
        }
    }

    private int calcularPrazo(Cliente cliente, PrecoBase precoBase, Equipamento equipamento) {
        boolean ehNotebook = equipamento instanceof Notebook;

        switch (precoBase) {
            case MANUTENCAO_NORMAL:
                return 6;
            case MANUTENCAO_EMERGENCIAL:
                return ehNotebook ? 4 : 3;
            case REVISAO:
                return 6;
            case LIMPEZA:
                return 6;
            default:
                return 0;
        }
    }
}

