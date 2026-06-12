import { Kafka } from 'kafkajs';
import { ClienteEntity } from '../model/ClienteEntity';

const kafka = new Kafka({
  clientId: 'cap-app',
  brokers: ['localhost:9092']
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
      //console.log('Grupos:', data.grupos.map(g => g.nome));

      // processar e persistir via cds.entities, se necessário
    }
  });
}