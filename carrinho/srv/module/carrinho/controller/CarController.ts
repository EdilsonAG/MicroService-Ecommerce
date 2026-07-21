import { CarService } from "../domain/service/CarService";
const jwt = require('jsonwebtoken');


export class CarController {

    private carService: CarService;

    constructor(carService: CarService) {
        this.carService = carService;
    }

    registerHandlers(srv: any): void {
        srv.on('READ', 'Carrinho', async (req: any) => {
            try {
                // chamar use case
                const authHeader = req.headers.authorization?.replace('Bearer ', '')
                const decoded = await jwt.decode(authHeader);
                const idUser = decoded.usuario_id

               return this.carService.buscarItensCarrinho(idUser)
            } catch (error: any) {
                req.error(400, error.message)
            }
        })

        srv.on('PATCH', 'Carrinho', async(req:any) =>{
            try {
                const authHeader = req.headers.authorization?.replace('Bearer ', '')
                const decoded = await jwt.decode(authHeader);
                console.log("requisição")
                //const asdf = JSON.stringify(req.req)
          
                const idUser = decoded.usuario_id
              
                console.log("CHEGOU NO PATCH")
                //console.log(req)
                const quantidade = req.data.itens.quantidade

                const idProduto = req.params[0].id
                 return this.carService.editarItemCarrinho(idUser,idProduto,quantidade)
            } catch (error) {
                
            }
        })

        srv.on('DELETE', 'Carrinho', async (req: any) => {
            console.log("CHEGOU NO DELETE DO CARRINHO")
             const authHeader = req.headers.authorization?.replace('Bearer ', '')
                const decoded = await jwt.decode(authHeader);
                const idUser = decoded.usuario_id
                const idProduto:number = Number(req.data.id)

                console.log(req.data.id)

                this.carService.deletarItemCarrinho(idUser,idProduto)

               
        })

        srv.on('addItemCarrinho', async (req: any) => {
            try {
                console.log("\n\n\n")
                console.log("CREATE CARRINHO")
                const authHeader = req.headers.authorization?.replace('Bearer ', '')
                const decoded = await jwt.decode(authHeader);
                const idUser = decoded.usuario_id
                const quantidade: number = req.data.quantidade;
                const idProduto:number = req.data.idProduto
                console.log("token")
                console.log(authHeader)
                console.log(idProduto)
                console.log(idProduto)
                console.log(idProduto)
                console.log(idUser)
                console.log(decoded.usuario_id)

                this.carService.addItemCarrinho(idUser,idProduto,quantidade)

            } catch (error) {

            }
        })
    }




}