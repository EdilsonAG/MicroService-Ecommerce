import { Carrinho } from "../model/Carrinho";
export declare class CarService {
    private carRepository;
    constructor();
    buscarItensCarrinho(idUser: string): Promise<Carrinho | null>;
    addItemCarrinho(idUser: string, idProduto: number, quantidade: number): Promise<null | undefined>;
}
//# sourceMappingURL=CarService.d.ts.map