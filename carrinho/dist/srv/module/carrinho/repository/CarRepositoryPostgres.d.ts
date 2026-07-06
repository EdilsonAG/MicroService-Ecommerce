import cds from '@sap/cds';
import { Carrinho } from '../domain/model/Carrinho';
import { CarRepository } from '../interface/repository/CarRepository';
import { ItemCarrinho } from '../domain/model/ItemCarrinho';
import { Product } from '../domain/model/Product';
export declare class CarRepositoryPostgres implements CarRepository {
    findItemById(idItem: string): Promise<Product | null>;
    addItemCarrinho(userId: string, item: ItemCarrinho): Promise<void>;
    findCarByUserId(userId: string): Promise<Carrinho | null>;
    createCarrinho(car: Carrinho): Promise<void>;
    findByUserId(tx: cds.Transaction, userId: string): Promise<Carrinho>;
    createCarrinho2(tx: cds.Transaction, carrinho: Carrinho): Promise<any>;
}
//# sourceMappingURL=CarRepositoryPostgres.d.ts.map