package br.com.supermercado.service;

import br.com.supermercado.dao.ProdutoDAO;
import br.com.supermercado.dao.VendaDAO;
import br.com.supermercado.model.ItemVenda;
import br.com.supermercado.model.Venda;
import java.sql.SQLException;

public class VendaService {
    private VendaDAO vendaDAO = new VendaDAO();
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    public void registrarVenda(Venda venda) throws SQLException {
        vendaDAO.salvar(venda);


        for (ItemVenda item : venda.getItens()) {
            produtoDAO.atualizarEstoque(item.getProduto().getId(), item.getQuantidade());
        }
    }
}