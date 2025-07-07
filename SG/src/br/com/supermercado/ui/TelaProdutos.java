package br.com.supermercado.ui;

import br.com.supermercado.model.Produto;
import br.com.supermercado.service.ProdutoService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaProdutos extends JFrame {
    private ProdutoService produtoService;
    private JTable tabelaProdutos;
    private DefaultTableModel tableModel;
    private JTextField txtNome, txtPreco, txtEstoque, txtId;
    private JButton btnSalvar, btnExcluir, btnLimpar;

    public TelaProdutos() {
        this.produtoService = new ProdutoService();

        setTitle("Gerenciamento de Produtos - Supermercado");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados do Produto"));

        txtId = new JTextField();
        txtId.setEditable(false);
        txtNome = new JTextField();
        txtPreco = new JTextField();
        txtEstoque = new JTextField();

        formPanel.add(new JLabel("ID:"));
        formPanel.add(txtId);
        formPanel.add(new JLabel("Nome do Produto:"));
        formPanel.add(txtNome);
        formPanel.add(new JLabel("Preço:"));
        formPanel.add(txtPreco);
        formPanel.add(new JLabel("Quantidade em Estoque:"));
        formPanel.add(txtEstoque);

        btnSalvar = new JButton("Salvar");
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Limpar Campos");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnLimpar);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Preço", "Estoque"}, 0);
        tabelaProdutos = new JTable(tableModel);

        tabelaProdutos.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tabelaProdutos.getSelectedRow() != -1) {
                preencherFormularioComLinhaSelecionada();
            }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(10, 10));
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(new JScrollPane(tabelaProdutos), BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> salvarProduto());
        btnExcluir.addActionListener(e -> excluirProduto());
        btnLimpar.addActionListener(e -> limparCampos());

        atualizarTabela();
    }

    private void preencherFormularioComLinhaSelecionada() {
        int linhaSelecionada = tabelaProdutos.getSelectedRow();
        txtId.setText(tableModel.getValueAt(linhaSelecionada, 0).toString());
        txtNome.setText(tableModel.getValueAt(linhaSelecionada, 1).toString());
        txtPreco.setText(tableModel.getValueAt(linhaSelecionada, 2).toString());
        txtEstoque.setText(tableModel.getValueAt(linhaSelecionada, 3).toString());
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa a tabela
        List<Produto> produtos = produtoService.listarTodos();
        for (Produto produto : produtos) {
            tableModel.addRow(new Object[]{
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getQuantidadeEstoque()
            });
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtPreco.setText("");
        txtEstoque.setText("");
        tabelaProdutos.clearSelection();
    }

    private void salvarProduto() {
        if (txtNome.getText().trim().isEmpty() || txtPreco.getText().trim().isEmpty() || txtEstoque.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos, exceto ID, são obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Produto produto = new Produto();
            produto.setNome(txtNome.getText());
            produto.setPreco(Double.parseDouble(txtPreco.getText()));
            produto.setQuantidadeEstoque(Integer.parseInt(txtEstoque.getText()));

            if (!txtId.getText().isEmpty()) {
                produto.setId(Integer.parseInt(txtId.getText()));
            }

            produtoService.salvarOuAtualizar(produto);
            JOptionPane.showMessageDialog(this, "Produto salvo com sucesso!");
            limparCampos();
            atualizarTabela();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preço e Estoque devem ser números válidos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirProduto() {
        if (tabelaProdutos.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto da tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este produto?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText());
            produtoService.excluir(id);
            JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!");
            limparCampos();
            atualizarTabela();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaProdutos tela = new TelaProdutos();
            tela.setVisible(true);
        });
    }
}