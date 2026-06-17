"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Carrinho = void 0;
class Carrinho {
    constructor() {
        this._itensCarrinho = new Array;
    }
    get id() {
        return this._id;
    }
    set id(value) {
        this._id = value;
    }
    get user() {
        return this._user;
    }
    set user(value) {
        this._user = value;
    }
    get itensCarrinho() {
        return this._itensCarrinho;
    }
    set itensCarrinho(value) {
        this._itensCarrinho = value;
    }
}
exports.Carrinho = Carrinho;
//# sourceMappingURL=Carrinho.js.map