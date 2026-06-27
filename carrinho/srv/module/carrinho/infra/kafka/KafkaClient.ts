import { Kafka } from "kafkajs";
import { SASLOptions } from 'kafkajs';
import { readFileSync } from 'fs';

export class KafkaClient {

    private static instance: Kafka;

    public static getInstace(): Kafka {
        if (!KafkaClient.instance) {

            const isSSL = process.env.KAFKA_PROTOCOL === 'SASL_SSL';

            KafkaClient.instance = new Kafka({
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
           
        }
        return KafkaClient.instance;
    }
}