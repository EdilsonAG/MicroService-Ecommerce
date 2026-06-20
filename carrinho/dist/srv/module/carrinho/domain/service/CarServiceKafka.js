"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.startConsumer = startConsumer;
const kafkajs_1 = require("kafkajs");
const fs_1 = require("fs");
const Carrinho_1 = require("../model/Carrinho");
const CarRepositoryPostgres_1 = require("../../repository/CarRepositoryPostgres");
const CarRepositoryRedis_1 = require("../../repository/CarRepositoryRedis");
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
async function startConsumer() {
    const isSSL = process.env.KAFKA_PROTOCOL === 'SASL_SSL';
    const carRepository = new CarRepositoryPostgres_1.CarRepositoryPostgres();
    const carRepositoryRedis = new CarRepositoryRedis_1.CarRepositoryRedis();
    const kafka = new kafkajs_1.Kafka({
        clientId: 'cap-app',
        brokers: [process.env.KAFKA_BROKERS],
        ssl: isSSL ? {
            rejectUnauthorized: process.env.KAFKA_REJECT_UNAUTHORIZED === 'true',
            ca: process.env.KAFKA_CERT_PATH
                ? [(0, fs_1.readFileSync)(process.env.KAFKA_CERT_PATH)]
                : undefined
        } : false,
        sasl: isSSL ? {
            mechanism: 'plain',
            username: process.env.KAFKA_USERNAME,
            password: process.env.KAFKA_PASSWORD
        } : undefined
    });
    try {
        const consumer = kafka.consumer({ groupId: 'cap-group' });
        await consumer.connect();
        await consumer.subscribe({ topic: 'user.created', fromBeginning: false });
        await consumer.run({
            eachMessage: async ({ message }) => {
                if (!message.value)
                    return;
                const user = JSON.parse(message.value.toString());
                if (!user.id)
                    return;
                console.log(user);
                const carrinhoExistente = await carRepositoryRedis.findCarByUserId(user.id);
                if (carrinhoExistente)
                    return console.log("carrinho ja existe");
                const carrinho = new Carrinho_1.Carrinho();
                carrinho.user = user;
                await carRepositoryRedis.createCarrinho(carrinho);
            }
            // eachMessage: async ({ message }) => {
            //   if (!message.value) return;
            //   const user: ClienteEntity = JSON.parse(message.value.toString());
            //   console.log('Cliente recebido:', user.nome, user.email);
            //   const carrinho = new Carrinho()
            //   carrinho.user = user;
            //   try {
            //     await cds.tx(async (tx) => {
            //       if (user.id === undefined) {
            //         throw new Error("Usuário nao existente")
            //       }
            //     })
            //   } catch (error) {
            //   }
            // try {
            //   await cds.tx(async (tx) => {
            //     if (user.id === undefined) {
            //       throw new Error("Usuário nao existente")
            //     }
            //     console.log("antes de encontrar")
            //     const carrinhoEncontrado: Carrinho = await carRepository.findByUserId(tx, user.id)
            //     console.log("depois de encontrar")
            //     if (carrinhoEncontrado) {
            //       throw new Error("Carrinho já existe");
            //     }
            //     console.log("antes de salvar no banco")
            //      await carRepository.createCarrinho2(tx,carrinho)
            //     console.log("salvo no banco")
            //     // Verifica se já existe carrinho para esse usuário
            //     // const existing = await tx.run(
            //     //   SELECT.one.from('Carrinhos').where({ user_id: user.id })
            //     // );
            //     // if (existing) {
            //     //   console.warn(`Carrinho já existe para usuário ${user.id}, ignorando.`);
            //     //   return;
            //     // }
            //   });
            // } catch (err) {
            //   console.error('Erro ao processar mensagem Kafka:', err);
            //   // Aqui você decide: deixar o Kafka retentar ou enviar pra DLQ
            //   throw err; // lança → Kafka vai retentar conforme sua config
            // }
            //  }
        });
    }
    catch (error) {
        console.error('Kafka indisponível, tentando novamente em 10s...', error);
        setTimeout(startConsumer, 10000);
    }
    // await consumer.run({
    //   eachMessage: async ({ message }) => {
    //     if (!message.value) return;
    //     const user: ClienteEntity = JSON.parse(message.value.toString());
    //     console.log('Cliente recebido:', user.nome, user.email);
    //     const carrinho = new Carrinho()
    //     carrinho.user = user
    //     carRepository.createCarrinho(carrinho);
    //   }
    // })
    ;
}
//# sourceMappingURL=CarServiceKafka.js.map