# =========================
# STAGE 1 - BUILD
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copia o POM pai
COPY pom.xml .

# Copia o POM do módulo
COPY ms-cursos/pom.xml ms-cursos/pom.xml

# Copia o código-fonte do módulo
COPY ms-cursos/src ms-cursos/src

# Builda apenas o módulo ms-cursos, mas com o parent disponível
RUN mvn -pl ms-cursos -am package -DskipTests

# =========================
# STAGE 2 - RUNTIME
# =========================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia o JAR gerado
COPY --from=build /app/ms-cursos/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
