CREATE TABLE foto_produto_entity(
	id bigint not null auto_increment,
    url varchar(255),
	id_produto_entity bigint,
   primary key (id),
   constraint fk_foto_produto_entity_produto_entity foreign key (id_produto_entity) references produto_entity (id)
   
   )