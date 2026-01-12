FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# -------------------------
# Copia o POM pai
# -------------------------
COPY pom.xml .

# -------------------------
# Copia TODOS os poms dos módulos
# (necessário por causa do <modules> no pom pai)
# -------------------------
COPY ms-contracts/pom.xml ms-contracts/pom.xml
COPY ms-autenticacao/pom.xml ms-autenticacao/pom.xml
COPY ms-cursos/pom.xml ms-cursos/pom.xml
COPY ms-notificacao/pom.xml ms-notificacao/pom.xml

# -------------------------
# Cache de dependências
# -------------------------
RUN mvn dependency:go-offline -B

# -------------------------
# Copia apenas os sources necessários
# -------------------------
COPY ms-contracts/src ms-contracts/src
COPY ms-notificacao/src ms-notificacao/src

# -------------------------
# Build somente do ms-notificacao
# -------------------------
RUN mvn clean package -pl ms-notificacao -am -DskipTests

# =========================
# STAGE 2 - RUNTIME
# =========================
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/ms-notificacao/target/*.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
