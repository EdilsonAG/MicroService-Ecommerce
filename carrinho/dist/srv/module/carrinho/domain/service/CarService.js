"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.CarService = void 0;
const CarRepositoryRedis_1 = require("../../repository/CarRepositoryRedis");
const Carrinho_1 = require("../model/Carrinho");
const ClienteEntity_1 = require("../model/ClienteEntity");
const ItemCarrinho_1 = require("../model/ItemCarrinho");
class CarService {
    constructor() {
        this.carRepository = new CarRepositoryRedis_1.CarRepositoryRedis();
    }
    async buscarItensCarrinho(idUser) {
        return await this.carRepository.findCarByUserId(idUser);
    }
    async addItemCarrinho(idUser, idProduto, quantidade) {
        console.log(idUser);
        console.log("CHEGOU NO addItemCarrinho");
        const carrinhoEncontrado = await this.carRepository.findCarByUserId(idUser);
        const produtoEncontrado = await this.carRepository.findItemById(idProduto);
        if (!carrinhoEncontrado) {
            const user = new ClienteEntity_1.ClienteEntity();
            const carrinhoNovo = new Carrinho_1.Carrinho();
            user.id = idUser;
            carrinhoNovo.user = user;
            console.log("carrino não encontrado");
            this.carRepository.createCarrinho(carrinhoNovo);
        }
        console.log("produtoEncontrado");
        console.log(produtoEncontrado);
        if (carrinhoEncontrado === null || produtoEncontrado === null) {
            return null;
        }
        console.log("adicionar item no carrinho");
        const itemCarrinho = new ItemCarrinho_1.ItemCarrinho();
        // itemCarrinho.carrinho = carrinhoEncontrado
        itemCarrinho.produto = produtoEncontrado;
        itemCarrinho.quantidade = quantidade;
        this.carRepository.addItemCarrinho(idUser, itemCarrinho);
    }
}
exports.CarService = CarService;
//# sourceMappingURL=CarService.js.map