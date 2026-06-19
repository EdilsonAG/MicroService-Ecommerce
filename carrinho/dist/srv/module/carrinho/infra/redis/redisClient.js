"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.RedisClient = void 0;
const ioredis_1 = __importDefault(require("ioredis"));
class RedisClient {
    static getInstance() {
        if (!RedisClient.instance) {
            RedisClient.instance = new ioredis_1.default({
                host: process.env.REDIS_HOST || 'localhost',
                port: Number(process.env.REDIS_PORT) || 6380,
                password: process.env.REDIS_PASSWORD || 'Ed.183729',
            });
        }
        return RedisClient.instance;
    }
}
exports.RedisClient = RedisClient;
//# sourceMappingURL=redisClient.js.map