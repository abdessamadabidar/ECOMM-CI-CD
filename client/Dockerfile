FROM node:18-alpine AS builder

RUN mkdir -p /usr/local/app

WORKDIR /usr/local/app

COPY ./package*.json ./

RUN npm install

COPY ./ /usr/local/app/

RUN npm run build

# Stage 2: Serve app with nginx server
FROM nginx:1.23.1-alpine

WORKDIR /usr/share/nginx/html
# Copy built files from the builder stage
COPY --from=builder /usr/local/app/dist/shopino/browser /usr/share/nginx/html

# Copy the server block configuration
COPY ./nginx.conf /etc/nginx/templates/nginx.conf.template

EXPOSE 80 443 6006 4200

CMD ["nginx", "-g", "daemon off;"]
