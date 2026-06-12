import  cds  from '@sap/cds';
import { CarController } from './module/carrinho/controller/CarController';
import { CarService } from './module/carrinho/domain/service/CarService';
//import { CarController } from './modules/car/infraestucture/web/CarController';
   

export default cds.service.impl((srv) => {
 
  const carService = new CarService;

  const carController = new CarController(carService);
  carController.registerHandlers(srv);
  
 
});