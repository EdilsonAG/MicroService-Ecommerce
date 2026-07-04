import Redis from "ioredis";
import { RedisClient } from "../../infra/redis/redisClient";
import { CarRepository } from "../../interface/repository/CarRepository";
import { CarRepositoryRedis } from "../../repository/CarRepositoryRedis";
import { Carrinho } from "../model/Carrinho";
import { ClienteEntity } from "../model/ClienteEntity";

export class CarService{

    private carRepository:CarRepository;

    constructor(){
        this.carRepository = new CarRepositoryRedis()
    }

    public criarCIarrinho():void{
        
    }

    public async addItemCarrinho(idUser:string, idProduto:string){
        
        
         const carrinhoEncontrado = await this.carRepository.findCarByUserId(idUser)
       
        
        if(!carrinhoEncontrado){
            const user = new ClienteEntity();
            const carrinhoNovo = new Carrinho()
            user.id =  idUser 
            carrinhoNovo.user = user
            this.carRepository.createCarrinho(carrinhoNovo)
        }

        //this.carRepository.addItemCarrinho(idUser)
        
    }
}