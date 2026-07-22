
namespace app;
using { cuid, managed } from '@sap/cds/common';
using { app.User } from './user';
 using { app.ItemCarrinho } from './itemCarrinho'; 

@cds.persistence.skip
entity Carrinho {
    key id          : String;
        user        : Association to one User;
        //   usar composition para array e também por conta do ciclo de vida em cascata
        itens       : Composition of many ItemCarrinho on itens.carrinho = $self;
}
 