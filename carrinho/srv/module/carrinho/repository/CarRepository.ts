import cds from '@sap/cds';
import { Carrinho } from '../domain/model/Carrinho';


export class CarRepository {


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

    async createCarrinho2(tx: cds.Transaction, userId: string) {
        return tx.run(
            cds.ql.INSERT.into('Carrinhos').entries({ user_id: userId })
        );
    }

}