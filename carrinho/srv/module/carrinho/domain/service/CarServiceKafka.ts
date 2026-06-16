import { Kafka } from 'kafkajs';
import { readFileSync } from 'fs';
import { ClienteEntity } from '../model/ClienteEntity';

const kafka = new Kafka({
  clientId: 'cap-app',
  brokers: ['localhost:9092'],
  ssl: {
    rejectUnauthorized: true, // self-signed, em produção coloca true
    ca: [readFileSync('C:/Users/Edilson/Documents/microservicos-eccomerce/chavekeystoredev2/kafka-cert.pem')],
    //ca: [readFileSync('C:/Users/Edilson/Documents/microservicos-eccomerce/auth/demo/demo/src/main/resources/keys/algafood-cert.pem')],
    
  },
  sasl: {
    mechanism: 'plain',
    username: 'appuser',
    password: 'appuser123'
  }
});

const consumer = kafka.consumer({ groupId: 'cap-group' });

export async function startConsumer() {
  await consumer.connect();
  await consumer.subscribe({ topic: 'user.created', fromBeginning: false });

  await consumer.run({
    eachMessage: async ({ message }) => {
      if (!message.value) return;

      const data: ClienteEntity = JSON.parse(message.value.toString());
      console.log('Cliente recebido:', data.nome, data.email);
    }
  });
}