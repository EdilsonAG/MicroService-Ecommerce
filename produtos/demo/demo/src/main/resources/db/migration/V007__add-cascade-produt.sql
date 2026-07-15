ALTER TABLE foto_produto_entity
DROP FOREIGN KEY fk_foto_produto_entity_produto_entity;

ALTER TABLE foto_produto_entity
ADD CONSTRAINT fk_foto_produto_entity_produto_entity
FOREIGN KEY (id_produto_entity) REFERENCES produto_entity(id)
ON DELETE CASCADE;