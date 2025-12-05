package br.edu.cs.poo.ac.ordem.telas;

import br.edu.cs.poo.ac.excecoes.ExcecaoNegocio;
import br.edu.cs.poo.ac.ordem.entidades.FechamentoOrdemServico;
import br.edu.cs.poo.ac.ordem.entidades.OrdemServico;
import br.edu.cs.poo.ac.ordem.entidades.PrecoBase;
import br.edu.cs.poo.ac.ordem.entidades.StatusOrdem;
import br.edu.cs.poo.ac.ordem.mediators.DadosOrdemServico;
import br.edu.cs.poo.ac.ordem.mediators.OrdemServicoMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;
import br.edu.cs.poo.ac.utils.ListaString;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TelaOrdemServico {

    private JPanel panelPrincipal;
    private JTabbedPane tabbedPane;

    // Aba Inclusão
    private JPanel panelInclusao;
    private JFormattedTextField txtCpfCnpjCliente;
    private JComboBox<PrecoBase> comboPrecoBase;
    private JTextField txtIdEquipamento;
    private JTextField txtVendedor;
    private JButton btnIncluir;
    private JButton btnLimparInclusao;
    private JLabel lblValorCalculado;
    private JLabel lblPrazoCalculado;

    // Aba Cancelamento
    private JPanel panelCancelamento;
    private JTextField txtNumeroCancelamento;
    private JTextArea txtMotivoCancelamento;
    private JFormattedTextField txtDataHoraCancelamento;
    private JButton btnCancelarOrdem;
    private JButton btnBuscarCancelamento;
    private JButton btnLimparCancelamento;
    private JLabel lblStatusCancelamento;
    private JLabel lblInfoOrdemCancelamento;

    // Aba Fechamento
    private JPanel panelFechamento;
    private JTextField txtNumeroFechamento;
    private JFormattedTextField txtDataFechamento;
    private JCheckBox chkPago;
    private JTextArea txtRelatorioFinal;
    private JButton btnFecharOrdem;
    private JButton btnBuscarFechamento;
    private JButton btnLimparFechamento;
    private JLabel lblStatusFechamento;
    private JLabel lblInfoOrdemFechamento;

    private OrdemServicoMediator mediator = OrdemServicoMediator.getInstancia();

    public TelaOrdemServico() {
        inicializarComponentes();
        configurarEventos();
    }

    private void inicializarComponentes() {
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(10, 10, 700, 500);
        panelPrincipal.add(tabbedPane);

        criarAbaInclusao();
        criarAbaCancelamento();
        criarAbaFechamento();

        panelPrincipal.setPreferredSize(new java.awt.Dimension(720, 520));
    }

    private void criarAbaInclusao() {
        panelInclusao = new JPanel();
        panelInclusao.setLayout(null);

        JLabel lblCpfCnpj = new JLabel("CPF/CNPJ do Cliente:");
        lblCpfCnpj.setBounds(20, 20, 150, 25);
        panelInclusao.add(lblCpfCnpj);

        MaskFormatter cpfCnpjFormatter = new MaskFormatter();
        cpfCnpjFormatter.setPlaceholderCharacter('_');
        txtCpfCnpjCliente = new JFormattedTextField(cpfCnpjFormatter);
        txtCpfCnpjCliente.setBounds(180, 20, 200, 25);
        panelInclusao.add(txtCpfCnpjCliente);

        txtCpfCnpjCliente.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                formatarCpfCnpj();
            }
        });

        JLabel lblPrecoBase = new JLabel("Preço Base:");
        lblPrecoBase.setBounds(20, 60, 150, 25);
        panelInclusao.add(lblPrecoBase);

        comboPrecoBase = new JComboBox<>(PrecoBase.values());
        comboPrecoBase.setBounds(180, 60, 300, 25);
        panelInclusao.add(comboPrecoBase);

        JLabel lblIdEquipamento = new JLabel("ID do Equipamento:");
        lblIdEquipamento.setBounds(20, 100, 150, 25);
        panelInclusao.add(lblIdEquipamento);

        txtIdEquipamento = new JTextField();
        txtIdEquipamento.setBounds(180, 100, 200, 25);
        panelInclusao.add(txtIdEquipamento);

        JLabel lblVendedor = new JLabel("Vendedor:");
        lblVendedor.setBounds(20, 140, 150, 25);
        panelInclusao.add(lblVendedor);

        txtVendedor = new JTextField();
        txtVendedor.setBounds(180, 140, 300, 25);
        panelInclusao.add(txtVendedor);

        lblValorCalculado = new JLabel("Valor: R$ 0,00");
        lblValorCalculado.setBounds(20, 180, 200, 25);
        panelInclusao.add(lblValorCalculado);

        lblPrazoCalculado = new JLabel("Prazo: 0 dias");
        lblPrazoCalculado.setBounds(240, 180, 200, 25);
        panelInclusao.add(lblPrazoCalculado);

        btnIncluir = new JButton("Incluir Ordem");
        btnIncluir.setBounds(180, 220, 150, 35);
        panelInclusao.add(btnIncluir);

        btnLimparInclusao = new JButton("Limpar");
        btnLimparInclusao.setBounds(340, 220, 100, 35);
        panelInclusao.add(btnLimparInclusao);

        tabbedPane.addTab("Inclusão", panelInclusao);
    }

    private void criarAbaCancelamento() {
        panelCancelamento = new JPanel();
        panelCancelamento.setLayout(null);

        JLabel lblNumero = new JLabel("Número da Ordem:");
        lblNumero.setBounds(20, 20, 150, 25);
        panelCancelamento.add(lblNumero);

        txtNumeroCancelamento = new JTextField();
        txtNumeroCancelamento.setBounds(180, 20, 300, 25);
        panelCancelamento.add(txtNumeroCancelamento);

        btnBuscarCancelamento = new JButton("Buscar");
        btnBuscarCancelamento.setBounds(490, 20, 100, 25);
        panelCancelamento.add(btnBuscarCancelamento);

        lblStatusCancelamento = new JLabel("Status: -");
        lblStatusCancelamento.setBounds(20, 55, 300, 25);
        panelCancelamento.add(lblStatusCancelamento);

        lblInfoOrdemCancelamento = new JLabel("");
        lblInfoOrdemCancelamento.setBounds(20, 80, 600, 50);
        lblInfoOrdemCancelamento.setVerticalAlignment(SwingConstants.TOP);
        panelCancelamento.add(lblInfoOrdemCancelamento);

        JSeparator separator1 = new JSeparator();
        separator1.setBounds(10, 140, 680, 2);
        panelCancelamento.add(separator1);

        JLabel lblMotivo = new JLabel("Motivo do Cancelamento:");
        lblMotivo.setBounds(20, 155, 200, 25);
        panelCancelamento.add(lblMotivo);

        txtMotivoCancelamento = new JTextArea();
        txtMotivoCancelamento.setLineWrap(true);
        txtMotivoCancelamento.setWrapStyleWord(true);
        JScrollPane scrollMotivo = new JScrollPane(txtMotivoCancelamento);
        scrollMotivo.setBounds(20, 185, 650, 100);
        panelCancelamento.add(scrollMotivo);

        JLabel lblDataHora = new JLabel("Data/Hora Cancelamento:");
        lblDataHora.setBounds(20, 300, 200, 25);
        panelCancelamento.add(lblDataHora);

        try {
            MaskFormatter dateTimeFormatter = new MaskFormatter("##/##/#### ##:##");
            dateTimeFormatter.setPlaceholderCharacter('_');
            txtDataHoraCancelamento = new JFormattedTextField(dateTimeFormatter);
        } catch (ParseException e) {
            txtDataHoraCancelamento = new JFormattedTextField();
        }
        txtDataHoraCancelamento.setBounds(220, 300, 150, 25);
        panelCancelamento.add(txtDataHoraCancelamento);

        JButton btnDataHoraAtual = new JButton("Agora");
        btnDataHoraAtual.setBounds(380, 300, 80, 25);
        btnDataHoraAtual.addActionListener(e -> {
            LocalDateTime agora = LocalDateTime.now();
            txtDataHoraCancelamento.setText(agora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        });
        panelCancelamento.add(btnDataHoraAtual);

        btnCancelarOrdem = new JButton("Cancelar Ordem");
        btnCancelarOrdem.setBounds(180, 340, 150, 35);
        panelCancelamento.add(btnCancelarOrdem);

        btnLimparCancelamento = new JButton("Limpar");
        btnLimparCancelamento.setBounds(340, 340, 100, 35);
        panelCancelamento.add(btnLimparCancelamento);

        tabbedPane.addTab("Cancelamento", panelCancelamento);
    }

    private void criarAbaFechamento() {
        panelFechamento = new JPanel();
        panelFechamento.setLayout(null);

        JLabel lblNumero = new JLabel("Número da Ordem:");
        lblNumero.setBounds(20, 20, 150, 25);
        panelFechamento.add(lblNumero);

        txtNumeroFechamento = new JTextField();
        txtNumeroFechamento.setBounds(180, 20, 300, 25);
        panelFechamento.add(txtNumeroFechamento);

        btnBuscarFechamento = new JButton("Buscar");
        btnBuscarFechamento.setBounds(490, 20, 100, 25);
        panelFechamento.add(btnBuscarFechamento);

        lblStatusFechamento = new JLabel("Status: -");
        lblStatusFechamento.setBounds(20, 55, 300, 25);
        panelFechamento.add(lblStatusFechamento);

        lblInfoOrdemFechamento = new JLabel("");
        lblInfoOrdemFechamento.setBounds(20, 80, 600, 50);
        lblInfoOrdemFechamento.setVerticalAlignment(SwingConstants.TOP);
        panelFechamento.add(lblInfoOrdemFechamento);

        JSeparator separator1 = new JSeparator();
        separator1.setBounds(10, 140, 680, 2);
        panelFechamento.add(separator1);

        JLabel lblDataFechamento = new JLabel("Data de Fechamento:");
        lblDataFechamento.setBounds(20, 155, 200, 25);
        panelFechamento.add(lblDataFechamento);

        try {
            MaskFormatter dateFormatter = new MaskFormatter("##/##/####");
            dateFormatter.setPlaceholderCharacter('_');
            txtDataFechamento = new JFormattedTextField(dateFormatter);
        } catch (ParseException e) {
            txtDataFechamento = new JFormattedTextField();
        }
        txtDataFechamento.setBounds(220, 155, 100, 25);
        panelFechamento.add(txtDataFechamento);

        JButton btnDataAtual = new JButton("Hoje");
        btnDataAtual.setBounds(330, 155, 80, 25);
        btnDataAtual.addActionListener(e -> {
            txtDataFechamento.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });
        panelFechamento.add(btnDataAtual);

        chkPago = new JCheckBox("Ordem foi paga");
        chkPago.setBounds(20, 190, 200, 25);
        panelFechamento.add(chkPago);

        JLabel lblRelatorio = new JLabel("Relatório Final:");
        lblRelatorio.setBounds(20, 225, 200, 25);
        panelFechamento.add(lblRelatorio);

        txtRelatorioFinal = new JTextArea();
        txtRelatorioFinal.setLineWrap(true);
        txtRelatorioFinal.setWrapStyleWord(true);
        JScrollPane scrollRelatorio = new JScrollPane(txtRelatorioFinal);
        scrollRelatorio.setBounds(20, 255, 650, 120);
        panelFechamento.add(scrollRelatorio);

        btnFecharOrdem = new JButton("Fechar Ordem");
        btnFecharOrdem.setBounds(180, 390, 150, 35);
        panelFechamento.add(btnFecharOrdem);

        btnLimparFechamento = new JButton("Limpar");
        btnLimparFechamento.setBounds(340, 390, 100, 35);
        panelFechamento.add(btnLimparFechamento);

        tabbedPane.addTab("Fechamento", panelFechamento);
    }

    private void configurarEventos() {
        // Eventos da aba Inclusão
        btnIncluir.addActionListener(e -> incluirOrdem());
        btnLimparInclusao.addActionListener(e -> limparInclusao());

        // Eventos da aba Cancelamento
        btnBuscarCancelamento.addActionListener(e -> buscarOrdemParaCancelamento());
        btnCancelarOrdem.addActionListener(e -> cancelarOrdem());
        btnLimparCancelamento.addActionListener(e -> limparCancelamento());

        // Eventos da aba Fechamento
        btnBuscarFechamento.addActionListener(e -> buscarOrdemParaFechamento());
        btnFecharOrdem.addActionListener(e -> fecharOrdem());
        btnLimparFechamento.addActionListener(e -> limparFechamento());
    }

    private void incluirOrdem() {
        try {
            String cpfCnpj = txtCpfCnpjCliente.getText().replaceAll("[^0-9]", "");
            PrecoBase precoBase = (PrecoBase) comboPrecoBase.getSelectedItem();
            String idEquipamento = txtIdEquipamento.getText().trim();
            String vendedor = txtVendedor.getText().trim();

            DadosOrdemServico dados = new DadosOrdemServico(cpfCnpj, precoBase.getCodigo(), idEquipamento, vendedor);
            mediator.incluir(dados);

            JOptionPane.showMessageDialog(panelPrincipal, "Ordem de serviço incluída com sucesso!", 
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparInclusao();
        } catch (ExcecaoNegocio ex) {
            exibirErros(ex.getRes().getMensagensErro());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panelPrincipal, 
                    "Erro ao incluir ordem: " + ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarOrdemParaCancelamento() {
        String numero = txtNumeroCancelamento.getText().trim();
        if (numero.isEmpty()) {
            JOptionPane.showMessageDialog(panelPrincipal, "Número da ordem deve ser informado!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        OrdemServico ordem = mediator.buscar(numero);
        if (ordem == null) {
            JOptionPane.showMessageDialog(panelPrincipal, "Ordem não encontrada!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            limparInfoCancelamento();
            return;
        }

        exibirInfoOrdemCancelamento(ordem);
    }

    private void cancelarOrdem() {
        try {
            String numero = txtNumeroCancelamento.getText().trim();
            String motivo = txtMotivoCancelamento.getText().trim();
            String dataHoraStr = txtDataHoraCancelamento.getText().trim();

            if (dataHoraStr.isEmpty() || dataHoraStr.contains("_")) {
                JOptionPane.showMessageDialog(panelPrincipal, 
                        "Data/hora de cancelamento deve ser informada!", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDateTime dataHoraCancelamento;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                dataHoraCancelamento = LocalDateTime.parse(dataHoraStr, formatter);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(panelPrincipal, 
                        "Formato de data/hora inválido! Use dd/MM/yyyy HH:mm", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            mediator.cancelar(numero, motivo, dataHoraCancelamento);

            JOptionPane.showMessageDialog(panelPrincipal, "Ordem cancelada com sucesso!", 
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCancelamento();
        } catch (ExcecaoNegocio ex) {
            exibirErros(ex.getRes().getMensagensErro());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panelPrincipal, 
                    "Erro ao cancelar ordem: " + ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarOrdemParaFechamento() {
        String numero = txtNumeroFechamento.getText().trim();
        if (numero.isEmpty()) {
            JOptionPane.showMessageDialog(panelPrincipal, "Número da ordem deve ser informado!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        OrdemServico ordem = mediator.buscar(numero);
        if (ordem == null) {
            JOptionPane.showMessageDialog(panelPrincipal, "Ordem não encontrada!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            limparInfoFechamento();
            return;
        }

        exibirInfoOrdemFechamento(ordem);
    }

    private void fecharOrdem() {
        try {
            String numero = txtNumeroFechamento.getText().trim();
            String dataStr = txtDataFechamento.getText().trim();
            boolean pago = chkPago.isSelected();
            String relatorio = txtRelatorioFinal.getText().trim();

            if (dataStr.isEmpty() || dataStr.contains("_")) {
                JOptionPane.showMessageDialog(panelPrincipal, 
                        "Data de fechamento deve ser informada!", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate dataFechamento;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                dataFechamento = LocalDate.parse(dataStr, formatter);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(panelPrincipal, 
                        "Formato de data inválido! Use dd/MM/yyyy", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            FechamentoOrdemServico fechamento = new FechamentoOrdemServico(
                    numero, dataFechamento, pago, relatorio);
            mediator.fechar(fechamento);

            JOptionPane.showMessageDialog(panelPrincipal, "Ordem fechada com sucesso!", 
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparFechamento();
        } catch (ExcecaoNegocio ex) {
            exibirErros(ex.getRes().getMensagensErro());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panelPrincipal, 
                    "Erro ao fechar ordem: " + ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exibirInfoOrdemCancelamento(OrdemServico ordem) {
        lblStatusCancelamento.setText("Status: " + ordem.getStatus().getDescricao());
        
        StringBuilder info = new StringBuilder("<html>");
        info.append("<b>Cliente:</b> ").append(ordem.getCliente().getNome()).append("<br>");
        info.append("<b>CPF/CNPJ:</b> ").append(ordem.getCliente().getCpfCnpj()).append("<br>");
        info.append("<b>Equipamento:</b> ").append(ordem.getEquipamento().getDescricao()).append("<br>");
        info.append("<b>Preço Base:</b> ").append(ordem.getPrecoBase().getDescricao()).append("<br>");
        info.append("<b>Valor:</b> R$ ").append(String.format("%.2f", ordem.getValor())).append("<br>");
        info.append("<b>Data Abertura:</b> ").append(ordem.getDataHoraAbertura()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("<br>");
        info.append("<b>Prazo:</b> ").append(ordem.getPrazoEmDias()).append(" dias");
        info.append("</html>");
        
        lblInfoOrdemCancelamento.setText(info.toString());
    }

    private void exibirInfoOrdemFechamento(OrdemServico ordem) {
        lblStatusFechamento.setText("Status: " + ordem.getStatus().getDescricao());
        
        StringBuilder info = new StringBuilder("<html>");
        info.append("<b>Cliente:</b> ").append(ordem.getCliente().getNome()).append("<br>");
        info.append("<b>CPF/CNPJ:</b> ").append(ordem.getCliente().getCpfCnpj()).append("<br>");
        info.append("<b>Equipamento:</b> ").append(ordem.getEquipamento().getDescricao()).append("<br>");
        info.append("<b>Preço Base:</b> ").append(ordem.getPrecoBase().getDescricao()).append("<br>");
        info.append("<b>Valor:</b> R$ ").append(String.format("%.2f", ordem.getValor())).append("<br>");
        info.append("<b>Data Abertura:</b> ").append(ordem.getDataHoraAbertura()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("<br>");
        info.append("<b>Prazo:</b> ").append(ordem.getPrazoEmDias()).append(" dias");
        info.append("</html>");
        
        lblInfoOrdemFechamento.setText(info.toString());
    }

    private void limparInclusao() {
        txtCpfCnpjCliente.setText("");
        comboPrecoBase.setSelectedIndex(0);
        txtIdEquipamento.setText("");
        txtVendedor.setText("");
        lblValorCalculado.setText("Valor: R$ 0,00");
        lblPrazoCalculado.setText("Prazo: 0 dias");
    }

    private void limparCancelamento() {
        txtNumeroCancelamento.setText("");
        txtMotivoCancelamento.setText("");
        txtDataHoraCancelamento.setText("");
        limparInfoCancelamento();
    }

    private void limparInfoCancelamento() {
        lblStatusCancelamento.setText("Status: -");
        lblInfoOrdemCancelamento.setText("");
    }

    private void limparFechamento() {
        txtNumeroFechamento.setText("");
        txtDataFechamento.setText("");
        chkPago.setSelected(false);
        txtRelatorioFinal.setText("");
        limparInfoFechamento();
    }

    private void limparInfoFechamento() {
        lblStatusFechamento.setText("Status: -");
        lblInfoOrdemFechamento.setText("");
    }

    private void formatarCpfCnpj() {
        String texto = txtCpfCnpjCliente.getText().replaceAll("[^0-9]", "");
        try {
            MaskFormatter formatter = null;
            if (texto.length() == 11) {
                formatter = new MaskFormatter("###.###.###-##");
            } else if (texto.length() == 14) {
                formatter = new MaskFormatter("##.###.###/####-##");
            } else {
                return;
            }
            JFormattedTextField tempField = new JFormattedTextField(formatter);
            tempField.setText(texto);
            txtCpfCnpjCliente.setText(tempField.getText());
        } catch (ParseException ex) {
            // Ignorar
        }
    }

    private void exibirErros(ListaString erros) {
        StringBuilder sb = new StringBuilder("Foram encontrados os seguintes erros:\n\n");
        String[] mensagens = erros.listar();
        for (String msg : mensagens) {
            sb.append("- ").append(msg).append("\n");
        }
        JOptionPane.showMessageDialog(panelPrincipal, sb.toString(), 
                "Erro de Validação", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Gestão de Ordens de Serviço");
        frame.setContentPane(new TelaOrdemServico().panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

