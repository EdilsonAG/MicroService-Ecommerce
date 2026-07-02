"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
// server.ts
const fs_1 = require("fs");
const CarServiceKafkaUser_1 = require("./module/carrinho/domain/service/CarServiceKafkaUser");
const cds = require('@sap/cds');
const path_1 = __importDefault(require("path"));
const CarServiceKafkaProduct_1 = require("./module/carrinho/domain/service/CarServiceKafkaProduct");
require('dotenv').config({ path: `.env.${process.env.NODE_ENV || 'local'}` });
const publicKeyPem = (0, fs_1.readFileSync)(path_1.default.join(__dirname, 'keys', 'algafood-pkey.pem'), 'utf8');
function parseCookies(cookie) {
    return Object.fromEntries((cookie || '').split('; ')
        .filter(Boolean)
        .map(c => {
        const [key, ...val] = c.split('=');
        return [key, val.join('=')];
    }));
}
async function jwt_auth(req, res, next) {
    console.log("\n\n");
    console.log("ESTA NO MIWARE");
    console.log("\n\n");
    console.log(req);
    console.log(res);
    // const   = parseCookies(req.headers.cookie || '')['access_token']
    const authHeader = req.headers.authorization?.replace('Bearer ', '');
    console.log(authHeader);
    console.log(authHeader);
    if (!authHeader)
        return res.status(401).send('Unauthorized');
    try {
        const { jwtVerify, importSPKI } = await import('jose');
        const publicKey = await importSPKI(publicKeyPem, 'RS256');
        const { payload } = await jwtVerify(authHeader, publicKey);
        req.user = new cds.User({
            id: payload.sub,
            roles: payload.authorities || []
        });
        next();
    }
    catch {
        res.status(401).send('Unauthorized');
    }
}
cds.on('bootstrap', (app) => {
    app.use('/odata', jwt_auth);
    (0, CarServiceKafkaUser_1.startConsumerUser)();
    (0, CarServiceKafkaProduct_1.startConsumerProduct)();
});
//# sourceMappingURL=server.js.map