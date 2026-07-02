"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.CarService = void 0;
const redisClient_1 = require("../../infra/redis/redisClient");
class CarService {
    criarCIarrinho() {
    }
    async addItemCarrinho(idUser, idProduto) {
        const instancia = redisClient_1.RedisClient.getInstance();
    }
}
exports.CarService = CarService;
//# sourceMappingURL=CarService.js.map