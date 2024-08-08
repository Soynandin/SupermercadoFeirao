CREATE DATABASE supermercadoFeirao;

USE supermercadoFeirao;

CREATE TABLE Funcionario (
    idFuncionario INT AUTO_INCREMENT PRIMARY KEY,
    nomeCompleto VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    email VARCHAR(150),
    dataNasc DATE NOT NULL,
    categoriaFunc INT NOT NULL,
    senha VARCHAR(30) NOT NULL
);

CREATE TABLE Cliente (
    idCliente INT AUTO_INCREMENT PRIMARY KEY,
    nomeCompleto VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    email VARCHAR(150),
    dataNasc DATE NOT NULL,
    limCredito DOUBLE NOT NULL
);

CREATE TABLE Produto (
    idProduto INT AUTO_INCREMENT PRIMARY KEY,
    codProduto VARCHAR(100) NOT NULL,
    nomeProduto VARCHAR(150) NOT NULL,
    categoriaProduto VARCHAR(45) NOT NULL,
    dtFabProduto DATE NOT NULL,
    dtValProduto DATE NOT NULL,
    precoCaixaProduto DOUBLE NOT NULL,
    precoUnidProduto DOUBLE NOT NULL,
    qtdCaixaProduto INT NOT NULL,
    qtdEstoqueProduto INT NOT NULL,
    qtdMinEstoqueProduto INT NOT NULL,
    alertaMinEstoque TINYINT(1) NOT NULL,
    quantidadeAtual INT NOT NULL
);

CREATE TABLE BalancoGeral (
    idBalancoGeral INT AUTO_INCREMENT PRIMARY KEY,
    dataHoraBalanco DATE NOT NULL,
    dataHoraCaixaAberto DATETIME ,
    dataHoraCaixaFechado DATETIME ,
	movDinheiro DOUBLE,
    movCredito DOUBLE,
    movDebito DOUBLE,
    movFiado DOUBLE,
    valorTotal DOUBLE,
    valorSaldo DOUBLE,
    valorDividas DOUBLE,
    Funcionario_idFuncionario INT NOT NULL,
    FOREIGN KEY (Funcionario_idFuncionario) REFERENCES Funcionario(idFuncionario)
);

CREATE TABLE Venda (
    idVenda INT AUTO_INCREMENT PRIMARY KEY,
    listaProdutos TEXT,
    dataHoraVenda DATETIME NOT NULL,
    formaPagVenda VARCHAR(45) NOT NULL,
    descontoVenda VARCHAR(45) NOT NULL,
    statusVenda VARCHAR(45) NOT NULL,
    subTotalVenda VARCHAR(45) NOT NULL,
    valorTotalVenda VARCHAR(45) NOT NULL,
    imposto VARCHAR(45) NOT NULL,
    cpfCliente VARCHAR(14),
    BalancoGeral_balancoGeralId INT NOT NULL,
    FOREIGN KEY (BalancoGeral_balancoGeralId) REFERENCES BalancoGeral(idBalancoGeral)
);
CREATE TABLE BalancoFinanceiro (
    idBalancoFinanceiro INT AUTO_INCREMENT PRIMARY KEY,
    dataHoraBalanco DATE NOT NULL,
    movimentoMensal DOUBLE NOT NULL,
    lucro DOUBLE NOT NULL,
    divida DOUBLE NOT NULL
);
select * from Produto;
INSERT INTO Funcionario (nomeCompleto, cpf, email, dataNasc, categoriaFunc, senha)
VALUES ('Jacinto', '115.634.630-40', 'Jacinto.Rego@example.com', '1985-07-15', 1, '123456');

-- Produtos de Padaria
INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('PAD001', 'Pão Francês', 'Padaria', '2024-06-01', '2024-06-07', 50.00, 1.00, 50, 10, 5, 1, 500);

INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('PAD002', 'Bolo de Chocolate', 'Padaria', '2024-06-01', '2024-06-10', 100.00, 20.00, 10, 5, 2, 1, 50);

-- Carnes e Aves
INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('CAR001', 'Frango Inteiro', 'Carnes e Aves', '2024-06-01', '2024-07-01', 200.00, 20.00, 10, 8, 3, 1, 80);

INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('CAR002', 'Carne Bovina', 'Carnes e Aves', '2024-06-01', '2024-07-01', 300.00, 30.00, 10, 6, 3, 1, 60);

-- Frutos do Mar
INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('FDM001', 'Camarão', 'Frutos do Mar', '2024-06-01', '2024-07-15', 500.00, 50.00, 10, 7, 2, 1, 70);

INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('FDM002', 'Salmão', 'Frutos do Mar', '2024-06-01', '2024-07-10', 600.00, 60.00, 10, 5, 2, 1, 50);

-- Frutas e Vegetais
INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('FV001', 'Maçã', 'Frutas e Vegetais', '2024-06-01', '2024-06-20', 100.00, 2.00, 50, 15, 5, 1, 750);

INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('FV002', 'Banana', 'Frutas e Vegetais', '2024-06-01', '2024-06-25', 80.00, 1.00, 80, 20, 8, 1, 1600);

-- Laticínios
INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('LAT001', 'Leite Integral', 'Laticínios', '2024-06-01', '2024-06-20', 60.00, 3.00, 20, 30, 10, 1, 600);

INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('LAT002', 'Queijo Mussarela', 'Laticínios', '2024-06-01', '2024-06-25', 150.00, 15.00, 10, 10, 3, 1, 100);

-- Congelados
INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('CON001', 'Pizza Congelada', 'Congelados', '2024-06-01', '2024-07-01', 200.00, 20.00, 10, 5, 2, 1, 50);

INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('CON002', 'Sorvete', 'Congelados', '2024-06-01', '2024-07-01', 100.00, 10.00, 10, 10, 3, 1, 100);

-- Mercearia
INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('MER001', 'Arroz', 'Mercearia', '2024-06-01', '2024-12-01', 60.00, 6.00, 10, 25, 10, 1, 250);

INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('MER002', 'Feijão', 'Mercearia', '2024-06-01', '2024-12-01', 50.00, 5.00, 10, 30, 10, 1, 300);

-- Bebidas
INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('BEB001', 'Refrigerante', 'Bebidas', '2024-06-01', '2024-12-01', 60.00, 3.00, 20, 25, 10, 1, 500);

INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('BEB002', 'Água Mineral', 'Bebidas', '2024-06-01', '2024-12-01', 40.00, 2.00, 20, 30, 10, 1, 600);

-- Limpeza
INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('LIM001', 'Detergente', 'Limpeza', '2024-06-01', '2024-12-01', 30.00, 3.00, 10, 50, 20, 1, 500);

INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('LIM002', 'Sabão em Pó', 'Limpeza', '2024-06-01', '2024-12-01', 40.00, 4.00, 10, 40, 15, 1, 400);

-- Cosméticos
INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('COS001', 'Shampoo', 'Cosméticos', '2024-06-01', '2024-12-01', 60.00, 6.00, 10, 20, 5, 1, 200);

INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('COS002', 'Condicionador', 'Cosméticos', '2024-06-01', '2024-12-01', 60.00, 6.00, 10, 20, 5, 1, 200);

-- Higiene Pessoal
INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('HIG001', 'Pasta de Dente', 'Higiene Pessoal', '2024-06-01', '2024-12-01', 20.00, 2.00, 10, 30, 10, 1, 300);

INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('HIG002', 'Sabonete', 'Higiene Pessoal', '2024-06-01', '2024-12-01', 30.00, 3.00, 10, 40, 15, 1, 400);

-- Bebês e Crianças
INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('BBC001', 'Fraldas', 'Bebês e Crianças', '2024-06-01', '2024-12-01', 100.00, 10.00, 10, 20, 5, 1, 200);

INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('BBC002', 'Lenços Umedecidos', 'Bebês e Crianças', '2024-06-01', '2024-12-01', 50.00, 5.00, 10, 30, 10, 1, 300);

-- Higiene Pet
INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('PET001', 'Shampoo Pet', 'Higiene Pet', '2024-06-01', '2024-12-01', 60.00, 6.00, 10, 20, 5, 1, 200);

INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('PET002', 'Areia para Gato', 'Higiene Pet', '2024-06-01', '2024-12-01', 80.00, 8.00, 10, 15, 5, 1, 150);

-- Bem-Estar e Saúde
INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('BEM001', 'Vitaminas', 'Bem-Estar e Saúde', '2024-06-01', '2024-12-01', 200.00, 20.00, 10, 10, 3, 1, 100);

INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('BEM002', 'Suplementos', 'Bem-Estar e Saúde', '2024-06-01', '2024-12-01', 150.00, 15.00, 10, 15, 5, 1, 150);

-- Queijos Especiais
INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('QUE001', 'Queijo Gorgonzola', 'Queijos Especiais', '2024-06-01', '2024-12-01', 300.00, 30.00, 10, 5, 2, 1, 50);

INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('QUE002', 'Queijo Brie', 'Queijos Especiais', '2024-06-01', '2024-12-01', 400.00, 40.00, 10, 5, 2, 1, 50);

-- Confeitaria
INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('CON001', 'Chocolate', 'Confeitaria', '2024-06-01', '2024-12-01', 150.00, 15.00, 10, 10, 3, 1, 100);

INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('CON002', 'Biscoito', 'Confeitaria', '2024-06-01', '2024-12-01', 80.00, 8.00, 10, 20, 5, 1, 200);

-- Tabacaria
INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('TAB001', 'Cigarro', 'Tabacaria', '2024-06-01', '2024-12-01', 500.00, 50.00, 10, 5, 2, 1, 50);

INSERT INTO Produto (codProduto, nomeProduto, categoriaProduto, dtFabProduto, dtValProduto, precoCaixaProduto, precoUnidProduto, qtdCaixaProduto, qtdEstoqueProduto, qtdMinEstoqueProduto, alertaMinEstoque, quantidadeAtual)
VALUES ('TAB002', 'Charuto', 'Tabacaria', '2024-06-01', '2024-12-01', 800.00, 80.00, 10, 5, 2, 1, 50);