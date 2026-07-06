namespace app;
using { managed } from '@sap/cds/common';

@cds.persistence.skip
entity User : managed {
    key id:  UUID;
    nome: String not null;
    email: String not null;
    senha: String not null;
  
 }


    