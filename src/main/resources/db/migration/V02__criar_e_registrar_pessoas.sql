CREATE TABLE pessoa (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
    logradouro varchar(30),
    numero varchar(10),
    complemento varchar(30),
    bairro varchar(20),
    cep varchar(10),
    cidade varchar(50),
    uf varchar(2),
    ativo bool not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa (nome, ativo) values ('Marco', true);
INSERT INTO pessoa (nome, ativo) values ('Guilherme', true);
INSERT INTO pessoa (nome, ativo) values ('Nícolas', true);



