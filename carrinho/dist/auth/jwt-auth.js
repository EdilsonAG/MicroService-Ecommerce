"use strict";
// // auth/jwt-auth.ts
// // auth/jwt-auth.ts
// import { Request, Response, NextFunction } from 'express'
// import { readFileSync } from 'fs'
// import cds from '@sap/cds'
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
// const publicKeyPem = readFileSync('./keys/public.pem', 'utf8')
// module.exports = async (req: Request, res: Response, next: NextFunction) => {
//   const token = parseCookies(req)['access_token']
//   if (!token) return res.status(401).send('Unauthorized')
//   try {
//     const { jwtVerify, importSPKI } = await import('jose')  
//     const publicKey = await importSPKI(publicKeyPem, 'RS256')
//     const { payload } = await jwtVerify(token, publicKey)
//     req = new cds.User({
//       id: payload.sub,
//       roles: (payload.authorities as string[]) || []
//     })
//     next()
//   } catch {
//     res.status(401).send('Unauthorized')
//   }
// }
// function parseCookies(req: Request): Record<string, string> {
//   return Object.fromEntries(
//     (req.headers.cookie || '').split('; ')
//       .filter(Boolean)
//       .map(c => c.split('='))
//   )
// }
// auth/jwt-auth.ts
// auth/jwt-auth.ts
// import { readFileSync } from 'fs'
// import cds from '@sap/cds'
// const publicKeyPem = readFileSync('./keys/public.pem', 'utf8')
// function parseCookies(cookie: string): Record<string, string> {
//   return Object.fromEntries(
//     (cookie || '').split('; ')
//       .filter(Boolean)
//       .map(c => {
//         const [key, ...val] = c.split('=')
//         return [key, val.join('=')]
//       })
//   )
// }
// export default class JwtAuth extends cds.ApplicationService {
//   async init() {
//     const { jwtVerify, importSPKI } = await import('jose')
//     const publicKey = await importSPKI(publicKeyPem, 'RS256')
//     const self = this as any // contorna o problema de tipagem
//     self.before('*', async (req: any) => {
//       const cookies = parseCookies(req.http?.req?.headers?.cookie || '')
//       const token = cookies['access_token']
//       if (!token) return req.reject(401, 'Unauthorized')
//       try {
//         const { payload } = await jwtVerify(token, publicKey)
//         req.user = new cds.User({
//           id: payload.sub,
//           roles: (payload.authorities as string[]) || []
//         })
//       } catch {
//         return req.reject(401, 'Unauthorized')
//       }
//     })
//     return super.init()
//   }
// }
//  module.exports  = JwtAuth
// auth/jwt-auth.ts
const fs_1 = require("fs");
const cds_1 = __importDefault(require("@sap/cds"));
const publicKeyPem = (0, fs_1.readFileSync)('./keys/public.pem', 'utf8');
function parseCookies(cookie) {
    return Object.fromEntries((cookie || '').split('; ')
        .filter(Boolean)
        .map(c => {
        const [key, ...val] = c.split('=');
        return [key, val.join('=')];
    }));
}
async function jwtAuth(req, res, next) {
    const token = parseCookies(req.headers.cookie || '')['access_token'];
    if (!token)
        return res.status(401).send('Unauthorized');
    try {
        const { jwtVerify, importSPKI } = await import('jose');
        const publicKey = await importSPKI(publicKeyPem, 'RS256');
        const { payload } = await jwtVerify(token, publicKey);
        req.user = new cds_1.default.User({
            id: payload.sub,
            roles: payload.authorities || []
        });
        next();
    }
    catch {
        res.status(401).send('Unauthorized');
    }
}
exports.default = jwtAuth;
//# sourceMappingURL=jwt-auth.js.map