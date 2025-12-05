package br.edu.cs.poo.ac.ordem.telas;

import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Contato;
import br.edu.cs.poo.ac.ordem.mediators.ClienteMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;
import br.edu.cs.poo.ac.utils.ListaString;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TelaCliente {

    private JPanel panelPrincipal;
    private JFormattedTextField txtCpfCnpj;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtCelular;
    private JCheckBox chkEhZap;
    private JFormattedTextField txtDataCadastro;
    private JButton btnNovo;
    private JButton btnBuscar;
    private JButton btnSalvar;
    private JButton btnExcluir;
    private JButton btnCancelar;
    private JButton btnLimpar;

    private ClienteMediator mediator = ClienteMediator.getInstancia();

    private enum EstadoTela {
        INICIAL,
        CRIANDO,
        EDITANDO
    }

    public TelaCliente() {
        inicializarComponentes();
        gerenciarEstadoTela(EstadoTela.INICIAL);

        btnNovo.addActionListener(e -> {
            ResultadoMediator res = mediator.validar(new Cliente(txtCpfCnpj.getText(), "", null, null));
            if (res.getMensagensErro().tamanho() > 0 && res.getMensagensErro().buscar(0).contains("CPF/CNPJ")) {
                JOptionPane.showMessageDialog(panelPrincipal, "CPF/CNPJ deve ser preenchido!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Cliente clienteExistente = mediator.buscar(txtCpfCnpj.getText().replaceAll("[^0-9]", ""));
            if (clienteExistente != null) {
                JOptionPane.showMessageDialog(panelPrincipal, "Cliente já existente!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                gerenciarEstadoTela(EstadoTela.CRIANDO);
            }
        });

        btnBuscar.addActionListener(e -> {
            if (txtCpfCnpj.getText().replaceAll("[^0-9]", "").isEmpty()) {
                JOptionPane.showMessageDialog(panelPrincipal, "CPF/CNPJ deve ser preenchido!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Cliente cliente = mediator.buscar(txtCpfCnpj.getText().replaceAll("[^0-9]", ""));
            if (cliente == null) {
                JOptionPane.showMessageDialog(panelPrincipal, "Cliente não existente!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                preencherCampos(cliente);
                gerenciarEstadoTela(EstadoTela.EDITANDO);
            }
        });

        btnSalvar.addActionListener(e -> {
            Cliente cliente = construirClienteDosCampos();
            ResultadoMediator res;
            String msgSucesso;

            if (btnSalvar.getText().equals("Incluir")) {
                res = mediator.incluir(cliente);
                msgSucesso = "Inclusão realizada com sucesso";
            } else {
                res = mediator.alterar(cliente);
                msgSucesso = "Alteração realizada com sucesso";
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
                ResultadoMediator res = mediator.excluir(txtCpfCnpj.getText().replaceAll("[^0-9]", ""));
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

        txtCpfCnpj.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                formatarCpfCnpj();
            }
        });
    }

    private void gerenciarEstadoTela(EstadoTela estado) {
        switch (estado) {
            case INICIAL:
                txtCpfCnpj.setEnabled(true);
                btnNovo.setEnabled(true);
                btnBuscar.setEnabled(true);

                txtNome.setEnabled(false);
                txtEmail.setEnabled(false);
                txtCelular.setEnabled(false);
                chkEhZap.setEnabled(false);
                txtDataCadastro.setEnabled(false);
                btnSalvar.setEnabled(false);
                btnSalvar.setText("Salvar");
                btnExcluir.setEnabled(false);
                btnCancelar.setEnabled(false);
                break;
            case CRIANDO:
                txtCpfCnpj.setEnabled(false);
                btnNovo.setEnabled(false);
                btnBuscar.setEnabled(false);
                txtDataCadastro.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                txtNome.setEnabled(true);
                txtEmail.setEnabled(true);
                txtCelular.setEnabled(true);
                chkEhZap.setEnabled(true);
                btnSalvar.setEnabled(true);
                btnSalvar.setText("Incluir");
                btnExcluir.setEnabled(false);
                btnCancelar.setEnabled(true);
                break;
            case EDITANDO:
                txtCpfCnpj.setEnabled(false);
                btnNovo.setEnabled(false);
                btnBuscar.setEnabled(false);

                txtNome.setEnabled(true);
                txtEmail.setEnabled(true);
                txtCelular.setEnabled(true);
                chkEhZap.setEnabled(true);
                txtDataCadastro.setEnabled(false);
                btnSalvar.setEnabled(true);
                btnSalvar.setText("Alterar");
                btnExcluir.setEnabled(true);
                btnCancelar.setEnabled(true);
                break;
        }
    }

    private void limparCampos(boolean limparCpf) {
        if (limparCpf) {
            txtCpfCnpj.setValue(null);
            txtCpfCnpj.setText("");
        }
        txtNome.setText("");
        txtEmail.setText("");
        txtCelular.setText("");
        chkEhZap.setSelected(false);
        txtDataCadastro.setValue(null);
        txtDataCadastro.setText("");
    }

    private Cliente construirClienteDosCampos() {
        String cpfCnpj = txtCpfCnpj.getText().replaceAll("[^0-9]", "");
        Contato contato = new Contato(txtEmail.getText(), txtCelular.getText(), chkEhZap.isSelected());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataCadastro = null;
        try {
            dataCadastro = LocalDate.parse(txtDataCadastro.getText(), formatter);
        } catch(Exception e) {
            if (btnSalvar.getText().equals("Incluir")) {
                dataCadastro = LocalDate.now();
            }
        }
        return new Cliente(cpfCnpj, txtNome.getText(), contato, dataCadastro);
    }

    private void preencherCampos(Cliente cliente) {
        txtNome.setText(cliente.getNome());
        txtEmail.setText(cliente.getContato().getEmail());
        txtCelular.setText(cliente.getContato().getCelular());
        chkEhZap.setSelected(cliente.getContato().isEhZap());
        txtDataCadastro.setText(cliente.getDataCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    private void formatarCpfCnpj() {
        String texto = txtCpfCnpj.getText().replaceAll("[^0-9]", "");
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
            txtCpfCnpj.setText(tempField.getText());
        } catch (ParseException ex) {
        }
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

        JLabel lblCpfCnpj = new JLabel("CPF/CNPJ:");
        lblCpfCnpj.setBounds(20, 20, 100, 25);
        panelPrincipal.add(lblCpfCnpj);

        txtCpfCnpj = new JFormattedTextField();
        txtCpfCnpj.setBounds(130, 20, 150, 25);
        panelPrincipal.add(txtCpfCnpj);

        btnNovo = new JButton("Novo");
        btnNovo.setBounds(290, 20, 80, 25);
        panelPrincipal.add(btnNovo);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(380, 20, 80, 25);
        panelPrincipal.add(btnBuscar);

        JSeparator separator = new JSeparator();
        separator.setBounds(10, 60, 460, 2);
        panelPrincipal.add(separator);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 80, 100, 25);
        panelPrincipal.add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(130, 80, 330, 25);
        panelPrincipal.add(txtNome);

        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setBounds(20, 115, 100, 25);
        panelPrincipal.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(130, 115, 330, 25);
        panelPrincipal.add(txtEmail);

        JLabel lblCelular = new JLabel("Celular:");
        lblCelular.setBounds(20, 150, 100, 25);
        panelPrincipal.add(lblCelular);

        txtCelular = new JTextField();
        txtCelular.setBounds(130, 150, 150, 25);
        panelPrincipal.add(txtCelular);

        chkEhZap = new JCheckBox("É WhatsApp");
        chkEhZap.setBounds(290, 150, 150, 25);
        panelPrincipal.add(chkEhZap);

        JLabel lblDataCadastro = new JLabel("Data de Cadastro:");
        lblDataCadastro.setBounds(20, 185, 120, 25);
        panelPrincipal.add(lblDataCadastro);

        try {
            MaskFormatter dateFormatter = new MaskFormatter("##/##/####");
            txtDataCadastro = new JFormattedTextField(dateFormatter);
        } catch (ParseException e) { e.printStackTrace(); }
        txtDataCadastro.setBounds(130, 185, 100, 25);
        panelPrincipal.add(txtDataCadastro);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(40, 240, 90, 30);
        panelPrincipal.add(btnSalvar);

        btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(140, 240, 90, 30);
        panelPrincipal.add(btnExcluir);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(240, 240, 90, 30);
        panelPrincipal.add(btnCancelar);

        btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(340, 240, 90, 30);
        panelPrincipal.add(btnLimpar);

        panelPrincipal.setPreferredSize(new java.awt.Dimension(480, 290));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Cadastro de Cliente");
        frame.setContentPane(new TelaCliente().panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}