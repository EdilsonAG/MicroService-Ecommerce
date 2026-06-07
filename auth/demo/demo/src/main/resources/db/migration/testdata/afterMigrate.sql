set foreign_key_checks = 0; 

lock tables cliente_entity write, permissao write, grupo write, grupo_permissao write; 

delete from cliente_entity;
delete from permissao;
delete from grupo;
delete from grupo_permissao;


set foreign_key_checks = 1;

alter table cliente_entity auto_increment = 1;
alter table permissao auto_increment = 1;
alter table grupo auto_increment = 1;
alter table grupo_permissao auto_increment = 1;
 
 
 
 

insert into permissao ( nome, descricao) values ( 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao ( nome, descricao) values ( 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into grupo (descricao) values ( 'usuario' );

insert into grupo_permissao (grupo_id,permissao_id) values ( 1, 1 );


insert into cliente_entity (nome,email,senha) values("edilson","edilson@gmail.com","$2a$10$aW6FImlJA1AqSdAsgNv9kOyMYMP2Eh06haocDbVpHKp5PTYOdjw2C");
insert into cliente_entity (nome,email,senha) values("debora","debora@gmail.com","$2a$10$aW6FImlJA1AqSdAsgNv9kOyMYMP2Eh06haocDbVpHKp5PTYOdjw2C");
insert into cliente_entity (nome,email,senha) values("eduardo","eduardo@gmail.com","$2a$10$aW6FImlJA1AqSdAsgNv9kOyMYMP2Eh06haocDbVpHKp5PTYOdjw2C");
 
unlock tables;