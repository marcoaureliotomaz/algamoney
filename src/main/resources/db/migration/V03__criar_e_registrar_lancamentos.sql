CREATE TABLE lancamento (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(50) NOT NULL,
    data_vencimento date not null,
    data_pagamento date,
    valor decimal(10,2) not null,
    observacao varchar(100),
    tipo varchar(20),
    codigo_categoria bigint(20) not null,
    codigo_pessoa bigint(20) not null,
    foreign key (codigo_categoria) references categoria(codigo),
    foreign key (codigo_pessoa) references pessoa(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa)
values ("teste 1", "2022-02-02", null,1.1, "obs teste","DESPESA", 7, 1);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_categoria, codigo_pessoa)
values ("teste 2", "2022-03-03", null,2.2, "obs teste", "RECEITA", 7, 1);