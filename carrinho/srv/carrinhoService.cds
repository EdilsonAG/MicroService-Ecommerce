using { app } from '../db/carrinho';

service carrinhoService @(requires: 'authenticated-user') { 
  entity Carrinho as projection on app.Carrinho;
}

