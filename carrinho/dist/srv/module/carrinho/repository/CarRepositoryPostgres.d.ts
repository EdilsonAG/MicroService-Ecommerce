import cds from '@sap/cds';
import { Carrinho } from '../domain/model/Carrinho';
import { CarRepository } from '../interface/repository/CarRepository';
import { ItemCarrinho } from '../domain/model/ItemCarrinho';
export declare class CarRepositoryPostgres implements CarRepository {
    addItemCarrinho(userId: string, item: ItemCarrinho): Promise<void>;
    findCarByUserId(userId: string): Promise<Carrinho | null>;
    createCarrinho(car: Carrinho): Promise<void>;
    findByUserId(tx: cds.Transaction, userId: string): Promise<Carrinho>;
    createCarrinho2(tx: cds.Transaction, carrinho: Carrinho): Promise<any>;
}
//# sourceMappingURL=CarRepositoryPostgres.d.ts.map