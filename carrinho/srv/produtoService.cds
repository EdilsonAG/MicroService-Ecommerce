using { app } from '../db/product';

service productService @(requires: 'authenticated-user') { 
  entity Product as projection on app.Product;
}

