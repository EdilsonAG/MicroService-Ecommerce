"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const cds_1 = __importDefault(require("@sap/cds"));
const CarController_1 = require("./module/carrinho/controller/CarController");
const CarService_1 = require("./module/carrinho/domain/service/CarService");
//import { CarController } from './modules/car/infraestucture/web/CarController';
exports.default = cds_1.default.service.impl((srv) => {
    const carService = new CarService_1.CarService;
    const carController = new CarController_1.CarController(carService);
    carController.registerHandlers(srv);
});
//# sourceMappingURL=carrinhoService.js.map