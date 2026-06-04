// // server.ts
// const cds = require('@sap/cds');

// import jwtAuth from '../auth/jwt-auth'

// import { env } from "process";
// require('dotenv').config();

// const jwt = require('jsonwebtoken');
// const fs = require('fs');
// import { readFileSync } from 'fs'
// import { Request, Response, NextFunction } from 'express';
 

// cds.on('bootstrap', (app) => {

//     function parseCookies(cookie: string): Record<string, string> {
//         return Object.fromEntries(
//             (cookie || '').split('; ')
//                 .filter(Boolean)
//                 .map(c => {
//                     const [key, ...val] = c.split('=')
//                     return [key, val.join('=')]
//                 })
//         )
//     }

//     const publicKeyPem = readFileSync('./keys/public.pem', 'utf8')

//     async function jwt_auth(req: Request, res: Response, next: NextFunction) {
//         const authHeader = req.headers.authorization;
//         const token = authHeader?.split(' ')[1]

//         const tokenCookie = parseCookies(req.headers.cookie || '')['access_token']
//         if (!token) return res.status(401).send('Unauthorized')

//         try {
//             const { jwtVerify, importSPKI } = await import('jose')
//             const publicKey = await importSPKI(publicKeyPem, 'RS256')
//             const { payload } = await jwtVerify(token, publicKey)

//                 ; (req as any).user = new cds.User({
//                     id: payload.sub,
//                     roles: (payload.authorities as string[]) || []
//                 })

//             next()
//         } catch {
//             res.status(401).send('Unauthorized')
//         }

    
//     async function verifyToken(params:type) {
//         jwt.verify(token, process.env.JWT_SECRET, (err, decoded) => {
//             console.log(decoded)
//             if (err) {
//                 if (err.name === 'TokenExpiredError') {
//                     return res.status(401).json({ message: 'Token expirado, faça login novamente' });
//                 }
//                 return res.status(401).json({ message: 'Token inválido' });
//             }

//             console.log(" Token decodificado:", decoded);
//             console.log(" Token decodificado:", decoded);



//             const user = new cds.User(decoded.sub);
//             user._roles = decoded.roles ?? [];
//             user.attr = { id: decoded.id };
//             new cds.User('anonymous');
//             ;


//             console.log(" req.user montado:", req.user);
//             console.log(" req.user._roles", req.user._roles);
//             return next();
//         })
//     }
        

//     }

//     app.use('/odata', jwt_auth)
// })

