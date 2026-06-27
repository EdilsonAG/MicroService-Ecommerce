import { KafkaClient } from "../../infra/kafka/KafkaClient";



export async function startConsumerProduct() {

    try {

        const kafka = KafkaClient.getInstace()

        const consumer = kafka.consumer({ groupId: 'cap-group' });
        await consumer.connect();
        await consumer.subscribe({ topic: 'product.updated', fromBeginning: false });

        await consumer.run({
            eachMessage: async ({ message }) => {
                
            }
        })
    } catch (error) {
        console.error('Kafka indisponível, tentando novamente em 10s...', error);
        setTimeout(startConsumerProduct, 10000);
    };
}
