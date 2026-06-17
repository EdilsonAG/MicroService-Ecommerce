"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.CarRepository = void 0;
const cds_1 = __importDefault(require("@sap/cds"));
class CarRepository {
    async createCarrinho(car) {
        try {
            const db = await cds_1.default.connect.to('db');
            await db.run(cds_1.default.ql.INSERT.into("app.Carrinho").entries({
                user_id: car.user?.id
            }));
        }
        catch (error) {
        }
    }
    async findByUserId(tx, userId) {
        const carrinho = await tx.run(cds_1.default.ql.SELECT.one.from('app.Carrinho').where({ user_id: userId }));
        return carrinho;
    }
    async createCarrinho2(tx, carrinho) {
        return tx.run(cds_1.default.ql.INSERT.into('app.Carrinho').entries({
            user_id: carrinho.user?.id
        }));
    }
}
exports.CarRepository = CarRepository;
//# sourceMappingURL=CarRepository.js.map