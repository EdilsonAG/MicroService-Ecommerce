import { Carrinho } from "../../domain/model/Carrinho";
import { ItemCarrinho } from "../../domain/model/ItemCarrinho";
import { Product } from "../../domain/model/Product";

export interface CarRepository{
      createCarrinho(car: Carrinho): Promise<void> 
      addItemCarrinho(userId: string, item: ItemCarrinho): Promise<void>
      findCarByUserId(userId: string): Promise<Carrinho | null>
      findItemById(idItem:number):Promise<Product| null>
    
}