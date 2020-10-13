FROM openjdk:11
COPY target/pokedex-0.0.1-SNAPSHOT.jar /opt/pokedex-api/

WORKDIR /opt/pokedex-api/
ENTRYPOINT ["java","-jar", "pokedex-0.0.1-SNAPSHOT.jar"]