

namespace app;
using { cuid, managed } from '@sap/cds/common';
 using { app.Product } from './product';
using { app.Carrinho } from './Carrinho';  



entity ItemCarrinho {
    key id          : UUID;
        carrinho    : Association to one Carrinho;
        produto     : Association to one Product;
        quantidade  : Integer;
} 