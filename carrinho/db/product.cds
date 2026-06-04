namespace app;
using { managed } from '@sap/cds/common';


entity Product : managed {
  key  id: UUID;
  descricaoProduto: String not null;
  nomeProduto: String;
  preco: Integer;
  imagem: String;
 } 