package br.com.supermercado.service;

import br.com.supermercado.dao.FuncionarioDAO;
import br.com.supermercado.model.Funcionario;

public class FuncionarioService {
    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    /**
     * @param login
     * @param senha
     * @return 
     */
    public Funcionario autenticar(String login, String senha) {
        Funcionario funcionario = funcionarioDAO.buscarPorLogin(login);

        if (funcionario != null && funcionario.getSenha().equals(senha)) {
            
            return funcionario;
        }

        return null;
    }
}