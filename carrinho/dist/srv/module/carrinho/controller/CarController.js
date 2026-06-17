"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.CarController = void 0;
class CarController {
    constructor(carService) {
        this.carService = carService;
    }
    registerHandlers(srv) {
        srv.on('CREATE', 'Carrinho', async (req) => {
            try {
                // chamar use case
                console.log("\n\n\n dados: " + req.data);
                //const id = await this.createProductUseCase.createProduct(req.data);
                //return { ID: id, ...req.data };
            }
            catch (error) {
                req.error(400, error.message);
            }
        });
    }
}
exports.CarController = CarController;
//# sourceMappingURL=CarController.js.map