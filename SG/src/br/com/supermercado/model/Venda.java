package br.com.supermercado.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venda {
    private int id;
    private LocalDateTime dataVenda;
    private double valorTotal;
    private List<ItemVenda> itens = new ArrayList<>();
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDateTime getDataVenda() { return dataVenda; }
    public void setDataVenda(LocalDateTime dataVenda) { this.dataVenda = dataVenda; }
    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }
    public List<ItemVenda> getItens() { return itens; }
    public void setItens(List<ItemVenda> itens) { this.itens = itens; }

    public void adicionarItem(ItemVenda item) {
        this.itens.add(item);
        this.valorTotal += item.getPrecoUnitario() * item.getQuantidade();
    }
}