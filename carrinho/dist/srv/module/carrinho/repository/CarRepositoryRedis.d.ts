import { Carrinho } from '../domain/model/Carrinho';
import { ItemCarrinho } from '../domain/model/ItemCarrinho';
import { CarRepository } from '../interface/repository/CarRepository';
export declare class CarRepositoryRedis implements CarRepository {
    private key;
    createCarrinho(car: Carrinho): Promise<void>;
    findCarByUserId(userId: string): Promise<Carrinho | null>;
    addItemCarrinho(userId: string, item: ItemCarrinho): Promise<void>;
}
//# sourceMappingURL=CarRepositoryRedis.d.ts.map