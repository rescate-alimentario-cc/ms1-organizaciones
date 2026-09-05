# ==============================================================================
# Multi-stage Dockerfile para MS1 Organizaciones (Spring Boot Java 17/21)
# ==============================================================================

# Etapa 1: Build con Maven
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app

# Descargar dependencias para aprovechar la cache de Docker
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el codigo fuente y empaquetar el JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen ligera de ejecucion JRE
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Crear usuario sin privilegios por seguridad
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

COPY --from=builder /app/target/ms1-organizaciones-*.jar app.jar

ENV PORT=8001
EXPOSE 8001

HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:8001/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
