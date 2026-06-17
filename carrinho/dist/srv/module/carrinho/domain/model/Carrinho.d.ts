import { ClienteEntity } from "./ClienteEntity";
import { ItemCarrinho } from "./ItemCarrinho";
export declare class Carrinho {
    private _id;
    private _user;
    private _itensCarrinho;
    get id(): string | undefined;
    set id(value: string | undefined);
    get user(): ClienteEntity | undefined;
    set user(value: ClienteEntity | undefined);
    get itensCarrinho(): Array<ItemCarrinho>;
    set itensCarrinho(value: Array<ItemCarrinho>);
}
//# sourceMappingURL=Carrinho.d.ts.map