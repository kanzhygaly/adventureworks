FROM java:8
VOLUME /tmp
ADD target/adventureworks-1.0.jar adventureworks-1.0.jar
RUN bash -c 'touch /adventureworks-1.0.jar'
# Make port 8080 available to the world outside this container
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/adventureworks-1.0.jar"]