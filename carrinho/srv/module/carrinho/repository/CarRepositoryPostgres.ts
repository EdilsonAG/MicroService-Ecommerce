import cds from '@sap/cds';
import { Carrinho } from '../domain/model/Carrinho';
import { CarRepository } from '../interface/repository/CarRepository';
import { ItemCarrinho } from '../domain/model/ItemCarrinho';
import { Product } from '../domain/model/Product';


export class CarRepositoryPostgres implements CarRepository{
    findItemById(idItem: string): Promise<Product | null> {
        throw new Error('Method not implemented.');
    }
    addItemCarrinho(userId: string, item: ItemCarrinho): Promise<void> {
        throw new Error('Method not implemented.');
    }
    findCarByUserId(userId: string): Promise<Carrinho | null> {
        throw new Error('Method not implemented.');
    }


    public async createCarrinho(car: Carrinho): Promise<void> {

        try {

            const db = await cds.connect.to('db');
            await db.run(
                cds.ql.INSERT.into("app.Carrinho").entries(
                    {
                        user_id: car.user?.id
                    }
                )
            )
        } catch (error) {

        }

    }


    

    async findByUserId(tx: cds.Transaction, userId: string): Promise<Carrinho> {
    const carrinho: Carrinho = await tx.run(
        cds.ql.SELECT.one.from('app.Carrinho').where({ user_id: userId })
    );

    return carrinho;
}

    async createCarrinho2(tx: cds.Transaction, carrinho: Carrinho) {
        return tx.run(
            cds.ql.INSERT.into('app.Carrinho').entries({ 
                user_id: carrinho.user?.id 
            })
        );
    }

}