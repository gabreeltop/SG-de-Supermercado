package br.com.supermercado.ui;

import br.com.supermercado.model.Funcionario;
import br.com.supermercado.service.FuncionarioService;
import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {
    private FuncionarioService funcionarioService = new FuncionarioService();
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnEntrar;

    public TelaLogin() {
        setTitle("Login - Sistema de Supermercado");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        txtLogin = new JTextField();
        txtSenha = new JPasswordField();
        formPanel.add(new JLabel("Login:"));
        formPanel.add(txtLogin);
        formPanel.add(new JLabel("Senha:"));
        formPanel.add(txtSenha);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnEntrar = new JButton("Entrar");
        buttonPanel.add(btnEntrar);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        btnEntrar.addActionListener(e -> realizarLogin());
    }

    private void realizarLogin() {
        String login = txtLogin.getText();
        String senha = new String(txtSenha.getPassword());

        Funcionario funcionario = funcionarioService.autenticar(login, senha);

        if (funcionario != null) {
            this.dispose();

            if (funcionario.getCargo() == Funcionario.Cargo.GERENTE) {
                SwingUtilities.invokeLater(() -> new TelaProdutos().setVisible(true));
            } else if (funcionario.getCargo() == Funcionario.Cargo.OPERADOR_DE_CAIXA) {
                SwingUtilities.invokeLater(() -> new TelaPDV().setVisible(true));
            }
        } else {
            JOptionPane.showMessageDialog(this, "Login ou senha inválidos.", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
        }
    }
}