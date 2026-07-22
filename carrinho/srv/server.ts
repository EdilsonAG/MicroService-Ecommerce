// server.ts
import { readFileSync } from 'fs'
import { Request, Response, NextFunction } from 'express'
import {  startConsumerUser } from './module/carrinho/domain/service/CarServiceKafkaUser';
const cds = require('@sap/cds');
import path from 'path'
import { startConsumerProduct } from './module/carrinho/domain/service/CarServiceKafkaProduct';
require('dotenv').config({ path: `.env.${process.env.NODE_ENV || 'local'}` });

const publicKeyPem = readFileSync(
  path.join(__dirname, 'keys', 'algafood-pkey.pem'),
  'utf8'
)

function parseCookies(cookie: string): Record<string, string> {
    return Object.fromEntries(
        (cookie || '').split('; ')
            .filter(Boolean)
            .map(c => {
                const [key, ...val] = c.split('=')
                return [key, val.join('=')]
            })
    )
}

async function jwt_auth(req: Request, res: Response, next: NextFunction) {
     
    console.log("ESTA NO MIWARE")
    console.log("\n\n")
     
    // const   = parseCookies(req.headers.cookie || '')['access_token']
    const authHeader = req.headers.authorization?.replace('Bearer ', '')
    console.log(authHeader)
    console.log(authHeader)
    if (!authHeader) return res.status(401).send('Unauthorized')

    try {
        const { jwtVerify, importSPKI } = await import('jose')
        const publicKey = await importSPKI(publicKeyPem, 'RS256')
        const { payload } = await jwtVerify(authHeader, publicKey)

        ;(req as any).user = new cds.User({
            id: payload.sub,
            roles: (payload.authorities as string[]) || []
        })

        next()
    } catch {
        res.status(401).send('Unauthorized')
    }
}

cds.on('bootstrap', (app) => {
    app.use('/odata', jwt_auth)
    startConsumerUser();
    startConsumerProduct();
})