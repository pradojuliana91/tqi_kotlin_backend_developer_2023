create table produtos_categorias (
    id uuid primary key,
    nome varchar(100)
);

insert into produtos_categorias (id, nome) values ('f442a928-39de-410b-85ff-6be93f19e77c', 'Limpeza');
insert into produtos_categorias (id, nome) values ('4a1f5305-0ebb-4ca6-924d-b19d67fce238', 'Bebidas');
insert into produtos_categorias (id, nome) values ('4f97dde2-34e8-438d-9fd2-6ec7e5aabee1', 'Bombonier');
insert into produtos_categorias (id, nome) values ('429a25a4-a1d8-4091-adc6-7deab184f2a8', 'Salgadinhos');
insert into produtos_categorias (id, nome) values ('a1f68e45-7d57-435b-8dc7-240df384763a', 'Chás e Cafes');
insert into produtos_categorias (id, nome) values ('d387d18a-bae9-47d8-8825-0269bf122cb7', 'Grãos e Cereais');
insert into produtos_categorias (id, nome) values ('9a648531-73ff-4600-8efa-fbb5a81db90e', 'Outros');

create table produtos (
    id uuid primary key,
    sku varchar(20) not null,
    nome varchar(100) not null,
    produto_categoria_id uuid not null,
    unidade_medida varchar(10) not null,
    preco_unitario numeric(14,2) not null,
    preco_venda numeric(14,2) not null
);

ALTER TABLE produtos ADD CONSTRAINT fk_produtos_produtos_categorias FOREIGN KEY (produto_categoria_id) REFERENCES produtos_categorias (id);

insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('66ae27af-9084-46ca-9ee9-1c78bbca170c','1','detergente','f442a928-39de-410b-85ff-6be93f19e77c','un', 2, 4);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('2e5a9797-bafe-4917-aec8-712dbb669358','2','sabonete','f442a928-39de-410b-85ff-6be93f19e77c','un', 2, 3);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('02e3165a-1fe2-4927-8bd2-6d9b1cc35682','3','amaciante','f442a928-39de-410b-85ff-6be93f19e77c','un', 8, 16);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('e59f7a71-5b97-4560-8c6d-af768083d139','4','desinfetante','f442a928-39de-410b-85ff-6be93f19e77c','un', 10, 20);

insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('ecf8a1df-8c8e-4353-a963-5fa5208cad9a','5','coca cola','4a1f5305-0ebb-4ca6-924d-b19d67fce238','un', 3, 5);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('c492e040-6608-4b25-91e9-b71af195a5c2','6','agua sem gas','4a1f5305-0ebb-4ca6-924d-b19d67fce238','un', 1, 2);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('4e1dd39e-e78b-402a-92a3-25b387a3c293','7','agua com gas','4a1f5305-0ebb-4ca6-924d-b19d67fce238','un', 1.50, 3);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('6f74e267-6fda-4fd6-9ce1-e67ce1993892','8','cerveja','4a1f5305-0ebb-4ca6-924d-b19d67fce238','un', 4, 8);

insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('fd58ba3e-8074-4464-97cd-42f191673b8f','9','chocolate','4f97dde2-34e8-438d-9fd2-6ec7e5aabee1','un', 1, 2);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('6bd07227-54a0-47c2-ad39-50a2ea8b4a54','10','chiclete','4f97dde2-34e8-438d-9fd2-6ec7e5aabee1','un', 1, 1.50);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('072b4d08-051b-44b9-a431-da865bd81eb9','11','pirulito','4f97dde2-34e8-438d-9fd2-6ec7e5aabee1','un', 1, 1.50);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('f68e3c8d-8811-4529-9df4-fdb420feea75','12','sorvete','4f97dde2-34e8-438d-9fd2-6ec7e5aabee1','un', 4, 8);

insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('7ee07830-4da8-4a7f-8e01-ce3959c49eb7','13','amendoim','429a25a4-a1d8-4091-adc6-7deab184f2a8','un', 4, 8);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('52092ef3-c7bc-49aa-a756-db8b3591b47a','14','biscoito','429a25a4-a1d8-4091-adc6-7deab184f2a8','un', 4, 8);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('1caf8c4a-36b4-431f-938e-592b3689b463','15','pipoca','429a25a4-a1d8-4091-adc6-7deab184f2a8','un', 4, 8);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('5d2cfdac-0daf-4649-942d-4ae03f7abff6','16','batata frita','429a25a4-a1d8-4091-adc6-7deab184f2a8','un', 4, 8);

insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('ac56cc8b-8c6b-4d6d-bd17-9bb4dd09e428','17','cappuccino','a1f68e45-7d57-435b-8dc7-240df384763a','un', 6, 10);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('bb73f75c-8cbd-4b68-ac19-0a5788829812','18','cafe especial','a1f68e45-7d57-435b-8dc7-240df384763a','un', 4, 8);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('6a4745bd-14b6-439d-aa7e-6e7ea825cd56','19','achocolatado','a1f68e45-7d57-435b-8dc7-240df384763a','un', 3, 6);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('e2974385-1d49-4f27-9c8e-e170cb97976b','20','cha mate','a1f68e45-7d57-435b-8dc7-240df384763a','un', 2, 5);

insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('e5f6c4da-0b2a-415f-bfcd-9c8c948c884e','21','granola','d387d18a-bae9-47d8-8825-0269bf122cb7','un', 12, 20);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('0ccf4210-41ce-4087-bf05-ce8d9a23e900','22','sucrilhos','d387d18a-bae9-47d8-8825-0269bf122cb7','un', 5, 15);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('b63ed5ae-0cf6-49b2-8f12-8572b157b9b3','23','frutas secas','d387d18a-bae9-47d8-8825-0269bf122cb7','un', 12, 25);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('53608c55-4075-4157-9c8e-88c7693c2f52','24','aveia','d387d18a-bae9-47d8-8825-0269bf122cb7','un', 2, 5);

insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('2303e0a2-2cf3-4061-baa8-7a269dee1731','25','pilha','9a648531-73ff-4600-8efa-fbb5a81db90e','un', 10, 25);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('55df6ac5-8b44-4ab8-85a0-6b8fac039f5b','26','fone de ouvido','9a648531-73ff-4600-8efa-fbb5a81db90e','un', 20, 30);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('9410d3d1-5906-4711-9b9e-0515cbd60bca','27','caneta','9a648531-73ff-4600-8efa-fbb5a81db90e','un', 2, 5);
insert into produtos (id, sku, nome, produto_categoria_id, unidade_medida, preco_unitario, preco_venda) values ('93c20771-d378-44ea-bbd9-8f7f538cf6e1','28','caderno','9a648531-73ff-4600-8efa-fbb5a81db90e','un', 10, 15);