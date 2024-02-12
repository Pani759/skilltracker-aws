# Fetching latest version of Java
FROM openjdk:8
 
# Setting up work directory
WORKDIR /fse4

# Copy the jar file into our fse4
COPY ./target/skillservice-1.0.0.jar /fse4

# Exposing port 8082
EXPOSE 8081

# Starting the application
CMD ["java", "-jar", "skillservice-1.0.0.jar"]