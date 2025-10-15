package br.edu.cs.poo.ac.ordem.mediators;

import br.edu.cs.poo.ac.ordem.daos.DesktopDAO;
import br.edu.cs.poo.ac.ordem.daos.NotebookDAO;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.utils.StringUtils;
import br.edu.cs.poo.ac.utils.ListaString;

public class EquipamentoMediator {

    private NotebookDAO notebookDAO = new NotebookDAO();
    private DesktopDAO desktopDAO = new DesktopDAO();
    private static EquipamentoMediator instancia;

    private EquipamentoMediator() {}

    public static EquipamentoMediator getInstancia() {
        if (instancia == null) {
            instancia = new EquipamentoMediator();
        }
        return instancia;
    }

    public ResultadoMediator validar(DadosEquipamento equip) {
        ListaString erros = new ListaString();

        if (StringUtils.estaVazia(equip.getSerial())) {
            erros.adicionar("Serial não informado.");
        }
        if (StringUtils.estaVazia(equip.getDescricao())) {
            erros.adicionar("Descrição não informada.");
        }
        if (equip.getValorEstimado() < 0) {
            erros.adicionar("Valor estimado não pode ser negativo.");
        }

        boolean validado = (erros.tamanho() == 0);
        return new ResultadoMediator(validado, false, erros);
    }

    public ResultadoMediator validarNotebook(Notebook note) {
        DadosEquipamento dados = new DadosEquipamento(note.getSerial(), note.getDescricao(), note.isEhNovo(), note.getValorEstimado());
        return validar(dados);
    }

    public ResultadoMediator validarDesktop(Desktop desk) {
        DadosEquipamento dados = new DadosEquipamento(desk.getSerial(), desk.getDescricao(), desk.isEhNovo(), desk.getValorEstimado());
        return validar(dados);
    }

    public ResultadoMediator incluirNotebook(Notebook note) {
        ResultadoMediator resultadoValidacao = validarNotebook(note);
        if (!resultadoValidacao.isValidado()) {
            return resultadoValidacao;
        }

        ListaString erros = new ListaString();
        boolean operacaoRealizada = notebookDAO.incluir(note);
        if (!operacaoRealizada) {
            erros.adicionar("Notebook já existente.");
        }
        return new ResultadoMediator(true, operacaoRealizada, erros);
    }

    public ResultadoMediator alterarNotebook(Notebook note) {
        ResultadoMediator resultadoValidacao = validarNotebook(note);
        if (!resultadoValidacao.isValidado()) {
            return resultadoValidacao;
        }

        ListaString erros = new ListaString();
        boolean operacaoRealizada = notebookDAO.alterar(note);
        if (!operacaoRealizada) {
            erros.adicionar("Notebook não encontrado para alteração.");
        }
        return new ResultadoMediator(true, operacaoRealizada, erros);
    }

    public ResultadoMediator excluirNotebook(String idUnico) {
        ListaString erros = new ListaString();
        boolean operacaoRealizada = notebookDAO.excluir(idUnico);
        if (!operacaoRealizada) {
            erros.adicionar("Notebook não encontrado para exclusão.");
        }
        return new ResultadoMediator(true, operacaoRealizada, erros);
    }

    public Notebook buscarNotebook(String idUnico) {
        return notebookDAO.buscar(idUnico);
    }

    public ResultadoMediator incluirDesktop(Desktop desk) {
        ResultadoMediator resultadoValidacao = validarDesktop(desk);
        if (!resultadoValidacao.isValidado()) {
            return resultadoValidacao;
        }

        ListaString erros = new ListaString();
        boolean operacaoRealizada = desktopDAO.incluir(desk);
        if (!operacaoRealizada) {
            erros.adicionar("Desktop já existente.");
        }
        return new ResultadoMediator(true, operacaoRealizada, erros);
    }

    public ResultadoMediator alterarDesktop(Desktop desk) {
        ResultadoMediator resultadoValidacao = validarDesktop(desk);
        if (!resultadoValidacao.isValidado()) {
            return resultadoValidacao;
        }

        ListaString erros = new ListaString();
        boolean operacaoRealizada = desktopDAO.alterar(desk);
        if (!operacaoRealizada) {
            erros.adicionar("Desktop não encontrado para alteração.");
        }
        return new ResultadoMediator(true, operacaoRealizada, erros);
    }

    public ResultadoMediator excluirDesktop(String idUnico) {
        ListaString erros = new ListaString();
        boolean operacaoRealizada = desktopDAO.excluir(idUnico);
        if (!operacaoRealizada) {
            erros.adicionar("Desktop não encontrado para exclusão.");
        }
        return new ResultadoMediator(true, operacaoRealizada, erros);
    }

    public Desktop buscarDesktop(String idUnico) {
        return desktopDAO.buscar(idUnico);
    }
}