# =========================
# STAGE 1 - BUILD
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# -------------------------
# Copia o POM pai
# -------------------------
COPY pom.xml .

# -------------------------
# Copia TODOS os poms dos módulos
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
COPY ms-cursos/src ms-cursos/src

# -------------------------
# Build somente do ms-cursos
# -------------------------
RUN mvn clean package -pl ms-cursos -am -DskipTests

# =========================
# STAGE 2 - RUNTIME
# =========================
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/ms-cursos/target/*.jar app.jar

EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
