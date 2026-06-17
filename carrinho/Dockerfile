FROM node:20-alpine

WORKDIR /app

COPY package*.json ./
RUN npm install --production

# Copia o build TypeScript
COPY ./dist ./dist

# CAP precisa dos schemas .cds
COPY ./db ./db
COPY ./srv ./srv

# Se tiver .cdsrc.json ou arquivos de config na raiz
COPY .env* ./
COPY tsconfig.json ./

EXPOSE 4004

CMD ["npx", "cds", "serve", "--production"]