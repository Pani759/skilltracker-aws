# Fetching latest version of Java
FROM openjdk:8
 
# Setting up work directory
WORKDIR /fse4

# Copy the jar file into our fse4
COPY ./target/profileservice-1.0.0.jar /fse4

# Exposing port 8081
EXPOSE 8081

# Starting the application
CMD ["java", "-jar", "profileservice-1.0.0.jar"]
#ENTRYPOINT ["java", "-jar", "profileservice-1.0.0.jar"]