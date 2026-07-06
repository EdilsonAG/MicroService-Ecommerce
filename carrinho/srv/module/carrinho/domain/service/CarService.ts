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

    public criarCIarrinho():void{
        
    }

    public async addItemCarrinho(idUser:string, idProduto:string){
        
        console.log(idUser)
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

        if(carrinhoEncontrado === null || produtoEncontrado === null){
            throw new Error("sff")
        }
        console.log("adicionar item no carrinho")

        const itemCarrinho = new ItemCarrinho()
        itemCarrinho.carrinho = carrinhoEncontrado
        itemCarrinho.produto = produtoEncontrado
        itemCarrinho.quantidade = 5

        
        this.carRepository.addItemCarrinho(idUser,itemCarrinho)
        
    }
}