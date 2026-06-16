import { Kafka, SASLOptions } from 'kafkajs';
import { readFileSync } from 'fs';
import { ClienteEntity } from '../model/ClienteEntity';


// const kafka = new Kafka({
//   clientId: 'cap-app',
//   brokers: ['localhost:9092'],
//   ssl: {
//     rejectUnauthorized: true, // self-signed, em produção coloca true
//     ca: [readFileSync('C:/Users/Edilson/Documents/microservicos-eccomerce/chavekeystoredev2/kafka-cert.pem')],
//     //ca: [readFileSync('C:/Users/Edilson/Documents/microservicos-eccomerce/auth/demo/demo/src/main/resources/keys/algafood-cert.pem')],

//   },
//   sasl: {
//     mechanism: 'plain',
//     username: 'appuser',
//     password: 'appuser123'
//   }
// });


export async function startConsumer() {
  
const isSSL = process.env.KAFKA_PROTOCOL === 'SASL_SSL';

const kafka = new Kafka({
  clientId: 'cap-app',
  brokers: [process.env.KAFKA_BROKERS!],
  ssl: isSSL ? {
    rejectUnauthorized: process.env.KAFKA_REJECT_UNAUTHORIZED === 'true',
    ca: process.env.KAFKA_CERT_PATH
      ? [readFileSync(process.env.KAFKA_CERT_PATH)]
      : undefined
  } : false,
  sasl: isSSL ? {
    mechanism: 'plain',
    username: process.env.KAFKA_USERNAME!,
    password: process.env.KAFKA_PASSWORD!
  } as SASLOptions : undefined
});

const consumer = kafka.consumer({ groupId: 'cap-group' });
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