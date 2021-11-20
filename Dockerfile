FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/*.jar /app/online-bookstore-rest-api.jar

EXPOSE 8080

CMD java -XX:+UseContainerSupport -Xmx512m -Dserver.port=${PORT} -jar online-bookstore-rest-api.jar