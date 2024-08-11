FROM openjdk:17
WORKDIR /app
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]

#FROM nginx
#COPY ./config/nginx.conf /etc/nginx/conf.d/nginx.conf
#COPY ./html/index.html /usr/share/nginx/html/index.html
#ENTRYPOINT ["nginx", "-g", "daemon off;"]