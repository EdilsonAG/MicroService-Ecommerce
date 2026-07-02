"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.KafkaClient = void 0;
const kafkajs_1 = require("kafkajs");
const fs_1 = require("fs");
class KafkaClient {
    static getInstance() {
        if (!KafkaClient.instance) {
            const isSSL = process.env.KAFKA_PROTOCOL === 'SASL_SSL';
            KafkaClient.instance = new kafkajs_1.Kafka({
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
        }
        return KafkaClient.instance;
    }
}
exports.KafkaClient = KafkaClient;
//# sourceMappingURL=KafkaClient.js.map