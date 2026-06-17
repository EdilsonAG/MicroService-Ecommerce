
import Redis from 'ioredis';
import { Carrinho } from '../domain/model/Carrinho';
import { RedisClient } from '../infra/redis/redisClient';

export class CarRepository {

    private key(userId: string) {
        return `carrinho:${userId}`;
    }

    //   async createCarrinho(car: Carrinho): Promise<void> {

    //     const redis:Redis = RedisClient.getInstance();

    //     if(car.user?.id === undefined){
    //         throw new Error("usuário não existente");
    //     }

    //     const key = this.key(car.user?.id);
    //     await redis.set(key, JSON.stringify(car));
    //     await redis.expire(key, 60 * 60 * 24 * 7);
    //   }

    async createCarrinho(car: Carrinho): Promise<void> {
        if (!car.user?.id) throw new Error("usuário não existente");

        const keyCar = car.id = crypto.randomUUID(); 

        //const key = this.key(car.user.id);

        const payload = {
            id: keyCar,
            user_id: car.user.id,
            itensCarrinho: car.itensCarrinho,
        };

        const redis = RedisClient.getInstance();
        await redis.set(keyCar, JSON.stringify(payload));
        await redis.expire(keyCar, 60 * 60 * 24 * 7);
    }

    async addItemCarrinho(car:Carrinho): Promise<void>{
        if(!car.id || !car.user?.id) throw new Error("sem id do carrinho");

      

        const payload = {
            id: car.id,
            user_id: car.user.id,
            itensCarrinho: car.itensCarrinho,
        };

        const redis = RedisClient.getInstance();
        await redis.set(car.id, JSON.stringify(payload));
        await redis.expire(car.id, 60 * 60 * 24 * 7);
    }

    async findByUserId(userId: string): Promise<Carrinho | null> {
        const redis: Redis = RedisClient.getInstance();
        const data = await redis.get(this.key(userId));
        if (!data) return null;
        return JSON.parse(data) as Carrinho;
    }
}