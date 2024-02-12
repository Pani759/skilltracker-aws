# Fetching latest version of Java
FROM openjdk:8
 
# Setting up work directory
WORKDIR /fse4

# Copy the jar file into our fse4
COPY ./target/skilltracker-api-gateway-1.0.0.jar /fse4

# Exposing port 8084
EXPOSE 8084

# Starting the application
CMD ["java", "-jar", "skilltracker-api-gateway-1.0.0.jar"]