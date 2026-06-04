
namespace app;
using { cuid, managed } from '@sap/cds/common';
using { app.User } from './user';
 using { app.ItemCarrinho } from './ItemCarrinho'; 

entity Carrinho {
    key id          : UUID;
        user        : Association to one User;
        //   usar composition para array e também por conta do ciclo de vida em cascata
        itens       : Composition of many ItemCarrinho on itens.carrinho = $self;
}
 