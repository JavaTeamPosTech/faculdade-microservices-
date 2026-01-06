FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .

COPY ms-contracts/pom.xml ./ms-contracts/
COPY ms-autenticacao/pom.xml ./ms-autenticacao/

COPY ms-cursos/pom.xml ./ms-cursos/
COPY ms-notificacao/pom.xml ./ms-notificacao/

RUN mvn dependency:go-offline -B

COPY ms-contracts/src ./ms-contracts/src
COPY ms-autenticacao/src ./ms-autenticacao/src

RUN mvn clean install -pl ms-autenticacao -am -Dfile.encoding=UTF-8 -B -V -DskipTests

FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /app/ms-autenticacao/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]