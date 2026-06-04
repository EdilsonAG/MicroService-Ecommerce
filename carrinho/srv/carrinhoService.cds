using { app } from '../db/Carrinho';

service carrinhoService @(requires: 'any') { 
  entity Carrinho as projection on app.Carrinho;
}

