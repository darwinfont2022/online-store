# stage 1 - build
FROM node:14-alpine AS builder

WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY ./package.json /app/
COPY ./package-lock.json /app/
RUN yarn
COPY . /app
RUN yarn build

# stage 2 - build the final image and copy the react build files
FROM nginx:stable-alpine
COPY --from=builder /app/build /usr/share/nginx/html
RUN rm /etc/nginx/conf.d/default.conf
# COPY nginx.config /etc/nginx/conf.d
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]