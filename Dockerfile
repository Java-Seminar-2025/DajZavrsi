FROM eclipse-temurin:21-jdk-jammy AS build
ENV HOME=/usr/dajzavrsi
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN --mount=type=cache,target=/root/.m2 ./mvnw -f $HOME/pom.xml clean install

FROM eclipse-temurin:21-jre-jammy AS runtime
ARG JAR_FILE=/usr/dajzavrsi/target/*.jar
COPY --from=build $JAR_FILE /app/dajzavrsi.jar
EXPOSE 8080
ENTRYPOINT java -jar /app/dajzavrsi.jar
