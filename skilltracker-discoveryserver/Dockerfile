# Fetching latest version of Java
FROM openjdk:8
 
# Setting up work directory
WORKDIR /fse4

# Copy the jar file into our fse4
COPY ./target/skilltracker-discoveryserver-1.0.0.jar /fse4

# Exposing port 8761
EXPOSE 8761

# Starting the application
CMD ["java", "-jar", "skilltracker-discoveryserver-1.0.0.jar"]