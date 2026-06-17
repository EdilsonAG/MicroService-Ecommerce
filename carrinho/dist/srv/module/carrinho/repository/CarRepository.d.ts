import cds from '@sap/cds';
import { Carrinho } from '../domain/model/Carrinho';
export declare class CarRepository {
    createCarrinho(car: Carrinho): Promise<void>;
    findByUserId(tx: cds.Transaction, userId: string): Promise<Carrinho>;
    createCarrinho2(tx: cds.Transaction, carrinho: Carrinho): Promise<any>;
}
//# sourceMappingURL=CarRepository.d.ts.map