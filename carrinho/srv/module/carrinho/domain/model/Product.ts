export class Product {
    private _id: number | undefined;
    private _descricaoProduto: string | undefined;
    private _nomeProduto: string | undefined;
    private _preco: number | undefined;
    public get preco(): number | undefined {
        return this._preco;
    }
    public set preco(value: number | undefined) {
        this._preco = value;
    }

    public get id(): number | undefined {
        return this._id;
    }
    public set id(value: number | undefined) {
        this._id = value;
    }


    public get descricaoProduto(): string | undefined {
        return this._descricaoProduto;
    }
    public set descricaoProduto(value: string | undefined) {
        this._descricaoProduto = value;
    }


    public get nomeProduto(): string | undefined {
        return this._nomeProduto;
    }
    public set nomeProduto(value: string | undefined) {
        this._nomeProduto = value;
    }

}