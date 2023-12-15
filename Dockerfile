FROM  eclipse-temurin:21
COPY build/libs/calculator*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]