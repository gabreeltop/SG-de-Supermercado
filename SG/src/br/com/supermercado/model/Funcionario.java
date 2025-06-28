package br.com.supermercado.model;

public class Funcionario {

    public enum Cargo {
        GERENTE,
        OPERADOR_DE_CAIXA
    }

    private int id;
    private String nome;
    private String login;
    private String senha;
    private Cargo cargo;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public Cargo getCargo() { return cargo; }
    public void setCargo(Cargo cargo) { this.cargo = cargo; }
}