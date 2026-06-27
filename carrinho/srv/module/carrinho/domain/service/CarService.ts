import Redis from "ioredis";
import { RedisClient } from "../../infra/redis/redisClient";

export class CarService{

    public criarCIarrinho():void{
        
    }

    public async addItemCarrinho(idUser:string, idProduto:string){
        
        const instancia:Redis = RedisClient.getInstance();

        
    }
}