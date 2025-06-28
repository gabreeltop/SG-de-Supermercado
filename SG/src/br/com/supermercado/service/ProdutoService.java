package br.com.supermercado.service;

import br.com.supermercado.dao.ProdutoDAO;
import br.com.supermercado.model.Produto;
import java.util.List;

public class ProdutoService {
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    public void salvarOuAtualizar(Produto produto) {
        if (produto.getId() == 0) {
            produtoDAO.salvar(produto);
        } else {
            produtoDAO.atualizar(produto);
        }
    }

    public void excluir(int id) {
        produtoDAO.excluir(id);
    }

    public List<Produto> listarTodos() {
        return produtoDAO.listarTodos();
    }

    // MÉTODO ADICIONADO:
    /**
     * Busca um produto pelo seu ID.
     * Este método é essencial para a tela de Ponto de Venda.
     * @param id O ID do produto a ser buscado.
     * @return O objeto Produto se encontrado, ou null caso contrário.
     */
    public Produto buscarPorId(int id) {
        return produtoDAO.buscarPorId(id);
    }
}