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
COPY --from=builder /usr/local/app/dist/shopino /usr/share/nginx/html

# Remove default nginx configuration
RUN rm /etc/nginx/conf.d/default.conf

# Copy the server block configuration as template
COPY ./nginx.conf /etc/nginx/templates/default.conf.template

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
