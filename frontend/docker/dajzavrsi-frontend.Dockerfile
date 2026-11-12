FROM oven/bun:1 AS base
WORKDIR /usr/src/dajzavrsi-frontend

FROM base AS development
RUN mkdir -p /temp/dev
COPY package.json bun.lock* /temp/development/
RUN cd /temp/development && bun install --frozen-lockfile

FROM base AS prerelease
COPY --from=development /temp/development/node_modules node_modules
COPY . .

ENV NODE_ENV=production
RUN [ "bun", "run", "build" ]

FROM nginx:alpine AS release
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=prerelease /usr/src/dajzavrsi-frontend/dist /usr/share/nginx/html
EXPOSE 80
