import Redis from "ioredis";
import { RedisClient } from "../../infra/redis/redisClient";
import { CarRepository } from "../../interface/repository/CarRepository";
import { CarRepositoryRedis } from "../../repository/CarRepositoryRedis";
import { Carrinho } from "../model/Carrinho";
import { ClienteEntity } from "../model/ClienteEntity";
import { ItemCarrinho } from "../model/ItemCarrinho";

export class CarService{

    private carRepository:CarRepository;

    constructor(){
        this.carRepository = new CarRepositoryRedis()
    }

    public async buscarItensCarrinho(idUser:string):Promise<Carrinho | null>{
       return await this.carRepository.findCarByUserId(idUser)
    }



    public async addItemCarrinho(idUser:string, idProduto:string, quantidade:number){
        
        console.log(idUser)
        console.log("CHEGOU NO addItemCarrinho")
         const carrinhoEncontrado = await this.carRepository.findCarByUserId(idUser)
         const produtoEncontrado = await this.carRepository.findItemById(idProduto)
       
        
        if(!carrinhoEncontrado ){
            const user = new ClienteEntity();
            const carrinhoNovo = new Carrinho()
            user.id =  idUser 
            carrinhoNovo.user = user
            console.log("carrino não encontrado")
            this.carRepository.createCarrinho(carrinhoNovo)
        }
        console.log("produtoEncontrado")
        console.log(produtoEncontrado)
        if(carrinhoEncontrado === null || produtoEncontrado === null){
            return null
        }
        console.log("adicionar item no carrinho")

        const itemCarrinho = new ItemCarrinho()
       // itemCarrinho.carrinho = carrinhoEncontrado
        itemCarrinho.produto = produtoEncontrado
        itemCarrinho.quantidade = quantidade

        
        this.carRepository.addItemCarrinho(idUser,itemCarrinho)
        
    }
}