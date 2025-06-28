package br.com.supermercado.dao;

import br.com.supermercado.model.ItemVenda;
import br.com.supermercado.model.Venda;
import java.sql.*;

public class VendaDAO {
    public void salvar(Venda venda) throws SQLException {
        String sqlVenda = "INSERT INTO vendas (data_venda, valor_total) VALUES (?, ?)";
        String sqlItemVenda = "INSERT INTO itens_venda (id_venda, id_produto, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        
        Connection conn = null;
        try {
            conn = ConexaoMySQL.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement pstmtVenda = conn.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS)) {
                pstmtVenda.setTimestamp(1, Timestamp.valueOf(venda.getDataVenda()));
                pstmtVenda.setDouble(2, venda.getValorTotal());
                pstmtVenda.executeUpdate();

                try (ResultSet generatedKeys = pstmtVenda.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idVenda = generatedKeys.getInt(1);

                        for (ItemVenda item : venda.getItens()) {
                            try (PreparedStatement pstmtItem = conn.prepareStatement(sqlItemVenda)) {
                                pstmtItem.setInt(1, idVenda);
                                pstmtItem.setInt(2, item.getProduto().getId());
                                pstmtItem.setInt(3, item.getQuantidade());
                                pstmtItem.setDouble(4, item.getPrecoUnitario());
                                pstmtItem.executeUpdate();
                            }
                        }
                    }
                }
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback(); 
            e.printStackTrace();
            throw e; 
        } finally {
            if (conn != null) conn.close();
        }
    }
}