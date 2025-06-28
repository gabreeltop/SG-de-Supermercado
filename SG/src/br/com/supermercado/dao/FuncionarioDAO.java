package br.com.supermercado.dao;

import br.com.supermercado.model.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionarioDAO {

    /**
     * @param login 
     * @return 
     */
    public Funcionario buscarPorLogin(String login) {
        String sql = "SELECT * FROM funcionarios WHERE login = ?";
        Funcionario funcionario = null;

        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                funcionario = new Funcionario();
                funcionario.setId(rs.getInt("id"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setLogin(rs.getString("login"));
                funcionario.setSenha(rs.getString("senha"));
                funcionario.setCargo(Funcionario.Cargo.valueOf(rs.getString("cargo")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funcionario;
    }
}