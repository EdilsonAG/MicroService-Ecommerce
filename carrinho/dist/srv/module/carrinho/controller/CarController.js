"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.CarController = void 0;
const jwt = require('jsonwebtoken');
class CarController {
    constructor(carService) {
        this.carService = carService;
    }
    registerHandlers(srv) {
        srv.on('READ', 'Carrinho', async (req) => {
            try {
                // chamar use case
                console.log("\n\n\n dados: " + req);
                //const id = await this.createProductUseCase.createProduct(req.data);
                //return { ID: id, ...req.data };
            }
            catch (error) {
                req.error(400, error.message);
            }
        });
        srv.on('CREATE', 'Carrinho', async (req) => {
            try {
                console.log("\n\n\n");
                console.log("CREATE CARRINHO");
                const authHeader = req.headers.authorization?.replace('Bearer ', '');
                const decoded = await jwt.decode(authHeader);
                const idUser = decoded.id;
                const idProduto = req.produto;
                console.log("token");
                console.log(authHeader);
                this.carService.addItemCarrinho(idUser, idProduto);
            }
            catch (error) {
            }
        });
    }
}
exports.CarController = CarController;
//# sourceMappingURL=CarController.js.map