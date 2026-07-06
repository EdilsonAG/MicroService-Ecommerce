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
# COPY ./keys ./dist/srv/keys
# COPY ./db/*.cds ./dist/db/
# COPY ./srv/*.cds ./dist/srv/
# WORKDIR /app/dist
# CMD ["node", "../node_modules/.bin/cds-serve", "--production"]

FROM node:20 AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

FROM node:20-slim
WORKDIR /app
COPY package*.json ./
RUN npm ci --omit=dev
COPY --from=builder /app/dist ./dist
COPY ./keys ./srv/keys
COPY ./db ./db/
COPY ./srv ./srv/
CMD ["node_modules/.bin/cds-serve", "--production"]