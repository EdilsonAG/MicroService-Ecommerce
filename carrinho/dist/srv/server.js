"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
// server.ts
const fs_1 = require("fs");
const CarServiceKafka_1 = require("./module/carrinho/domain/service/CarServiceKafka");
const cds = require('@sap/cds');
require('dotenv').config({ path: `.env.${process.env.NODE_ENV || 'local'}` });
const publicKeyPem = (0, fs_1.readFileSync)('./keys/algafood-pkey.pem', 'utf8');
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
    const token = parseCookies(req.headers.cookie || '')['access_token'];
    console.log(token);
    if (!token)
        return res.status(401).send('Unauthorized');
    try {
        const { jwtVerify, importSPKI } = await import('jose');
        const publicKey = await importSPKI(publicKeyPem, 'RS256');
        const { payload } = await jwtVerify(token, publicKey);
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
    (0, CarServiceKafka_1.startConsumer)();
});
//# sourceMappingURL=server.js.map