FROM openjdk:21-oracle
RUN mkdir -p /app/data
VOLUME /tmp
EXPOSE 8080
ADD ./build/libs/Backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]