import Redis from "ioredis";
import { RedisClient } from "../../infra/redis/redisClient";
import { CarRepository } from "../../interface/repository/CarRepository";
import { CarRepositoryRedis } from "../../repository/CarRepositoryRedis";
import { Carrinho } from "../model/Carrinho";
import { ClienteEntity } from "../model/ClienteEntity";
import { ItemCarrinho } from "../model/ItemCarrinho";

export class CarService {

    private carRepository: CarRepository;

    constructor() {
        this.carRepository = new CarRepositoryRedis()
    }

    public async editarItemCarrinho(idUser: string, idProduto: number, quantidade: number) {
        const carrinhoEncontrado = await this.carRepository.findCarByUserId(idUser)
        await this.carRepository.findItemById(idProduto)

        console.log("carrinho antes editado")
        console.log(idProduto)
        console.log(quantidade)
        console.log(carrinhoEncontrado)

        carrinhoEncontrado?.itensCarrinho.find(i => {
            if (Number(i.produto?.id) === Number(idProduto)) {
                i.quantidade  =+ quantidade
            }
        });

        if(carrinhoEncontrado)
        await this.carRepository.createCarrinho(carrinhoEncontrado);

        console.log("CARRINHOO EDITADO")
        console.log(carrinhoEncontrado)
    }

    public async buscarItensCarrinho(idUser: string): Promise<Carrinho | null> {
        return await this.carRepository.findCarByUserId(idUser)
    }

    public async deletarItemCarrinho(idUser: string, idProduto: number) {
        const carrinhoEncontrado = await this.carRepository.findCarByUserId(idUser)
        const produtoEncontrado = await this.carRepository.findItemById(idProduto)

        if (!carrinhoEncontrado) {
            throw new Error("Carrinho não encontrado");
        }
        console.log(carrinhoEncontrado)
        console.log("ajsadfgkj")
        console.log(carrinhoEncontrado.user)
        if (carrinhoEncontrado.user === undefined) return null;
        console.log(carrinhoEncontrado.user.id)

        console.log("chegou depois do if")

        carrinhoEncontrado.toJSON()

        //const carrinhoEncontrado.itensCarrinho = carrinhoEncontrado?.itensCarrinho.filter(item => item.produto?.id === idProduto)
        // carrinhoEncontrado.itensCarrinho = carrinhoEncontrado.itensCarrinho
        //     .filter(item => item.produto?.id !== idProduto);

        console.log('idProduto:', idProduto, typeof idProduto);
        //console.log('_id item:', carrinhoEncontrado.itensCarrinho[0]._produto?._id, typeof carrinhoEncontrado.itensCarrinho[0]._produto?._id);


        carrinhoEncontrado.itensCarrinho = carrinhoEncontrado.itensCarrinho
            .filter(item => Number(item.produto?.id) !== Number(idProduto));
        console.log("CARRINHO DEPOIS DE REMOVER")
        console.log(carrinhoEncontrado)
        console.log("chegou antes de salvar o carrinho")
        console.log(carrinhoEncontrado.user?.id)
        await this.carRepository.createCarrinho(carrinhoEncontrado);
    }



    public async addItemCarrinho(idUser: string, idProduto: number, quantidade: number) {

        console.log(idUser)
        console.log("CHEGOU NO addItemCarrinho")
        const carrinhoEncontrado = await this.carRepository.findCarByUserId(idUser)
        const produtoEncontrado = await this.carRepository.findItemById(idProduto)

        console.log(carrinhoEncontrado)

        if (!carrinhoEncontrado) {
            const user = new ClienteEntity();
            const carrinhoNovo = new Carrinho()
            user.id = idUser
            carrinhoNovo.user = user
            console.log("carrino não encontrado")
            this.carRepository.createCarrinho(carrinhoNovo)
        }
        console.log("produtoEncontrado")
        console.log(produtoEncontrado)
        if (carrinhoEncontrado === null || produtoEncontrado === null) {
            return null
        }
        console.log("adicionar item no carrinho")

        const itemCarrinho = new ItemCarrinho()
        // itemCarrinho.carrinho = carrinhoEncontrado
        itemCarrinho.produto = produtoEncontrado
        itemCarrinho.quantidade = quantidade


        this.carRepository.addItemCarrinho(idUser, itemCarrinho)

    }
}