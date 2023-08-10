create table carrinhos (
    id uuid primary key,
    data_criacao timestamp NOT NULL,
    valor_total_venda numeric(14,2) NOT NULL
);

create table carrinho_itens (
    id uuid primary key,
    carrinho_id uuid not null,
    produto_id uuid not null,
    quantidade int not null,
    preco_venda numeric(14,2) NOT NULL,
    valor_total numeric(14,2) NOT NULL
);

ALTER TABLE carrinho_itens ADD CONSTRAINT fk_carrinho_itens_carrinhos FOREIGN KEY (carrinho_id) REFERENCES carrinhos (id);
ALTER TABLE carrinho_itens ADD CONSTRAINT fk_carrinho_itens_produtos FOREIGN KEY (produto_id) REFERENCES produtos (id);