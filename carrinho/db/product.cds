namespace app;
using { managed } from '@sap/cds/common';

@cds.persistence.skip
entity Product : managed {
  key  id: String;
  descricaoProduto: String not null;
  nomeProduto: String;
  preco: Integer;
  imagem: String;
 } 