export class ClienteEntity{
    private _id: number | undefined;
    private _nome: string | undefined;
    private _email: string | undefined;
    public get email(): string | undefined {
        return this._email;
    }
    public set email(value: string | undefined) {
        this._email = value;
    }

    public get nome(): string | undefined {
        return this._nome;
    }
    public set nome(value: string | undefined) {
        this._nome = value;
    }

    public get id(): number | undefined{
        return this._id;
    }
    public set id(value: number) {
        this._id = value;
    }
    

}