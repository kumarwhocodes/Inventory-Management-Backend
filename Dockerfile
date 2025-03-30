FROM amazoncorretto:21-alpine
LABEL maintainer="Kumar Sambhav sambhav26k@gmail.com" \
      description="Inventory Management Application"
COPY build/libs/inventory-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]