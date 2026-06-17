import { Carrinho } from "./Carrinho";
import { Product } from "./Product";
export declare class ItemCarrinho {
    private _id;
    private _produto;
    private _carrinho;
    private _quantidade;
    get id(): string | undefined;
    set id(value: string | undefined);
    get produto(): Product | undefined;
    set produto(value: Product | undefined);
    get quantidade(): number | undefined;
    set quantidade(value: number | undefined);
    get carrinho(): Carrinho | undefined;
    set carrinho(value: Carrinho | undefined);
}
//# sourceMappingURL=ItemCarrinho.d.ts.map