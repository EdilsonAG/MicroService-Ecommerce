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
                const authHeader = req.headers.authorization?.replace('Bearer ', '');
                const decoded = await jwt.decode(authHeader);
                const idUser = decoded.usuario_id;
                return this.carService.buscarItensCarrinho(idUser);
            }
            catch (error) {
                req.error(400, error.message);
            }
        });
        srv.on('addItemCarrinho', async (req) => {
            try {
                console.log("\n\n\n");
                console.log("CREATE CARRINHO");
                const authHeader = req.headers.authorization?.replace('Bearer ', '');
                const decoded = await jwt.decode(authHeader);
                const idUser = decoded.usuario_id;
                const quantidade = req.data.quantidade;
                const idProduto = req.data.idProduto;
                console.log("token");
                console.log(authHeader);
                console.log(idProduto);
                console.log(idProduto);
                console.log(idProduto);
                console.log(idUser);
                console.log(decoded.usuario_id);
                this.carService.addItemCarrinho(idUser, idProduto, quantidade);
            }
            catch (error) {
            }
        });
    }
}
exports.CarController = CarController;
//# sourceMappingURL=CarController.js.map