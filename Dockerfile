# Build stage
FROM amazoncorretto:21-alpine AS builder
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

# Runtime stage
FROM amazoncorretto:21-alpine
LABEL maintainer="Kumar Sambhav sambhav26k@gmail.com" \
      description="Inventory Management Application"
COPY --from=builder /app/build/libs/inventory-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]