import { Carrinho } from "../../domain/model/Carrinho";
import { ItemCarrinho } from "../../domain/model/ItemCarrinho";
export interface CarRepository {
    createCarrinho(car: Carrinho): Promise<void>;
    addItemCarrinho(userId: string, item: ItemCarrinho): Promise<void>;
    findCarByUserId(userId: string): Promise<Carrinho | null>;
}
//# sourceMappingURL=CarRepository.d.ts.map