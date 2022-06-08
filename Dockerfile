FROM openjdk:17
COPY . /app
WORKDIR /app
RUN ./gradlew build
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/currency_gif_api-0.0.1-SNAPSHOT.jar"]