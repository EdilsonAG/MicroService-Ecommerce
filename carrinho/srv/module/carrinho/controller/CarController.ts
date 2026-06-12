export class CarController{

    registerHandlers(srv: any): void {
        srv.on('CREATE', 'Carrinho', async (req: any) => {
            try {
                // chamar use case
                console.log("\n\n\n dados: "+req.data)
                //const id = await this.createProductUseCase.createProduct(req.data);
                return { ID: id, ...req.data };
            } catch (error: any) {
                req.error(400, error.message)
            }
        })
    }


}