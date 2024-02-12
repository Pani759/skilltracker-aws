# Fetching latest version of Java
FROM openjdk:8
 
# Setting up work directory
WORKDIR /fse4

# Copy the jar file into our fse4
COPY ./target/skilltracker-event-1.0.0.jar /fse4

# Exposing port 8083
EXPOSE 8083

# Starting the application
CMD ["java", "-jar", "skilltracker-event-1.0.0.jar"]