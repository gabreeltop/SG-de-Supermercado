-- Cria o banco de dados se ele ainda não existir
CREATE DATABASE IF NOT EXISTS supermercado_db;

-- Seleciona o banco de dados para usar nos comandos seguintes
USE supermercado_db;

-- Cria a tabela de produtos
CREATE TABLE IF NOT EXISTS produtos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    quantidade_estoque INT NOT NULL
);

-- Cria a tabela de funcionários (com login e cargo)
CREATE TABLE IF NOT EXISTS funcionarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    cargo VARCHAR(50) NOT NULL -- 'GERENTE' ou 'OPERADOR_DE_CAIXA'
);

-- Cria a tabela para registrar cada venda
CREATE TABLE IF NOT EXISTS vendas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data_venda DATETIME NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL
);

-- Cria a tabela para registrar os itens de cada venda
CREATE TABLE IF NOT EXISTS itens_venda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_venda INT NOT NULL,
    id_produto INT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_venda) REFERENCES vendas(id),
    FOREIGN KEY (id_produto) REFERENCES produtos(id)
);

-- Inserir alguns produtos de exemplo (opcional)
-- (Verifica se a tabela está vazia antes de inserir para não duplicar)
INSERT INTO produtos (nome, preco, quantidade_estoque)
SELECT * FROM (SELECT 'Arroz Tipo 1 (5kg)', 25.50, 100) AS tmp
WHERE NOT EXISTS (SELECT nome FROM produtos WHERE nome = 'Arroz Tipo 1 (5kg)');

INSERT INTO produtos (nome, preco, quantidade_estoque)
SELECT