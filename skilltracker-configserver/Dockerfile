# Fetching latest version of Java
FROM openjdk:8
 
# Setting up work directory
WORKDIR /fse4

# Copy the jar file into our fse4
COPY ./target/skilltracker-configserver-1.0.0.jar /fse4

# Exposing port 8888
EXPOSE 8888

# Starting the application
CMD ["java", "-jar", "skilltracker-configserver-1.0.0.jar"]