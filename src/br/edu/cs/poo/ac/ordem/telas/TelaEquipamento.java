package br.edu.cs.poo.ac.ordem.telas;

import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Equipamento;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.ordem.mediators.EquipamentoMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;
import br.edu.cs.poo.ac.utils.ElementoListaString;
import br.edu.cs.poo.ac.utils.ListaString;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TelaEquipamento {

    private JPanel panelPrincipal;
    private JComboBox<String> comboTipo;
    private JTextField txtSerial;
    private JTextArea txtDescricao;
    private JRadioButton radioEhNovoNao;
    private JRadioButton radioEhNovoSim;
    private JFormattedTextField txtValorEstimado;
    private JButton btnNovo;
    private JButton btnBuscar;
    private JButton btnSalvar;
    private JButton btnExcluir;
    private JButton btnCancelar;
    private JButton btnLimpar;

    private JPanel panelNotebook;
    private JRadioButton radioDadosSensiveisNao;
    private JRadioButton radioDadosSensiveisSim;
    private JPanel panelDesktop;
    private JRadioButton radioEhServidorNao;
    private JRadioButton radioEhServidorSim;

    private EquipamentoMediator mediator = EquipamentoMediator.getInstancia();

    private enum EstadoTela {
        INICIAL,
        CRIANDO,
        EDITANDO
    }

    public TelaEquipamento() {
        inicializarComponentes();
        gerenciarEstadoTela(EstadoTela.INICIAL);

        comboTipo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                atualizarPaineisDinamicos();
            }
        });

        btnNovo.addActionListener(e -> {
            if (txtSerial.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(panelPrincipal, "Serial deve ser preenchido!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String idTipo = getTipoSelecionado();
            String idUnico = idTipo + txtSerial.getText();
            Equipamento equipExistente = null;
            if (idTipo.equals("NO")) {
                equipExistente = mediator.buscarNotebook(idUnico);
            } else {
                equipExistente = mediator.buscarDesktop(idUnico);
            }

            if (equipExistente != null) {
                JOptionPane.showMessageDialog(panelPrincipal, "Equipamento já existente!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                gerenciarEstadoTela(EstadoTela.CRIANDO);
            }
        });

        btnBuscar.addActionListener(e -> {
            if (txtSerial.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(panelPrincipal, "Serial deve ser preenchido!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String idTipo = getTipoSelecionado();
            String idUnico = idTipo + txtSerial.getText();
            Equipamento equipamento = null;
            if (idTipo.equals("NO")) {
                equipamento = mediator.buscarNotebook(idUnico);
            } else {
                equipamento = mediator.buscarDesktop(idUnico);
            }

            if (equipamento == null) {
                JOptionPane.showMessageDialog(panelPrincipal, "Equipamento não existente!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                preencherCampos(equipamento);
                gerenciarEstadoTela(EstadoTela.EDITANDO);
            }
        });

        btnSalvar.addActionListener(e -> {
            ResultadoMediator res;
            String msgSucesso;

            if (getTipoSelecionado().equals("NO")) {
                Notebook note = construirNotebookDosCampos();
                if (btnSalvar.getText().equals("Incluir")) {
                    res = mediator.incluirNotebook(note);
                    msgSucesso = "Inclusão realizada com sucesso";
                } else {
                    res = mediator.alterarNotebook(note);
                    msgSucesso = "Alteração realizada com sucesso";
                }
            } else {
                Desktop desk = construirDesktopDosCampos();
                if (btnSalvar.getText().equals("Incluir")) {
                    res = mediator.incluirDesktop(desk);
                    msgSucesso = "Inclusão realizada com sucesso";
                } else {
                    res = mediator.alterarDesktop(desk);
                    msgSucesso = "Alteração realizada com sucesso";
                }
            }

            if (res.isOperacaoRealizada()) {
                JOptionPane.showMessageDialog(panelPrincipal, msgSucesso, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos(true);
                gerenciarEstadoTela(EstadoTela.INICIAL);
            } else {
                exibirErros(res.getMensagensErro());
            }
        });

        btnExcluir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(panelPrincipal, "Tem certeza que deseja excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String idTipo = getTipoSelecionado();
                String idUnico = idTipo + txtSerial.getText();
                ResultadoMediator res;
                if(idTipo.equals("NO")){
                    res = mediator.excluirNotebook(idUnico);
                } else {
                    res = mediator.excluirDesktop(idUnico);
                }

                if (res.isOperacaoRealizada()) {
                    JOptionPane.showMessageDialog(panelPrincipal, "Exclusão realizada com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparCampos(true);
                    gerenciarEstadoTela(EstadoTela.INICIAL);
                } else {
                    JOptionPane.showMessageDialog(panelPrincipal, "Exclusão não pôde ser realizada", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(e -> {
            limparCampos(true);
            gerenciarEstadoTela(EstadoTela.INICIAL);
        });

        btnLimpar.addActionListener(e -> {
            limparCampos(false);
        });
    }

    private void gerenciarEstadoTela(EstadoTela estado) {
        switch (estado) {
            case INICIAL:
                comboTipo.setEnabled(true);
                txtSerial.setEnabled(true);
                btnNovo.setEnabled(true);
                btnBuscar.setEnabled(true);

                txtDescricao.setEnabled(false);
                radioEhNovoNao.setEnabled(false);
                radioEhNovoSim.setEnabled(false);
                txtValorEstimado.setEnabled(false);
                radioDadosSensiveisNao.setEnabled(false);
                radioDadosSensiveisSim.setEnabled(false);
                radioEhServidorNao.setEnabled(false);
                radioEhServidorSim.setEnabled(false);

                btnSalvar.setEnabled(false);
                btnSalvar.setText("Salvar");
                btnExcluir.setEnabled(false);
                btnCancelar.setEnabled(false);
                break;
            case CRIANDO:
            case EDITANDO:
                comboTipo.setEnabled(false);
                txtSerial.setEnabled(false);
                btnNovo.setEnabled(false);
                btnBuscar.setEnabled(false);

                txtDescricao.setEnabled(true);
                radioEhNovoNao.setEnabled(true);
                radioEhNovoSim.setEnabled(true);
                txtValorEstimado.setEnabled(true);
                radioDadosSensiveisNao.setEnabled(true);
                radioDadosSensiveisSim.setEnabled(true);
                radioEhServidorNao.setEnabled(true);
                radioEhServidorSim.setEnabled(true);

                btnSalvar.setEnabled(true);
                btnSalvar.setText(estado == EstadoTela.CRIANDO ? "Incluir" : "Alterar");
                btnExcluir.setEnabled(estado == EstadoTela.EDITANDO);
                btnCancelar.setEnabled(true);
                break;
        }
    }

    private void limparCampos(boolean limparAreaAcesso) {
        if (limparAreaAcesso) {
            comboTipo.setSelectedIndex(0);
            txtSerial.setText("");
        }
        txtDescricao.setText("");
        radioEhNovoNao.setSelected(true);
        txtValorEstimado.setValue(0.0);
        radioDadosSensiveisNao.setSelected(true);
        radioEhServidorNao.setSelected(true);
    }

    private void preencherCampos(Equipamento equip) {
        txtDescricao.setText(equip.getDescricao());
        if(equip.isEhNovo()){
            radioEhNovoSim.setSelected(true);
        } else {
            radioEhNovoNao.setSelected(true);
        }
        txtValorEstimado.setValue(equip.getValorEstimado());

        if(equip instanceof Notebook){
            if(((Notebook) equip).isCarregaDadosSensiveis()){
                radioDadosSensiveisSim.setSelected(true);
            } else {
                radioDadosSensiveisNao.setSelected(true);
            }
        } else if (equip instanceof Desktop){
            if(((Desktop) equip).isEhServidor()){
                radioEhServidorSim.setSelected(true);
            } else {
                radioEhServidorNao.setSelected(true);
            }
        }
    }

    private String getTipoSelecionado(){
        return "Notebook".equals(comboTipo.getSelectedItem()) ? "NO" : "DE";
    }

    private Notebook construirNotebookDosCampos(){
        double valor = 0.0;
        if (txtValorEstimado.getValue() instanceof Number) {
            valor = ((Number)txtValorEstimado.getValue()).doubleValue();
        }
        return new Notebook(
                txtSerial.getText(),
                txtDescricao.getText(),
                radioEhNovoSim.isSelected(),
                valor,
                radioDadosSensiveisSim.isSelected()
        );
    }

    private Desktop construirDesktopDosCampos(){
        double valor = 0.0;
        if (txtValorEstimado.getValue() instanceof Number) {
            valor = ((Number)txtValorEstimado.getValue()).doubleValue();
        }
        return new Desktop(
                txtSerial.getText(),
                txtDescricao.getText(),
                radioEhNovoSim.isSelected(),
                valor,
                radioEhServidorSim.isSelected()
        );
    }

    private void atualizarPaineisDinamicos() {
        boolean isNotebook = "Notebook".equals(comboTipo.getSelectedItem());
        panelNotebook.setVisible(isNotebook);
        panelDesktop.setVisible(!isNotebook);
    }

    private void exibirErros(ListaString erros) {
        StringBuilder sb = new StringBuilder("Foram encontrados os seguintes erros:\n\n");
        String[] mensagens = erros.listar();
        for (String msg : mensagens) {
            sb.append("- ").append(msg).append("\n");
        }
        JOptionPane.showMessageDialog(panelPrincipal, sb.toString(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
    }

    private void inicializarComponentes() {
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(20, 20, 80, 25);
        panelPrincipal.add(lblTipo);

        comboTipo = new JComboBox<>(new String[]{"Notebook", "Desktop"});
        comboTipo.setBounds(100, 20, 120, 25);
        panelPrincipal.add(comboTipo);

        JLabel lblSerial = new JLabel("Serial:");
        lblSerial.setBounds(240, 20, 80, 25);
        panelPrincipal.add(lblSerial);

        txtSerial = new JTextField();
        txtSerial.setBounds(300, 20, 150, 25);
        panelPrincipal.add(txtSerial);

        btnNovo = new JButton("Novo");
        btnNovo.setBounds(470, 20, 80, 25);
        panelPrincipal.add(btnNovo);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(560, 20, 80, 25);
        panelPrincipal.add(btnBuscar);

        JSeparator separator = new JSeparator();
        separator.setBounds(10, 60, 640, 2);
        panelPrincipal.add(separator);

        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setBounds(20, 80, 80, 25);
        panelPrincipal.add(lblDescricao);

        txtDescricao = new JTextArea();
        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
        scrollDescricao.setBounds(100, 80, 540, 60);
        panelPrincipal.add(scrollDescricao);

        JLabel lblEhNovo = new JLabel("É novo:");
        lblEhNovo.setBounds(20, 155, 80, 25);
        panelPrincipal.add(lblEhNovo);

        radioEhNovoSim = new JRadioButton("Sim");
        radioEhNovoNao = new JRadioButton("Não");
        radioEhNovoNao.setSelected(true);
        ButtonGroup groupEhNovo = new ButtonGroup();
        groupEhNovo.add(radioEhNovoSim);
        groupEhNovo.add(radioEhNovoNao);
        radioEhNovoNao.setBounds(100, 155, 60, 25);
        radioEhNovoSim.setBounds(170, 155, 60, 25);
        panelPrincipal.add(radioEhNovoNao);
        panelPrincipal.add(radioEhNovoSim);

        JLabel lblValor = new JLabel("Valor Est.:");
        lblValor.setBounds(20, 190, 80, 25);
        panelPrincipal.add(lblValor);

        NumberFormat format = new DecimalFormat("#,##0.00");
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Double.class);
        formatter.setAllowsInvalid(false);
        txtValorEstimado = new JFormattedTextField(formatter);
        txtValorEstimado.setValue(0.0);
        txtValorEstimado.setBounds(100, 190, 130, 25);
        panelPrincipal.add(txtValorEstimado);

        panelNotebook = new JPanel(null);
        panelNotebook.setBounds(10, 225, 400, 30);
        JLabel lblDadosSensiveis = new JLabel("Dados Sensíveis:");
        lblDadosSensiveis.setBounds(10, 0, 120, 25);
        radioDadosSensiveisSim = new JRadioButton("Sim");
        radioDadosSensiveisNao = new JRadioButton("Não");
        radioDadosSensiveisNao.setSelected(true);
        ButtonGroup groupSensiveis = new ButtonGroup();
        groupSensiveis.add(radioDadosSensiveisSim);
        groupSensiveis.add(radioDadosSensiveisNao);
        radioDadosSensiveisNao.setBounds(120, 0, 60, 25);
        radioDadosSensiveisSim.setBounds(190, 0, 60, 25);
        panelNotebook.add(lblDadosSensiveis);
        panelNotebook.add(radioDadosSensiveisNao);
        panelNotebook.add(radioDadosSensiveisSim);
        panelPrincipal.add(panelNotebook);

        panelDesktop = new JPanel(null);
        panelDesktop.setBounds(10, 225, 400, 30);
        JLabel lblEhServidor = new JLabel("É Servidor:");
        lblEhServidor.setBounds(10, 0, 120, 25);
        radioEhServidorSim = new JRadioButton("Sim");
        radioEhServidorNao = new JRadioButton("Não");
        radioEhServidorNao.setSelected(true);
        ButtonGroup groupServidor = new ButtonGroup();
        groupServidor.add(radioEhServidorSim);
        groupServidor.add(radioEhServidorNao);
        radioEhServidorNao.setBounds(120, 0, 60, 25);
        radioEhServidorSim.setBounds(190, 0, 60, 25);
        panelDesktop.add(lblEhServidor);
        panelDesktop.add(radioEhServidorNao);
        panelDesktop.add(radioEhServidorSim);
        panelPrincipal.add(panelDesktop);

        atualizarPaineisDinamicos();

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(140, 280, 90, 30);
        panelPrincipal.add(btnSalvar);

        btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(240, 280, 90, 30);
        panelPrincipal.add(btnExcluir);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(340, 280, 90, 30);
        panelPrincipal.add(btnCancelar);

        btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(440, 280, 90, 30);
        panelPrincipal.add(btnLimpar);

        panelPrincipal.setPreferredSize(new java.awt.Dimension(660, 330));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Cadastro de Equipamento");
        frame.setContentPane(new TelaEquipamento().panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}