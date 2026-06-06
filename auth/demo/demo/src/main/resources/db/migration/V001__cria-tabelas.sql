CREATE TABLE cliente_entity(
    id bigint not null auto_increment,
    nome varchar(40),
    email varchar(40),
    senha varchar(20),
    
    primary key (id)
);

create table grupo (
	id bigint not null auto_increment,
	nome varchar(60) not null,
	
	primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table permissao (
	id bigint not null auto_increment,
	descricao varchar(60) not null,
	nome varchar(100) not null,
	
	primary key (id)
) engine=InnoDB default charset=utf8mb4;


create table grupo_permissao (
	grupo_id bigint not null,
	permissao_id bigint not null,
	
	primary key (grupo_id, permissao_id)
) engine=InnoDB default charset=utf8mb4;

 
CREATE TABLE cliente_entity_grupo(

    grupo_id bigint not null,
    cliente_id bigint not null,

    primary key (grupo_id, cliente_id)

)