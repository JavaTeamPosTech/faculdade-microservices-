# =========================
# STAGE 1 - BUILD
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copia todo o monorepo
COPY . .

# Builda todos os módulos e instala no local repository do container
RUN mvn clean install -DskipTests

# =========================
# STAGE 2 - RUNTIME
# =========================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia apenas o jar do ms-autenticacao
COPY --from=build /app/ms-autenticacao/target/*.jar app.jar

# Porta que o microserviço vai expor
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
