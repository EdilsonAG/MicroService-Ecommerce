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
                console.log("\n\n\n dados: " + req)

                //const id = await this.createProductUseCase.createProduct(req.data);
                //return { ID: id, ...req.data };
            } catch (error: any) {
                req.error(400, error.message)
            }
        })

        srv.on('CREATE', 'Carrinho', async (req: any) => {
            try {
                console.log("\n\n\n")
                console.log("CREATE CARRINHO")
                const authHeader = req.headers.authorization?.replace('Bearer ', '')
                const decoded = await jwt.decode(authHeader);
                const idUser = decoded.id
                const idProduto:string = req.produto
                console.log("token")
                console.log(authHeader)

                this.carService.addItemCarrinho(idUser,idProduto)

            } catch (error) {

            }
        })
    }




}