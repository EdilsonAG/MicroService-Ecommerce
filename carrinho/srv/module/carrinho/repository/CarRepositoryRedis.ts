
import { Carrinho } from '../domain/model/Carrinho';
import { RedisClient } from '../infra/redis/redisClient';
import { ItemCarrinho } from '../domain/model/ItemCarrinho';
import { CarRepository } from '../interface/repository/CarRepository';
import { Product } from '../domain/model/Product';

export class CarRepositoryRedis implements CarRepository {

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

    // async createCarrinho(car: Carrinho): Promise<void> {
    //     if (!car.user?.id) throw new Error("usuário não existente");

    //     const id = car.id = crypto.randomUUID();

    //     const key = this.key(car.user.id);

    //     const payload = {
    //         id: id,
    //         user_id: car.user.id,
    //         itensCarrinho: car.itensCarrinho,
    //     };

    //     const redis = RedisClient.getInstance();
    //     await redis.set(key, JSON.stringify(payload));
    //     await redis.expire(key, 60 * 60 * 24 * 7);
    // }

    // // async addItemCarrinho(car:Carrinho): Promise<void>{
    // //     if(!car.id || !car.user?.id) throw new Error("sem id do carrinho");



    // //     const payload = {
    // //         id: car.id,
    // //         user_id: car.user.id,
    // //         itensCarrinho: car.itensCarrinho,
    // //     };

    // //     const redis = RedisClient.getInstance();
    // //     await redis.set(car.id, JSON.stringify(payload));
    // //     await redis.expire(car.id, 60 * 60 * 24 * 7);
    // // }

    // async addItemCarrinho(userId: string, item: ItemCarrinho): Promise<void> {
    //     const redis = RedisClient.getInstance();
    //     //const key = this.key(userId);

    //     const data = await redis.get(userId);
    //     if (!data) throw new Error("Carrinho não encontrado");

    //     const carrinho = JSON.parse(data);
    //     carrinho.itensCarrinho.push(item);

    //     await redis.set(userId, JSON.stringify(carrinho));
    //     await redis.expire(userId, 60 * 60 * 24 * 7);
    // }

    // async findCarByUserId(id: string): Promise<Carrinho | null> {
    //     const redis: Redis = RedisClient.getInstance();
    //     const data = await redis.get(this.key(id));
    //     if (!data) return null;
    //     return JSON.parse(data) as Carrinho;
    // }

    async createCarrinho(car: Carrinho): Promise<void> {

        console.log("=============")
        console.log(car.user)
        console.log("=============")

        if (!car.user?.id) throw new Error("usuário não existente");
        let carrinhoId = car.id

        if (!carrinhoId) {
            carrinhoId = crypto.randomUUID();
            car.id = carrinhoId;

        }
        const payload = {
            _id: carrinhoId,
            _user: car.user.id,
            _itensCarrinho: car.itensCarrinho,
        };

        const redis = RedisClient.getInstance();
        const TTL = 60 * 60 * 24 * 7;

        await redis.set(`carrinho:${carrinhoId}`, JSON.stringify(payload));
        await redis.expire(`carrinho:${carrinhoId}`, TTL);


        await redis.set(`user-carrinho:${car.user.id}`, carrinhoId);
        await redis.expire(`user-carrinho:${car.user.id}`, TTL);
    }

    async findCarByUserId(userId: string): Promise<Carrinho | null> {
        const redis = RedisClient.getInstance();

        console.log("chegou aqui no findcarByID")

        const carrinhoId = await redis.get(`user-carrinho:${userId}`);
        if (!carrinhoId) return null;

        console.log("carrinhoId")
        console.log(carrinhoId)

        const data = await redis.get(`carrinho:${carrinhoId}`);
        if (!data) return null;

        console.log("data")
        console.log(data)
        const parsed = JSON.parse(data);
        return Object.assign(new Carrinho(), parsed); // reconstrói instância real
    }

    async addItemCarrinho(userId: string, item: ItemCarrinho): Promise<void> {
        const redis = RedisClient.getInstance();

        const carrinhoId = await redis.get(`user-carrinho:${userId}`);
        if (!carrinhoId) throw new Error("Carrinho não encontrado");

        const data = await redis.get(`carrinho:${carrinhoId}`);
        if (!data) throw new Error("Carrinho não encontrado");

        const carrinho = JSON.parse(data);
        carrinho._itensCarrinho.push(item);

        const TTL = 60 * 60 * 24 * 7;
        await redis.set(`carrinho:${carrinhoId}`, JSON.stringify(carrinho));
        await redis.expire(`carrinho:${carrinhoId}`, TTL);
    }

    async findItemById(idItem: number): Promise<Product | null> {

        const redis = RedisClient.getInstance()

        const data = await redis.get(`product:${idItem}`);
        if (!data) return null;

        const parsed = JSON.parse(data);
        return Object.assign(new Product(), parsed);
        // console.log
        // return JSON.parse(data) as Product
    }
}