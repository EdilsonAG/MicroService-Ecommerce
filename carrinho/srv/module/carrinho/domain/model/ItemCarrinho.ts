import { Carrinho } from "./Carrinho";
import { Product } from "./Product";

export class ItemCarrinho {
    private _id: string | undefined;
    private _produto: Product | undefined;
    private _carrinho: Carrinho | undefined;
    private _quantidade: number | undefined;
    
    public get id(): string | undefined {
        return this._id;
    }
    public set id(value: string | undefined) {
        this._id = value;
    }

    public get produto(): Product | undefined {
        return this._produto;
    }
    public set produto(value: Product | undefined) {
        this._produto = value;
    }

    public get quantidade(): number | undefined {
        return this._quantidade;
    }
    public set quantidade(value: number | undefined) {
        this._quantidade = value;
    }

    public get carrinho(): Carrinho | undefined {
        return this._carrinho;
    }
    public set carrinho(value: Carrinho | undefined) {
        this._carrinho = value;
    }
}