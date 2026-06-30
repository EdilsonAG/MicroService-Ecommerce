import { KafkaClient } from "../../infra/kafka/KafkaClient";
import { RedisClient } from "../../infra/redis/redisClient";
import { Product } from "../model/Product";



export async function startConsumerProduct() {

    try {

        const redis = RedisClient.getInstance()
        const kafka = KafkaClient.getInstance()

        console.log("chegou")
        const consumer = kafka.consumer({ groupId: 'group-car-product' });
        await consumer.connect();
        await consumer.subscribe({ topic: 'product.updated', fromBeginning: false });
        console.log("chegou2")

        await consumer.run({
            eachMessage: async ({ message }) => {
                //if (!message.value) return;

                
                /*
                Quando um produto é deletado no Product Service, o producer
                Spring envia uma mensagem com valor null para o Kafka. Isso é o padrão do Log Compaction para7
                sinalizar deleção
                
                ISSO QUE VOU ENVIAR NO SPRING
                
                kafkaTemplate.send("product.updated", product.getId().toString(), null);
                */
                if (message.value === null) {
                    const key = message.key?.toString();
                    if (key) await redis.del(`product:${key}`);
                    return;
                }

                try {
                    console.log("chegou aqui")
                    const product: Product = JSON.parse(message.value.toString());
                    console.log("Produtos chegaram")
                    console.log(product)
                    // Set já sobrescreve se existir, cria se não existir

                    await redis.set(
                        `product:${product.id}`,
                        JSON.stringify(product),
                        'EX',
                        3600
                    );
                } catch (parseError) {
                    console.error('Mensagem inválida, ignorando:', message.value.toString());
                }


                // const data = await redis.get(`product:${product.id}`);
                // if(data){
                //     const product:Product = JSON.parse(data);
                //     await redis.del(`product:${product.id}`)
                // }

                //  await redis.set(
                //     `product:${product.id}`,
                //     JSON.stringify(product),
                //     'EX',
                //     3600
                // );
            }
        })
    } catch (error) {
        console.error('Kafka indisponível, tentando novamente em 10s...', error);
        setTimeout(startConsumerProduct, 10000);
    };
}
