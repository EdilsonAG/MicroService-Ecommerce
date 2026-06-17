import { ClienteEntity } from "./ClienteEntity";
import { ItemCarrinho } from "./ItemCarrinho";
 
 

export class Carrinho {
    private _id: string | undefined;
    private _user: ClienteEntity | undefined;
    private _itensCarrinho: Array<ItemCarrinho> = new Array<ItemCarrinho>;

    public get id(): string | undefined {
        return this._id;
    }
    public set id(value: string | undefined) {
        this._id = value;
    }
    public get user(): ClienteEntity | undefined {
        return this._user;
    }
    public set user(value: ClienteEntity | undefined) {
        this._user = value;
    }

    public get itensCarrinho(): Array<ItemCarrinho> {
        return this._itensCarrinho;
    }
    public set itensCarrinho(value: Array<ItemCarrinho>) {
        this._itensCarrinho = value;
    }
}