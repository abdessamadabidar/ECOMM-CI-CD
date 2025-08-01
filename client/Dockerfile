FROM node:18-alpine AS builder

RUN mkdir -p /usr/local/app

WORKDIR /usr/local/app

COPY ./package*.json ./

RUN npm install

COPY ./ /usr/local/app/

RUN npm run build

# Stage 2: Serve app with nginx server
FROM nginx:latest

# Copy built files from the builder stage
COPY --from=builder /usr/local/app/dist/shopino/browser /usr/share/nginx/html

COPY ./nginx.conf /etc/nginx/nginx.conf

EXPOSE 80 443 6006 4200

CMD ["nginx", "-g", "daemon off;"]
