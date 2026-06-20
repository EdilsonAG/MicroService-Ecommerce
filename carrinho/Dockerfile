# FROM node:20-alpine

# WORKDIR /app

# COPY package*.json ./
# RUN npm install --production

# # Copia o build TypeScript
# COPY ./dist ./dist

# # CAP precisa dos schemas .cds
# COPY ./db ./db
# COPY ./srv ./srv

# # Se tiver .cdsrc.json ou arquivos de config na raiz
# COPY .env* ./
# COPY tsconfig.json ./

# EXPOSE 4004

# CMD ["npx", "cds", "serve", "--production"]


# FROM node:20-alpine

# WORKDIR /app

# COPY package*.json ./
# RUN npm install --production

# COPY ./dist ./dist
# COPY ./db ./db
# COPY ./srv ./srv
# #COPY .cdsrc.json ./

# EXPOSE 4004

# CMD ["npx", "cds", "serve", "--production"]

# FROM node:20

# WORKDIR /app

# COPY package*.json ./
# RUN npm install

# COPY ./dist ./dist
# COPY ./db ./db
# COPY ./srv ./srv

# EXPOSE 4004

# CMD ["node_modules/.bin/cds", "serve", "--production"]


FROM node:20 AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM node:20
WORKDIR /app
COPY package*.json ./
RUN npm install --omit=dev
COPY --from=builder /app/dist ./dist
COPY ./keys ./dist/srv/keys
COPY ./db/*.cds ./dist/db/
COPY ./srv/*.cds ./dist/srv/
WORKDIR /app/dist
CMD ["node", "../node_modules/.bin/cds-serve", "--production"]

# FROM node:20 AS builder

# WORKDIR /app
# COPY package*.json ./
# RUN npm install

# COPY . .
# RUN npm run build

# FROM node:20

# WORKDIR /app
# COPY package*.json ./
# RUN npm install --omit=dev

# COPY --from=builder /app/dist ./dist
# COPY ./db ./db
# COPY ./srv ./srv

# EXPOSE 4004

# CMD ["node_modules/.bin/cds-serve", "--production"]