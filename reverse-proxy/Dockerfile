FROM nginx:latest

WORKDIR /proxy

COPY ./nginx.conf /etc/nginx/nginx.conf

RUN rm -rf /etc/nginx/conf.d/default.conf

ENTRYPOINT ["nginx", "-g", "daemon off;"]

EXPOSE 80