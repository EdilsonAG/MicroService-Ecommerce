// server.ts
import { readFileSync } from 'fs'
import { Request, Response, NextFunction } from 'express'
const cds = require('@sap/cds');
require('dotenv').config();

const publicKeyPem = readFileSync('./keys/algafood-pkey.pem', 'utf8')

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
    console.log("\n\n")
    console.log("ESTA NO MIWARE")
    console.log("\n\n")
    const token = parseCookies(req.headers.cookie || '')['access_token']

    console.log(token)
    if (!token) return res.status(401).send('Unauthorized')

    try {
        const { jwtVerify, importSPKI } = await import('jose')
        const publicKey = await importSPKI(publicKeyPem, 'RS256')
        const { payload } = await jwtVerify(token, publicKey)

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
})