import  cds  from '@sap/cds';
import { CarController } from './module/carrinho/controller/CarController';
//import { CarController } from './modules/car/infraestucture/web/CarController';
   

export default cds.service.impl((srv) => {
 
  
  const carController = new CarController()
  carController.registerHandlers(srv);
  
 
});