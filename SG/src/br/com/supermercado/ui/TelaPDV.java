package br.com.supermercado.ui;

import br.com.supermercado.model.ItemVenda;
import br.com.supermercado.model.Produto;
import br.com.supermercado.model.Venda;
import br.com.supermercado.service.ProdutoService;
import br.com.supermercado.service.VendaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TelaPDV extends JFrame {
    private Venda vendaAtual;
    private ProdutoService produtoService = new ProdutoService();
    private VendaService vendaService = new VendaService();
    
    private DefaultTableModel tableModel;
    private JTable tabelaItens;
    private JTextField txtCodProduto;
    private JLabel lblTotal;

    public TelaPDV() {
        setTitle("PDV - Ponto de Venda");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        iniciarNovaVenda();
        
        // Layout
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Código do Produto:"));
        txtCodProduto = new JTextField(10);
        JButton btnAdicionar = new JButton("Adicionar");
        topPanel.add(txtCodProduto);
        topPanel.add(btnAdicionar);

        tableModel = new DefaultTableModel(new Object[]{"Cód.", "Produto", "Qtd.", "Preço Unit.", "Subtotal"}, 0);
        tabelaItens = new JTable(tableModel);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        lblTotal = new JLabel("TOTAL: R$ 0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 24));
        bottomPanel.add(lblTotal, BorderLayout.WEST);

        JButton btnFinalizar = new JButton("Finalizar Venda");
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 18));
        bottomPanel.add(btnFinalizar, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(tabelaItens), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Ações
        btnAdicionar.addActionListener(e -> adicionarProduto());
        btnFinalizar.addActionListener(e -> finalizarVenda());
    }

    private void iniciarNovaVenda() {
        vendaAtual = new Venda();
        vendaAtual.setDataVenda(LocalDateTime.now());
        if (tableModel != null) {
            tableModel.setRowCount(0);
            atualizarTotal();
        }
    }

    private void adicionarProduto() {
        try {
            int codProduto = Integer.parseInt(txtCodProduto.getText());
            Produto produto = produtoService.buscarPorId(codProduto);

            if (produto != null) {
                if(produto.getQuantidadeEstoque() > 0) {
                    ItemVenda item = new ItemVenda();
                    item.setProduto(produto);
                    item.setQuantidade(1);
                    item.setPrecoUnitario(produto.getPreco());

                    vendaAtual.adicionarItem(item);
                    atualizarTabela();
                    atualizarTotal();
                } else {
                    JOptionPane.showMessageDialog(this, "Produto sem estoque!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Produto não encontrado!");
            }
            txtCodProduto.setText("");
            txtCodProduto.requestFocus();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código do produto inválido.");
        }
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        for(ItemVenda item : vendaAtual.getItens()) {
            tableModel.addRow(new Object[]{
                item.getProduto().getId(),
                item.getProduto().getNome(),
                item.getQuantidade(),
                String.format("%.2f", item.getPrecoUnitario()),
                String.format("%.2f", item.getQuantidade() * item.getPrecoUnitario())
            });
        }
    }
    
    private void atualizarTotal() {
        lblTotal.setText(String.format("TOTAL: R$ %.2f", vendaAtual.getValorTotal()));
    }
    
    private void finalizarVenda() {
        if (vendaAtual.getItens().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum item na venda.");
            return;
        }
        
        try {
            vendaService.registrarVenda(vendaAtual);
            JOptionPane.showMessageDialog(this, "Venda registrada com sucesso!");
            iniciarNovaVenda();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar a venda: " + e.getMessage());
            e.printStackTrace();
        }
    }
}